package com.schlock.website.pages.old.v1;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;

import java.util.Collections;
import java.util.List;

public abstract class AbstractVersion1Page extends AbstractOldVersionPage
{
    public SiteVersion getVersion()
    {
        return SiteVersion.V1;
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
