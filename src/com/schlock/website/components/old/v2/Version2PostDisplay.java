package com.schlock.website.components.old.v2;

import com.schlock.website.components.old.AbstractOldPostDisplay;
import com.schlock.website.entities.old.SiteVersion;

public class Version2PostDisplay extends AbstractOldPostDisplay
{
    protected SiteVersion getVersion()
    {
        return SiteVersion.V2;
    }
}
