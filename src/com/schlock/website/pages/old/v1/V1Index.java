package com.schlock.website.pages.old.v1;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionIndex;
import com.schlock.website.services.DeploymentContext;
import org.apache.tapestry5.ioc.annotations.Inject;

public class V1Index extends AbstractOldVersionIndex
{
    @Inject
    private DeploymentContext context;


    private String page;
    private AbstractPost post;


    Object onActivate()
    {
        page = null;
        post = getPost(null);
        return true;
    }

    Object onActivate(String param)
    {
        if(isSpecialPage(param))
        {
            page = param;
            post = null;
        }
        else
        {
            page = null;
            post = getPost(param);
        }
        return true;
    }

    public String getPage()
    {
        return page;
    }

    public AbstractPost getPost()
    {
        return post;
    }

    public SiteVersion getVersion()
    {
        return SiteVersion.V1;
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
}
