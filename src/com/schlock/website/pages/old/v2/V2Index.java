package com.schlock.website.pages.old.v2;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionIndex;

public class V2Index extends AbstractOldVersionIndex
{

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
}
