package com.schlock.website.components.old;

import com.schlock.website.entities.blog.AbstractPost;
import org.apache.tapestry5.annotations.Parameter;

public class SiteVersion2Template
{
    @Parameter(required = true)
    private AbstractPost post;
}
