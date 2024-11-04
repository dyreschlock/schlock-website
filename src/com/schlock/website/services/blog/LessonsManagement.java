package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.LessonPost;

import java.util.List;

public interface LessonsManagement
{
    String SIXTH_GRADE = "sixth";
    String FIFTH_GRADE = "fifth";
    String FOURTH_GRADE = "fourth";
    String THIRD_GRADE = "third";
    String SECOND_GRADE = "second";
    String FIRST_GRADE = "first";

    String HI_FRIENDS_PREFIX = "hi-friends-";
    String CONJUNCTION = "+";
    String LESSONS_PREFIX = "lesson-";

    String SPECIAL_TAG = "special";
    String SELF_INTRO = "self-intro";
    String CHRISTMAS = "christmas";

    String LESSON_1_1 = "lesson-1-1";
    String LESSON_1_2 = "lesson-1-2";
    String LESSON_1 = "lesson-1";
    String LESSON_2 = "lesson-2";
    String LESSON_3 = "lesson-3";
    String LESSON_4 = "lesson-4";
    String LESSON_5 = "lesson-5";
    String LESSON_6 = "lesson-6";
    String LESSON_7 = "lesson-7";
    String LESSON_8 = "lesson-8";
    String LESSON_9 = "lesson-9";

    String FULL_DOWNLOAD = "year-download";
    String SYLLABUS = "syllabus";

    String HEISEI25 = "h25";
    String HEISEI26 = "h26";
    String HEISEI27 = "h27";
    String HEISEI28 = "h28";

    String FAKE_YEAR = "course";

    String LESSON_PLAN_FOLDER = "lesson_plans";
    String FLASH_CARDS_FOLDER = "flash_cards";



    List<String> getYears();

    List<String> getGrades();

    List<String> getDisplayGrades();

    List<String> getLessons(String grade, String year);

    List<String> getYearlyItems(String grade);

    List<String> getSpecialLessons(String grade, String year);


    LessonPost getPost(String lesson, String grade, String year);

    void resetPostCache();


    String getGrade(String... parameters);

    String getYear(String... parameters);

    String getLesson(String grade, String year, String... parameters);


    List<String> getGrades(AbstractPost post);

    String getYear(AbstractPost post);


    String getLessonPlanImageLink(AbstractPost post);

    String getFlashCardImageLink(AbstractPost post);
}
