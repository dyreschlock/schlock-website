package com.schlock.website.components.old.v7;

import com.schlock.website.components.old.AbstractOldPagedPreviousNext;
import com.schlock.website.entities.old.SiteVersion;

public class Version7PagedPreviousNext extends AbstractOldPagedPreviousNext
{
    protected SiteVersion getVersion()
    {
        return SiteVersion.V7;
    }
}
