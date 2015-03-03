package com.schlock.website.services.blog.impl;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Keyword;
import com.schlock.website.entities.blog.LessonPost;
import com.schlock.website.services.blog.LessonsManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class LessonsManagementImpl implements LessonsManagement
{
    private final PostDAO postDAO;

    private Map<String, LessonPost> cachedPosts;

    public LessonsManagementImpl(PostDAO postDAO)
    {
        this.postDAO = postDAO;
    }


    public List<String> getGrades(String selected)
    {
        if (StringUtils.isBlank(selected))
        {
            return Arrays.asList(
                    SIXTH_GRADE, FIFTH_GRADE //, FOURTH_GRADE, THIRD_GRADE, SECOND_GRADE, FIRST_GRADE
            );
        }
        return Arrays.asList(selected);
    }

    public List<String> getLessons(String grade)
    {
        List<String> lessons = new ArrayList<String>();

        if (StringUtils.equalsIgnoreCase(grade, SIXTH_GRADE))
        {
            int hi_friends = 2;

            String l1 = HI_FRIENDS_PREFIX + hi_friends + CONJUNCTION + LESSONS_PREFIX + 1;
            lessons.add(l1 + "-1");
            lessons.add(l1 + "-2");

            for (int i = 2; i <= 8; i++)
            {
                String str = HI_FRIENDS_PREFIX + hi_friends + CONJUNCTION + LESSONS_PREFIX + i;
                lessons.add(str);
            }
        }
        else if (StringUtils.equalsIgnoreCase(grade, FIFTH_GRADE))
        {
            int hi_friends = 1;

            for (int i = 1; i <= 9; i++)
            {
                String str = HI_FRIENDS_PREFIX + hi_friends + CONJUNCTION + LESSONS_PREFIX + i;
                lessons.add(str);
            }
        }

        return lessons;
    }

    public List<String> getYearlyItems(String grade)
    {
        List<String> items = new ArrayList<String>();

        if (StringUtils.equalsIgnoreCase(grade, SIXTH_GRADE) ||
                StringUtils.equalsIgnoreCase(grade, FIFTH_GRADE))
        {
            items.add(FULL_DOWNLOAD);
            items.add(SYLLABUS);
        }
        return items;
    }

    public List<String> getSpecialLessons(String grade)
    {
        List<String> lessons = new ArrayList<String>();

        return lessons;
    }

    public List<String> getTotalYears()
    {
        List<String> years = new ArrayList<String>();
//        years.add(HEISEI28);
        years.add(HEISEI27);
        years.add(HEISEI26);
        years.add(HEISEI25);

        return years;
    }

    public List<String> getYears(String selectedYear, String lessonString)
    {
        List<String> years = null;
        if (StringUtils.isNotBlank(selectedYear))
        {
            years = new ArrayList<String>();
            years.add(selectedYear);
        }
        else
        {
            years = getTotalYears();
        }

        if (StringUtils.containsIgnoreCase(lessonString, HI_FRIENDS_PREFIX))
        {
            int year = getHiFriendsNumber(lessonString);
            int lesson = getLessonNumber(lessonString);

            if (lesson < 4)
            {
                years.remove(HEISEI25);
            }
        }
        return years;
    }


    private int getLessonNumber(String lessonString)
    {
        int index = HI_FRIENDS_PREFIX.length() + 1 + 1 + LESSONS_PREFIX.length();
        String lesson = StringUtils.substring(lessonString, index, index + 1);

        return Integer.parseInt(lesson);
    }

    private int getHiFriendsNumber(String lessonString)
    {
        int index = HI_FRIENDS_PREFIX.length();
        String year = StringUtils.substring(lessonString, index, index + 1);

        return Integer.parseInt(year);
    }


    public LessonPost getPost(String lesson, String year)
    {
        final String hash = year + lesson;
        if (cachedPosts.containsKey(hash))
        {
            return cachedPosts.get(hash);
        }

        List<String> keywords = Arrays.asList(lesson, year);

        List<LessonPost> posts = postDAO.getLessonPostByKeywords(keywords);

        LessonPost post = null;
        if (!posts.isEmpty())
        {
            post = posts.get(0);
        }

        cachedPosts.put(hash, post);
        return post;
    }

    public void resetPostCache()
    {
        cachedPosts = new HashMap<String, LessonPost>();
    }


    public String getGrade(String... parameters)
    {
        for (String p : parameters)
        {
            String id = gradesMap().get(p);
            if (StringUtils.isNotBlank(id))
            {
                return id;
            }
        }
        return null;
    }

    private String getGradeParam(String gradeId)
    {
        for (String param : gradesMap().keySet())
        {
            String id = gradesMap().get(param);
            if (StringUtils.equalsIgnoreCase(gradeId, id))
            {
                return param;
            }
        }
        return null;
    }

    private Map<String, String> gradesMap()
    {
        Map<String, String> map = new HashMap<String, String>();

        map.put(SIXTH_GRADE_PARAM, SIXTH_GRADE);
        map.put(FIFTH_GRADE_PARAM, FIFTH_GRADE);
//        map.put(FOURTH_GRADE_PARAM, FOURTH_GRADE);
//        map.put(THIRD_GRADE_PARAM, THIRD_GRADE);
//        map.put(SECOND_GRADE_PARAM, SECOND_GRADE);
//        map.put(FIRST_GRADE_PARAM, FIRST_GRADE);

        return map;
    }

    public String getYear(String... parameters)
    {
        for (String p : parameters)
        {
            for (String y : getTotalYears())
            {
                if (StringUtils.equalsIgnoreCase(p, y))
                {
                    return y;
                }
            }
        }
        return "";
    }


    public String getGrade(AbstractPost post)
    {
        for (String grade : getGrades(null))
        {
            for (Keyword keyword : post.getKeywords())
            {
                if (StringUtils.equalsIgnoreCase(grade, keyword.getName()))
                {
                    return grade;
                }
            }
        }
        return "";
    }

    public String getGradeParam(AbstractPost post)
    {
        String gradeId = getGrade(post);
        return getGradeParam(gradeId);
    }

    public String getYear(AbstractPost post)
    {
        for (String year : getTotalYears())
        {
            for (Keyword keyword : post.getKeywords())
            {
                if (StringUtils.equalsIgnoreCase(year, keyword.getName()))
                {
                    return year;
                }
            }
        }
        return "";
    }
}
