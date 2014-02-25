package com.schlock.website.components.blog.content;

import com.schlock.website.entities.blog.Post;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class PostDisplay
{
    @Parameter(required = true)
    @Property
    private Post post;
}
