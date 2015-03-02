package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.LessonPost;

import java.util.List;

public interface LessonsManagement
{
    public static final String SIXTH_GRADE_PARAM = "sixth";
    public static final String FIFTH_GRADE_PARAM = "fifth";
    public static final String FOURTH_GRADE_PARAM = "fourth";
    public static final String THIRD_GRADE_PARAM = "third";
    public static final String SECOND_GRADE_PARAM = "second";
    public static final String FIRST_GRADE_PARAM = "first";

    public static final String SIXTH_GRADE = "6nen";
    public static final String FIFTH_GRADE = "5nen";
    public static final String FOURTH_GRADE = "4nen";
    public static final String THIRD_GRADE = "3nen";
    public static final String SECOND_GRADE = "2nen";
    public static final String FIRST_GRADE = "1nen";

    public static final String HI_FRIENDS_PREFIX = "hi-friends-";
    public static final String LESSONS_PREFIX = "lesson-";
    public static final String CONJUNCTION = "+";

    public static final String FULL_DOWNLOAD = "year-download";
    public static final String SYLLABUS = "yearly-syllabus";

    public static final String HEISEI25 = "25";
    public static final String HEISEI26 = "26";
    public static final String HEISEI27 = "27";
    public static final String HEISEI28 = "28";

    public List<String> getGrades(String selected);

    public List<String> getLessons(String grade);

    public List<String> getYearlyItems(String grade);

    public List<String> getSpecialLessons(String grade);

    public List<String> getYears(String lesson);

    public LessonPost getPost(String lesson, String year);

    public void resetPostCache();
}
