package com.schlock.website.components.old.v4;

import com.schlock.website.components.old.AbstractOldPagedPreviousNext;
import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;

public class Version4PagedPreviousNext extends AbstractOldPagedPreviousNext
{
    protected SiteVersion getVersion()
    {
        return SiteVersion.V4;
    }

    protected AbstractPost getPost()
    {
        return null;
    }

    protected Integer getPageNumber()
    {
        if (super.getPost() != null)
        {
            return 0;
        }
        return super.getPageNumber();
    }

    public String getPage()
    {
        if (super.getPost() != null)
        {
            return AbstractOldVersionPage.ARCHIVE_PAGE;
        }
        return super.getPage();
    }
}
