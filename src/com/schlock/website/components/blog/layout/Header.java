package com.schlock.website.components.blog.layout;

import com.schlock.website.entities.blog.AbstractCategory;
import com.schlock.website.entities.blog.PostCategory;
import com.schlock.website.services.database.blog.CategoryDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class Header
{
    @Inject
    private CategoryDAO categoryDAO;

    @Property
    private AbstractCategory currentCategory;


    public List<PostCategory> getCategories()
    {
        return categoryDAO.getTopInOrder();
    }

}
