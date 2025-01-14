package com.schlock.website.components.old.v4;

import com.schlock.website.components.old.AbstractOldPostDisplay;
import com.schlock.website.entities.old.SiteVersion;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class Version4PhotoPageDisplay extends AbstractOldPostDisplay
{
    @Parameter(required = true)
    @Property
    private String page;

    protected SiteVersion getVersion()
    {
        return SiteVersion.V4;
    }

    protected int getColumnCount()
    {
        return 6;
    }
}
