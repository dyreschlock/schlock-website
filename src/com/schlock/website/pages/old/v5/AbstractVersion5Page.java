package com.schlock.website.pages.old.v5;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;

public abstract class AbstractVersion5Page extends AbstractOldVersionPage
{
    public SiteVersion getVersion()
    {
        return SiteVersion.V5;
    }

    public String getPage()
    {
        return "";
    }

    public AbstractPost getPost()
    {
        return null;
    }
}
