package com.schlock.website.components.blog.layout;

import com.schlock.website.entities.blog.AbstractPost;
import org.apache.tapestry5.annotations.Parameter;

public class MapHeaderScript
{
    @Parameter(required = true)
    private AbstractPost post;

    public boolean isMapPage()
    {
        return post != null && post.isMapPage();
    }
}
