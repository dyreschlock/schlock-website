package com.schlock.website.components.old.v1;

import com.schlock.website.components.old.AbstractOldPagedPreviousNext;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;

public class Version1PagedPreviousNext extends AbstractOldPagedPreviousNext
{
    protected SiteVersion getVersion()
    {
        return SiteVersion.V1;
    }

    protected String getLinkContext()
    {
        return AbstractOldVersionPage.ARCHIVE_PAGE;
    }
}
