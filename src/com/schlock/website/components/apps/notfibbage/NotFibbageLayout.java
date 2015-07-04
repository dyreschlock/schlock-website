package com.schlock.website.components.apps.notfibbage;

import com.schlock.website.services.blog.CssCache;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class NotFibbageLayout
{
    @Parameter(required = true)
    @Property
    private String title;

    @Inject
    private CssCache cssCache;

    @Inject
    private Messages messages;




    public String getCss()
    {
        return cssCache.getAllCss();
    }
}
