package com.schlock.website.components.old.v6;

import com.schlock.website.components.old.AbstractOldRecentPosts;
import com.schlock.website.entities.old.SiteVersion;

public class Version6RecentPosts extends AbstractOldRecentPosts
{
    protected SiteVersion getVersion()
    {
        return SiteVersion.V6;
    }

    public int getPostCountMax()
    {
        return 10;
    }
}
