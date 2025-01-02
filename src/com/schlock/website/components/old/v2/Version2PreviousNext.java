package com.schlock.website.components.old.v2;

import com.schlock.website.components.old.AbstractOldPreviousNext;
import com.schlock.website.entities.old.SiteVersion;

public class Version2PreviousNext extends AbstractOldPreviousNext
{
    protected SiteVersion getVersion()
    {
        return SiteVersion.V2;
    }
}
