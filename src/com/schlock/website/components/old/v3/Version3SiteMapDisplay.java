package com.schlock.website.components.old.v3;

import com.schlock.website.components.old.AbstractOldLinks;
import com.schlock.website.entities.old.SiteVersion;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class Version3SiteMapDisplay extends AbstractOldLinks
{
    @Parameter(required = true)
    @Property
    private String image2;

    @Parameter(required = true)
    @Property
    private String image4;

    public SiteVersion getVersion()
    {
        return SiteVersion.V3;
    }
}
