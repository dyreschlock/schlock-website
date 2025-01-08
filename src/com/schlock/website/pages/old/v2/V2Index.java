package com.schlock.website.pages.old.v2;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.PostCategory;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;
import org.apache.tapestry5.annotations.Property;

public class V2Index extends AbstractOldVersionPage
{
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

    public String getCssPage()
    {
        return "index " + getPage();
    }

    public AbstractPost getPost()
    {
        return post;
    }

    public SiteVersion getVersion()
    {
        return SiteVersion.V2;
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

    public String getGamesPage()
    {
        return REVIEWS_PAGE;
    }
}
