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



    public String getPage()
    {
        return "";
    }

    public AbstractPost getPost()
    {
        return null;
    }

    public SiteVersion getVersion()
    {
        return SiteVersion.V6;
    }
}
