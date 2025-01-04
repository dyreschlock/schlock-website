package com.schlock.website.components.old.v7;

import com.schlock.website.components.old.AbstractOldArchiveList;
import com.schlock.website.entities.old.SiteVersion;

public class Version7ArchiveList extends AbstractOldArchiveList
{
    protected SiteVersion getVersion()
    {
        return SiteVersion.V7;
    }
}
