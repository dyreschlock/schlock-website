package com.schlock.website.pages.old.v2;

import com.schlock.website.entities.blog.AbstractPost;
import org.apache.tapestry5.annotations.Parameter;

public class V2Index
{
    @Parameter(required = true)
    private AbstractPost post;
}
