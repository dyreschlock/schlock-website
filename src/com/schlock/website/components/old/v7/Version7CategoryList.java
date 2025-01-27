package com.schlock.website.components.old.v7;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.PostCategory;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.HashMap;
import java.util.List;

public class Version7CategoryList
{
    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    private PostDAO postDAO;

    @Property
    private PostCategory currentCategory;


    private HashMap<Long, Integer> categoryCount = new HashMap<>();


    public List<PostCategory> getCategories()
    {
        return categoryDAO.getAllPostCategoriesInAlphaOrder();
    }

    public Integer getPostCount()
    {
        Long categoryId = currentCategory.getId();
        Integer count = categoryCount.get(categoryId);
        if (count == null)
        {
            List<Post> posts = postDAO.getMostRecentPosts(null, false, null, null, categoryId);
            count = posts.size();

            categoryCount.put(categoryId, count);
        }
        return count;
    }
}
