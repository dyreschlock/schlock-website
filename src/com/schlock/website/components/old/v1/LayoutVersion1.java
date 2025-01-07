package com.schlock.website.components.old.v1;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.services.blog.CssCache;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class LayoutVersion1
{
    @Parameter(required = true)
    private List<AbstractPost> posts;

    @Parameter(required = true)
    @Property
    private String page;

    @Inject
    private CssCache cssCache;




    public String getPageCss()
    {
        return cssCache.getCssOldVersions(posts, getVersion());
    }

    public SiteVersion getVersion()
    {
        return SiteVersion.V1;
    }
}
