package com.schlock.website.components.old;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.blog.CssCache;
import com.schlock.website.services.blog.PostManagement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class SiteVersion1Template
{
    @Inject
    private PostManagement postManagement;

    @Inject
    private DeploymentContext context;

    @Inject
    private DateFormatter dateFormatter;

    @Inject
    private CssCache cssCache;


    @Parameter(required = true)
    private AbstractPost post;


    public String getPageCss()
    {
        return cssCache.getCssOldVersions(post, SiteVersion.V1);
    }


    public String getImageLinkPopular()
    {
        String link = context.webDomain() + "img/old/popular.gif";
        return link;
    }

    public String getImageLinkComic()
    {
        String link = context.webDomain() + "img/old/pic4.jpg";
        return link;
    }

    public String getPostTitle()
    {
        return post.getTitle();
    }

    public String getPostBodyHTML()
    {
        String html = postManagement.generatePostHTML(post, SiteVersion.V1);
        return html;
    }

    public boolean isHasDate()
    {
        return post != null && post.getCreated() != null;
    }

    public String getPostDate()
    {
        return dateFormatter.shortDateFormat(post.getCreated());
    }
}
