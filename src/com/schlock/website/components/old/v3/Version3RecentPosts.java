package com.schlock.website.components.old.v3;

import com.schlock.website.components.old.AbstractOldRecentPosts;
import com.schlock.website.entities.old.SiteVersion;

public class Version3RecentPosts extends AbstractOldRecentPosts
{
    public String getCurrentPostTitle()
    {
        return super.getCurrentPostTitle().toUpperCase();
    }

    public int getPostCountMax()
    {
        return 6;
    }

    protected SiteVersion getVersion()
    {
        return SiteVersion.V3;
    }
}
