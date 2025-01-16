package com.schlock.website.components.old.v5;

import com.schlock.website.components.old.AbstractOldPagedPreviousNext;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;

public class Version5PagedPreviousNext extends AbstractOldPagedPreviousNext
{
    protected SiteVersion getVersion()
    {
        return SiteVersion.V5;
    }

    protected String getLinkContext()
    {
        return AbstractOldVersionPage.ARCHIVE_PAGE;
    }

    public boolean isHasPrevious()
    {
        if (isPostBasePage())
        {
            return true;
        }
        return super.isHasPrevious();
    }

    public String getPreviousPageLink()
    {
        if (isPostBasePage())
        {
            return getPageLink(1);
        }
        return super.getPreviousPageLink();
    }
}
