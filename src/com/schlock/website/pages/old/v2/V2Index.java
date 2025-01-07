package com.schlock.website.pages.old.v2;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;
import com.schlock.website.services.DeploymentContext;
import org.apache.tapestry5.ioc.annotations.Inject;

public class V2Index extends AbstractOldVersionPage
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
        if (isSpecialPage(param))
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
        return "index";
    }

    public AbstractPost getPost()
    {
        return post;
    }

    public SiteVersion getVersion()
    {
        return SiteVersion.V2;
    }


    public String getGamesPage()
    {
        return REVIEWS_PAGE;
    }
}
