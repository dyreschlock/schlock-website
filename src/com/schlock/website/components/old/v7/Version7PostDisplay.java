package com.schlock.website.components.old.v7;

import com.schlock.website.components.old.AbstractOldPostDisplay;
import com.schlock.website.entities.old.SiteVersion;

public class Version7PostDisplay extends AbstractOldPostDisplay
{
    protected SiteVersion getVersion()
    {
        return SiteVersion.V7;
    }
}
