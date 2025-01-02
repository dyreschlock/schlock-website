package com.schlock.website.pages.old.v2;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionIndex;

public class V2Index extends AbstractOldVersionIndex
{

    private AbstractPost post;


    Object onActivate()
    {
        post = getPost(null);

        return true;
    }

    Object onActivate(String param)
    {
        post = getPost(param);

        return true;
    }

    public String getPage()
    {
        return "";
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
