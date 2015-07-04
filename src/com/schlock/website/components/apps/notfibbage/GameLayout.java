package com.schlock.website.components.apps.notfibbage;

import com.schlock.website.services.blog.CssCache;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class GameLayout
{
    @Inject
    private CssCache cssCache;

    @Inject
    private Messages messages;




    public String getTitle()
    {
        return messages.get("title");
    }

    public String getCss()
    {
        return cssCache.getAllCss();
    }
}
