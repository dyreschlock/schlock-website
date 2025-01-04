package com.schlock.website.components.old.v7;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.PostCategory;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class Version7CategoryList
{
    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    private PostDAO postDAO;

    @Property
    private PostCategory currentCategory;

    public List<PostCategory> getCategories()
    {
        return categoryDAO.getAllPostCategoriesInAlphaOrder();
    }

    public Integer getPostCount()
    {
        Long categoryId = currentCategory.getId();

        List<Post> posts = postDAO.getMostRecentPosts(null, false, null, null, categoryId);
        return posts.size();
    }
}
