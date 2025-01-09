package com.schlock.website.pages.old.v6;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;

public class V6Index extends AbstractOldVersionPage
{
    private AbstractPost post;
    private String page;
    private Integer pageNumber;


    Object onActivate()
    {
        page = null;
        post = getDefaultPost();
        pageNumber = 1;

        return true;
    }

    Object onActivate(String param)
    {
        page = null;
        post = null;
        pageNumber = 1;

        if (isArchivePage(param))
        {
            page = param;
        }
        else
        {
            post = getPost(param);
        }
        return true;
    }

    Object onActivate(String p1, String p2)
    {
        page = p1;
        post = null;
        pageNumber = Integer.parseInt(p2);

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
        return SiteVersion.V6;
    }
}
