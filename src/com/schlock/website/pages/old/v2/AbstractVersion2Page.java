package com.schlock.website.pages.old.v2;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;

import java.util.Collections;
import java.util.List;

public abstract class AbstractVersion2Page extends AbstractOldVersionPage
{
    public SiteVersion getVersion()
    {
        return SiteVersion.V2;
    }

    public AbstractPost getPost()
    {
        return null;
    }

    public List<AbstractPost> getPosts()
    {
        return Collections.EMPTY_LIST;
    }

    public String getPage()
    {
        return "";
    }
}
