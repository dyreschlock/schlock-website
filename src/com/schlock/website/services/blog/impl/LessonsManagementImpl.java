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
        grades.add(FOURTH_GRADE);
        grades.add(THIRD_GRADE);
        grades.add(SECOND_GRADE);
        grades.add(FIRST_GRADE);

        return grades;
    }

    public List<String> getDisplayGrades()
    {
        List<String> grades = new ArrayList<String>();

        grades.add(SIXTH_GRADE);
        grades.add(FIFTH_GRADE);
        grades.add(THIRD_GRADE + CONJUNCTION + FOURTH_GRADE);
        grades.add(FIRST_GRADE + CONJUNCTION + SECOND_GRADE);

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


    public List<String> getLessons(String grade, String year)
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
        else
        {
            List<String> grades = Arrays.asList(StringUtils.split(grade, CONJUNCTION));
            List<LessonPost> posts = postDAO.getByYearGradeLessonKeyword(year, grades, null);

            for (LessonPost post : posts)
            {
                String uuid = post.getUuid();
                if (StringUtils.containsIgnoreCase(uuid, LESSONS_PREFIX))
                {
                    int index = uuid.lastIndexOf(LESSONS_PREFIX) + LESSONS_PREFIX.length();
                    String name = uuid.substring(index);

                    lessons.add(name);
                }
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
        lessons.addAll(standardSpecialLessons());

        if (StringUtils.equalsIgnoreCase(grade, SIXTH_GRADE) ||
                StringUtils.equalsIgnoreCase(grade, FIFTH_GRADE))
        {
            List<LessonPost> posts = postDAO.getByYearGradeLessonKeyword(year, grade, null);
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

        List<String> grades = Arrays.asList(StringUtils.split(grade, CONJUNCTION));
        List<LessonPost> posts = postDAO.getByYearGradeLessonKeyword(year, grades, lesson);

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
        for (String g : getGrades())
        {
            for (String p : parameters)
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
        for (String y : getYears())
        {
            for (String p : parameters)
            {
                if (StringUtils.equalsIgnoreCase(p, y))
                {
                    return y;
                }
            }
        }
        return "";
    }

    public String getLesson(String grade, String year, String... parameters)
    {
        for (String l : getLessons(grade, year))
        {
            for (String p : parameters)
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
