package com.schlock.website.components.old.v1;

import com.schlock.website.components.old.AbstractOldPostListingDisplay;
import com.schlock.website.entities.old.SiteVersion;

public class Version1PostListingDisplay extends AbstractOldPostListingDisplay
{
    protected SiteVersion getVersion()
    {
        return SiteVersion.V1;
    }
}
