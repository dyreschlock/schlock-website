package com.schlock.website.pages.courses;

import com.schlock.website.entities.blog.*;
import com.schlock.website.services.blog.LessonsManagement;
import com.schlock.website.services.database.blog.KeywordDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import javax.inject.Inject;
import java.util.List;

public class CoursesIndex
{
    @Inject
    private LessonsManagement lessonManagement;

    @Inject
    private KeywordDAO keywordDAO;

    @Inject
    private PostDAO postDAO;


    @Property
    private Keyword currentKeyword;

    @Property
    private Integer currentIndex;

    @Property
    private AbstractPost currentPost;


    @Persist
    @Property
    private CoursePage selectedPage;


    Object onActivate()
    {
        return onActivate(null);
    }

    Object onActivate(String parameter)
    {
        this.selectedPage = null;

        List<AbstractPost> posts = postDAO.getAllByUuid(parameter);
        for(AbstractPost post : posts)
        {
            if (post.isCoursePage())
            {
                this.selectedPage = (CoursePage) post;
            }
        }

        lessonManagement.resetPostCache();
        return true;
    }


    public Page getPage()
    {
        if (selectedPage != null)
        {
            return selectedPage;
        }
        return (Page) postDAO.getByUuid(Page.COURSE_LIST_UUID);
    }

    public boolean isCourseSelected()
    {
        return selectedPage != null;
    }

    public boolean isUseGradeDisplay()
    {
        return selectedPage != null && selectedPage.isLessonCourse();
    }

    public boolean isUseCustomDisplay()
    {
        return selectedPage != null && !selectedPage.isLessonCourse();
    }

    
    public List<Keyword> getCourseKeywords()
    {
        return keywordDAO.getCourseKeywordsInOrder();
    }

    public List<AbstractPost> getCoursesByKeyword()
    {
        int postCount = 21;
        String keyword = currentKeyword.getName();
        boolean unpublished = true;

        List<AbstractPost> posts = postDAO.getAllCoursesByKeyword(keyword);
        return posts;
    }

    public String getExtraCss()
    {
        String extraCss = "";
        if ((currentIndex + 1) % 2 == 0)
        {
            extraCss += " twoColumnLast";
        }
        else
        {
            extraCss += " clr";
        }
        return extraCss;
    }
}
