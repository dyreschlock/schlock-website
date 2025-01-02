package com.schlock.website.components.old.v1;

import com.schlock.website.components.old.AbstractOldRecentPosts;
import com.schlock.website.entities.old.SiteVersion;

public class Version1RecentPosts extends AbstractOldRecentPosts
{
    protected SiteVersion getVersion()
    {
        return SiteVersion.V1;
    }
}
