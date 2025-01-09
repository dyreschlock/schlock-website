package com.schlock.website.components.old.v6;

import com.schlock.website.components.old.AbstractOldArchiveList;
import com.schlock.website.entities.old.SiteVersion;

public class Version6ArchiveList extends AbstractOldArchiveList
{
    protected SiteVersion getVersion()
    {
        return SiteVersion.V6;
    }
}
