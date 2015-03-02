package com.schlock.website.pages.lessons;

import com.schlock.website.entities.blog.LessonPost;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.services.blog.LessonsManagement;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class LessonsIndex
{
    private static final String TITLE_SUFFIX = "-title";
    private static final String YEAR_SUFFIX = "-year";

    @Inject
    private PostDAO postDAO;

    @Inject
    private PostManagement postManagement;

    @Inject
    private LessonsManagement lessonManagement;

    @Inject
    private Messages messages;

    private String selectedGrade;


    @Property
    private String currentGrade;

    @Property
    private String currentLesson;

    @Property
    private String currentYear;


    Object onActivate()
    {
        lessonManagement.resetPostCache();
        return true;
    }

    Object onActivate(String parameter)
    {
        this.selectedGrade = parameter;

        lessonManagement.resetPostCache();
        return true;
    }

    public boolean isFifthOrSixth()
    {
        return StringUtils.equalsIgnoreCase(LessonsManagement.SIXTH_GRADE, currentGrade) ||
                StringUtils.equalsIgnoreCase(LessonsManagement.FIFTH_GRADE, currentGrade);
    }


    public List<String> getGrades()
    {
        return lessonManagement.getGrades(selectedGrade);
    }

    public List<String> getLessons()
    {
        return lessonManagement.getLessons(currentGrade);
    }

    public List<String> getYearlyItems()
    {
        return lessonManagement.getYearlyItems(currentGrade);
    }

    public List<String> getSpecialLessons()
    {
        return lessonManagement.getSpecialLessons(currentGrade);
    }

    public List<String> getYears()
    {
        return lessonManagement.getYears(currentLesson);
    }

    public String getGradeTitle()
    {
        return getTitle(currentGrade + TITLE_SUFFIX);
    }

    public String getLessonTitle()
    {
        return getTitle(currentLesson);
    }

    public String getLessonYear()
    {
        return getTitle(currentYear + YEAR_SUFFIX);
    }

    private String getTitle(String nlsKey)
    {
        String message = messages.get(nlsKey);
        return postManagement.wrapJapaneseTextInTags(message);
    }



    public boolean isHasLessonPlan()
    {
        LessonPost post = getPost();
        return post != null &&
                StringUtils.isNotBlank(post.getLessonPlanLink());
    }

    public boolean isHasFlashcards()
    {
        LessonPost post = getPost();
        return post != null &&
                StringUtils.isNotBlank(post.getFlashCardsLink());
    }

    public boolean isHasPost()
    {
        LessonPost post = getPost();
        return post != null &&
                post.isVisible();
    }

    public LessonPost getPost()
    {
        return lessonManagement.getPost(currentLesson, currentYear);
    }





    private Page cachedPage;

    public Page getPage()
    {
        if (cachedPage == null)
        {
            cachedPage = (Page) postDAO.getByUuid(Page.LESSON_PLANS_UUID);
        }
        return cachedPage;
    }
}
