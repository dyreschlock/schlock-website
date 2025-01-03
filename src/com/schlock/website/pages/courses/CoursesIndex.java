package com.schlock.website.pages.courses;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.CourseCategory;
import com.schlock.website.entities.blog.CoursePage;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.services.blog.LessonsManagement;
import com.schlock.website.services.database.blog.CategoryDAO;
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
    private CategoryDAO categoryDAO;

    @Inject
    private PostDAO postDAO;


    @Property
    private CourseCategory currentCategory;

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

    public List<CourseCategory> getCategories()
    {
        return categoryDAO.getCourseInOrder();
    }

    public List<AbstractPost> getCategoryCourses()
    {
        int postCount = 21;
        Long catId = currentCategory.getId();
        boolean unpublished = true;

        List<AbstractPost> posts = postDAO.getAllCoursesByCategory(catId);
        return posts;
    }


    public String getExtraCatCss()
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
