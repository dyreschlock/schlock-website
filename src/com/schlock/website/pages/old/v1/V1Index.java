package com.schlock.website.pages.old.v1;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.PostCategory;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;
import com.schlock.website.services.DeploymentContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

public class V1Index extends AbstractOldVersionPage
{
    @Inject
    private DeploymentContext context;


    private AbstractPost post;
    private String page;
    private Integer pageNumber;


    @Property
    private AbstractPost currentPost;


    Object onActivate()
    {
        page = null;
        post = getDefaultPost();
        pageNumber = 1;

        return true;
    }

    Object onActivate(String param)
    {
        return onActivate(param, "1");
    }

    Object onActivate(String p1, String p2)
    {
        page = null;
        post = null;
        pageNumber = Integer.parseInt(p2);

        if (isPagedPage(p1))
        {
            page = p1;
        }
        else
        {
            post = getDefaultPost();
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

    public PostCategory getCategory()
    {
        if (getPost() == null)
        {
            return getUpdatesCategory();
        }
        return super.getCategory();
    }

    public Integer getPageNumber()
    {
        return pageNumber;
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
