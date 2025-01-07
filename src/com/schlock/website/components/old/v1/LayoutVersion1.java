package com.schlock.website.components.old.v1;

import com.schlock.website.entities.old.SiteVersion;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class LayoutVersion1
{
    @Parameter(required = true)
    @Property
    private String page;

    @Parameter(required = true)
    @Property
    private String pageCss;


    public SiteVersion getVersion()
    {
        return SiteVersion.V1;
    }
}
