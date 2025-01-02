package com.schlock.website.components.old.v2;

import com.schlock.website.components.old.AbstractOldRecentPosts;
import com.schlock.website.entities.old.SiteVersion;

public class Version2RecentPosts extends AbstractOldRecentPosts
{
    protected SiteVersion getVersion()
    {
        return SiteVersion.V2;
    }
}
