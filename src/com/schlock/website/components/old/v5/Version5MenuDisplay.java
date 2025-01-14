package com.schlock.website.components.old.v5;

import com.schlock.website.components.old.AbstractOldLinks;
import com.schlock.website.entities.old.SiteVersion;
import org.apache.tapestry5.annotations.Parameter;

public class Version5MenuDisplay extends AbstractOldLinks
{
    @Parameter(required = true)
    private String page;

    public SiteVersion getVersion()
    {
        return SiteVersion.V5;
    }
}
