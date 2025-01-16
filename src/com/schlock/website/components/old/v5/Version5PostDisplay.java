package com.schlock.website.components.old.v5;

import com.schlock.website.components.old.AbstractOldPostDisplay;
import com.schlock.website.entities.old.SiteVersion;

public class Version5PostDisplay extends AbstractOldPostDisplay
{
    protected SiteVersion getVersion()
    {
        return SiteVersion.V5;
    }
}
