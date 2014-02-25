package com.schlock.website.components.blog.link;

import com.schlock.website.model.blog.Category;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class CategoryLink
{
    @Parameter(required = true)
    @Property
    private Category category;
}
