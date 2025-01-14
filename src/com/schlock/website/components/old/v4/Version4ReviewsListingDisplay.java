package com.schlock.website.components.old.v4;

import com.schlock.website.components.old.AbstractOldPostListingDisplay;
import com.schlock.website.entities.old.SiteVersion;

public class Version4ReviewsListingDisplay extends AbstractOldPostListingDisplay
{
    protected SiteVersion getVersion()
    {
        return SiteVersion.V4;
    }
}
