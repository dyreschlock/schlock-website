package com.schlock.website.components.blog.content.lessons;

import com.schlock.website.entities.blog.LessonPost;
import com.schlock.website.pages.Index;
import com.schlock.website.services.blog.LessonsManagement;
import com.schlock.website.services.blog.PostManagement;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

public class LessonGrade
{
    private static final String TITLE_SUFFIX = "-title";
    private static final String YEAR_SUFFIX = "-year";


    @Parameter(required = true)
    @Property
    private String grade;

    @Parameter(required = true)
    @Property
    private String year;

    @Property
    private String currentLesson;

    @Property
    private String currentYear;


    @Inject
    private Messages messages;

    @Inject
    private PostManagement postManagement;

    @Inject
    private LessonsManagement lessonManagement;

    @Inject
    private PageRenderLinkSource linkSource;




    public boolean isFifthOrSixth()
    {
        return StringUtils.equalsIgnoreCase(LessonsManagement.SIXTH_GRADE, grade) ||
                StringUtils.equalsIgnoreCase(LessonsManagement.FIFTH_GRADE, grade);
    }

    public List<String> getLessons()
    {
        return lessonManagement.getLessons(grade);
    }

    public List<String> getYearlyItems()
    {
        return lessonManagement.getYearlyItems(grade);
    }

    public List<String> getSpecialLessons()
    {
        return lessonManagement.getSpecialLessons(grade);
    }

    public List<String> getYears()
    {
        return lessonManagement.getYears(year, currentLesson);
    }


    public String getGradeTitle()
    {
        return getTitle(grade + TITLE_SUFFIX);
    }

    public String getLessonTitle()
    {
        return getTitle(currentLesson);
    }

    public String getLessonYear()
    {
        return getTitle(currentYear + YEAR_SUFFIX);
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

    Object onSelectPost(String uuid)
    {
        return linkSource.createPageRenderLinkWithContext(Index.class, uuid);
    }




    private String getTitle(String nlsKey)
    {
        String message = messages.get(nlsKey);
        return postManagement.wrapJapaneseTextInTags(message);
    }
}
