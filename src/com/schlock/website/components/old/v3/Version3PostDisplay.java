package com.schlock.website.components.old.v3;

import com.schlock.website.components.old.AbstractOldPostDisplay;
import com.schlock.website.entities.old.SiteVersion;
import org.apache.tapestry5.annotations.Parameter;

public class Version3PostDisplay extends AbstractOldPostDisplay
{
    @Parameter(required = true)
    private String page;

    protected SiteVersion getVersion()
    {
        return SiteVersion.V3;
    }
}
