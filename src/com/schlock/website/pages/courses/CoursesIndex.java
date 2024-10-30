package com.schlock.website.pages.courses;

import com.schlock.website.entities.blog.CourseCategory;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Property;

import javax.inject.Inject;
import java.util.List;

public class CoursesIndex
{
    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    private PostDAO postDAO;


    @Property
    private CourseCategory currentCategory;

    @Property
    private Post currentPost;


    public List<CourseCategory> getCategories()
    {
        return categoryDAO.getCourseInOrder();
    }

    public List<Post> getCategoryCourses()
    {
        int postCount = 21;
        Long catId = currentCategory.getId();
        boolean unpublished = true;

        List<Post> posts = postDAO.getMostRecentPosts(postCount, unpublished, null, null, catId);
        return posts;
    }






    public Page getPage()
    {
        return (Page) postDAO.getByUuid(Page.COURSE_LIST_UUID);
    }
}
