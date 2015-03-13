package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.LessonPost;

import java.util.List;

public interface LessonsManagement
{
    public static final String SIXTH_GRADE = "sixth";
    public static final String FIFTH_GRADE = "fifth";
    public static final String FOURTH_GRADE = "fourth";
    public static final String THIRD_GRADE = "third";
    public static final String SECOND_GRADE = "second";
    public static final String FIRST_GRADE = "first";

    public static final String HI_FRIENDS_PREFIX = "hi-friends-";
    public static final String CONJUNCTION = "+";
    public static final String LESSONS_PREFIX = "lesson-";

    public static final String SPECIAL_TAG = "special";
    public static final String SELF_INTRO = "self-intro";
    public static final String CHRISTMAS = "christmas";

    public static final String LESSON_1_1 = "lesson-1-1";
    public static final String LESSON_1_2 = "lesson-1-2";
    public static final String LESSON_1 = "lesson-1";
    public static final String LESSON_2 = "lesson-2";
    public static final String LESSON_3 = "lesson-3";
    public static final String LESSON_4 = "lesson-4";
    public static final String LESSON_5 = "lesson-5";
    public static final String LESSON_6 = "lesson-6";
    public static final String LESSON_7 = "lesson-7";
    public static final String LESSON_8 = "lesson-8";
    public static final String LESSON_9 = "lesson-9";

    public static final String FULL_DOWNLOAD = "year-download";
    public static final String SYLLABUS = "syllabus";

    public static final String HEISEI25 = "h25";
    public static final String HEISEI26 = "h26";
    public static final String HEISEI27 = "h27";
    public static final String HEISEI28 = "h28";

    public static final String LESSON_PLAN_FOLDER = "lesson_plans";
    public static final String FLASH_CARDS_FOLDER = "flash_cards";


    public List<String> getYears();

    public List<String> getGrades();

    public List<String> getDisplayGrades();

    public List<String> getLessons(String grade, String year);

    public List<String> getYearlyItems(String grade);

    public List<String> getSpecialLessons(String grade, String year);


    public LessonPost getPost(String lesson, String grade, String year);

    public void resetPostCache();


    public String getGrade(String... parameters);

    public String getYear(String... parameters);

    public String getLesson(String grade, String year, String... parameters);


    public List<String> getGrades(AbstractPost post);

    public String getYear(AbstractPost post);


    public String getLessonPlanImageLink(AbstractPost post);

    public String getFlashCardImageLink(AbstractPost post);
}
