package com.schlock.website.components.old.v2;

import com.schlock.website.components.old.AbstractOldPostListingDisplay;
import com.schlock.website.entities.old.SiteVersion;

public class Version2PostListingDisplay extends AbstractOldPostListingDisplay
{
    protected SiteVersion getVersion()
    {
        return SiteVersion.V2;
    }
}
