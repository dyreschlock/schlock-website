package com.schlock.website.components.old.v5;

import com.schlock.website.components.old.AbstractOldPostDisplay;
import com.schlock.website.entities.old.SiteVersion;

public class Version5PhotoPageDisplay extends AbstractOldPostDisplay
{
    protected SiteVersion getVersion()
    {
        return SiteVersion.V5;
    }

    public String getPostTitle()
    {
        return super.getPostTitle().toLowerCase();
    }

    public String getPostDate()
    {
        return super.getPostDate().toLowerCase();
    }
}
