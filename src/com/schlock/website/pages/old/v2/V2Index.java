package com.schlock.website.pages.old.v2;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionIndex;
import com.schlock.website.services.DeploymentContext;
import org.apache.tapestry5.ioc.annotations.Inject;

public class V2Index extends AbstractOldVersionIndex
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
        return page;
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
        return GAMES_PAGE;
    }

    public String getImageLinkStar1()
    {
        String link = context.webDomain() + "img/old/star1.gif";
        return link;
    }

    public String getImageLinkStar2()
    {
        String link = context.webDomain() + "img/old/star2.gif";
        return link;
    }
}
