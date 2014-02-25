package com.schlock.website.components.blog.link;

import com.schlock.website.model.blog.Post;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class PostLink
{
    @Parameter(required = true)
    @Property
    private Post post;
}
