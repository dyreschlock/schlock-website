package com.schlock.website.components.old.v1n2;

import com.schlock.website.components.old.AbstractOldPagedPreviousNext;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;
import org.apache.tapestry5.annotations.Parameter;

public class Version1n2PagedPreviousNext extends AbstractOldPagedPreviousNext
{
    @Parameter(required = true)
    private SiteVersion version;

    protected SiteVersion getVersion()
    {
        return version;
    }

    protected String getLinkContext()
    {
        return AbstractOldVersionPage.ARCHIVE_PAGE;
    }

    public boolean isHasNext()
    {
        if (getPost() != null)
        {
            return true;
        }
        return super.isHasNext();
    }

    public String getNextPageLink()
    {
        if (getPost() != null)
        {
            return getPageLink(1);
        }
        return super.getNextPageLink();
    }
}
