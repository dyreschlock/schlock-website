package com.schlock.website.components.blog;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.services.blog.CssCache;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class LayoutBlog
{
    @Parameter(required = true)
    @Property
    private AbstractPost post;

    @Inject
    private CssCache cssCache;

    @Inject
    private Messages messages;


    public String getTitle()
    {
        String title = messages.get("website-title");

        return title + " // " + post.getTitle();
    }

    public String getPrimaryCss()
    {
        return cssCache.getPrimaryCss();
    }
}
