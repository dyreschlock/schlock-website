package com.schlock.website.components.old.v4;

import com.schlock.website.components.old.AbstractOldLinks;
import com.schlock.website.entities.old.SiteVersion;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class Version4MenuDisplay extends AbstractOldLinks
{
    @Parameter(required = true)
    @Property
    private String page;

    public SiteVersion getVersion()
    {
        return SiteVersion.V4;
    }
}
