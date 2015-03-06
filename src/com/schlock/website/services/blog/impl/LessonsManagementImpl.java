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

    public List<String> getYears()
    {
        List<String> years = new ArrayList<String>();
//        years.add(HEISEI28);
        years.add(HEISEI27);
        years.add(HEISEI26);
        years.add(HEISEI25);

        return years;
    }

    public List<String> getGrades()
    {
        List<String> grades = new ArrayList<String>();

        grades.add(SIXTH_GRADE);
        grades.add(FIFTH_GRADE);
//        grades.add(FOURTH_GRADE);
//        grades.add(THIRD_GRADE);
//        grades.add(SECOND_GRADE);
//        grades.add(FIRST_GRADE);

        return grades;
    }

    private List<String> sixthGradeLessons()
    {
        List<String> lessons = new ArrayList<String>();

        lessons.add(LESSON_1_1);
        lessons.add(LESSON_1_2);
        lessons.add(LESSON_2);
        lessons.add(LESSON_3);
        lessons.add(LESSON_4);
        lessons.add(LESSON_5);
        lessons.add(LESSON_6);
        lessons.add(LESSON_7);
        lessons.add(LESSON_8);

        return lessons;
    }

    private List<String> fifthGradeLessons()
    {
        List<String> lessons = new ArrayList<String>();

        lessons.add(LESSON_1);
        lessons.add(LESSON_2);
        lessons.add(LESSON_3);
        lessons.add(LESSON_4);
        lessons.add(LESSON_5);
        lessons.add(LESSON_6);
        lessons.add(LESSON_7);
        lessons.add(LESSON_8);
        lessons.add(LESSON_9);

        return lessons;
    }

    private List<String> standardSpecialLessons()
    {
        List<String> lessons = new ArrayList<String>();

        lessons.add(SELF_INTRO);
        lessons.add(CHRISTMAS);

        return lessons;
    }


    public List<String> getLessons(String grade)
    {
        List<String> lessons = new ArrayList<String>();

        if (StringUtils.equalsIgnoreCase(grade, SIXTH_GRADE))
        {
            int hi_friends = 2;

            for (String lesson : sixthGradeLessons())
            {
                lessons.add(HI_FRIENDS_PREFIX + hi_friends + CONJUNCTION + lesson);
            }
        }
        else if (StringUtils.equalsIgnoreCase(grade, FIFTH_GRADE))
        {
            int hi_friends = 1;

            for (String lesson : fifthGradeLessons())
            {
                lessons.add(HI_FRIENDS_PREFIX + hi_friends + CONJUNCTION + lesson);
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

    public List<String> getSpecialLessons(String grade, String year)
    {
        List<String> lessons = new ArrayList<String>();

        List<String> keywords = Arrays.asList(grade, year);
        List<LessonPost> posts = postDAO.getLessonPostByKeywords(keywords);

        lessons.addAll(standardSpecialLessons());

        for (LessonPost post : posts)
        {
            String uuid = post.getUuid();
            if (StringUtils.containsIgnoreCase(uuid, SPECIAL_TAG))
            {
                boolean contains = false;
                for (String special : standardSpecialLessons())
                {
                    if (StringUtils.containsIgnoreCase(uuid, special))
                    {
                        contains = true;
                    }
                }

                if (!contains)
                {
                    int index = uuid.indexOf(SPECIAL_TAG) + SPECIAL_TAG.length() + 1;
                    String name = uuid.substring(index);

                    lessons.add(name);
                }
            }
        }

        return lessons;
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


    public LessonPost getPost(String lesson, String grade, String year)
    {
        final String hash = year + grade + lesson;
        if (cachedPosts.containsKey(hash))
        {
            return cachedPosts.get(hash);
        }

        List<String> keywords = Arrays.asList(lesson, grade, year);

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
            for (String g : getGrades())
            {
                if (StringUtils.equalsIgnoreCase(p, g))
                {
                    return g;
                }
            }
        }
        return null;
    }

    public String getYear(String... parameters)
    {
        for (String p : parameters)
        {
            for (String y : getYears())
            {
                if (StringUtils.equalsIgnoreCase(p, y))
                {
                    return y;
                }
            }
        }
        return "";
    }

    public String getLesson(String grade, String... parameters)
    {
        for (String p : parameters)
        {
            for (String l : getLessons(grade))
            {
                if (StringUtils.containsIgnoreCase(l, p))
                {
                    return l;
                }
            }
        }
        return "";
    }


    public String getGrade(AbstractPost post)
    {
        for (String grade : getGrades())
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

    public String getYear(AbstractPost post)
    {
        for (String year : getYears())
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
