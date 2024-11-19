package com.schlock.website.components.blog.content;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.services.blog.JavaScriptCache;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

public class CustomJavascript
{
    @Inject
    private JavaScriptCache javaScriptCache;

    @Parameter(required = true)
    @Property
    private AbstractPost post;


    public boolean isHasJavascript()
    {
        return javaScriptCache.isHasCustomJavascript(post);
    }

    public String getJavascript()
    {
        return javaScriptCache.getCustomJavascript(post);
    }
}
