package com.schlock.website.pages.category;

import com.schlock.website.entities.blog.Category;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.ViewState;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.List;

public class CategoryIndex
{
    @SessionState
    private ViewState viewState;


    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    private PostDAO postDAO;


    private Category category;

    @Property
    private Category currentCategory;

    @Property
    private Post currentPost;



    Object onActivate(String parameter)
    {
        category = categoryDAO.getByUuid(parameter);

        return true;
    }

    public List<Post> getPosts()
    {
        int postCount = 20;
        boolean unpublished = viewState.isShowUnpublished();
        Long categoryId = currentCategory.getId();

        List<Post> posts = postDAO.getMostRecentPosts(postCount, unpublished, categoryId);
        return posts;
    }


    private List<Category> cachedCategories;

    public List<Category> getCategories()
    {
        if (cachedCategories == null)
        {
            Long id = category.getId();

            List<Category> categories = new ArrayList<Category>();
            categories.add(category);
            categories.addAll(categoryDAO.getSubInOrder(id));

            cachedCategories = categories;
        }
        return cachedCategories;
    }


    public String getPageTitle()
    {
        if(category != null)
        {
            return category.getName();
        }
        return "";
    }
}
