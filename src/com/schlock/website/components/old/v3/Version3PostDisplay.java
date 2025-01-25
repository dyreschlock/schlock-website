package com.schlock.website.components.old.v3;

import com.schlock.website.components.old.AbstractOldPostDisplay;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.old.SiteVersion;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class Version3PostDisplay extends AbstractOldPostDisplay
{
    @Parameter(required = true)
    @Property
    private String page;

    protected SiteVersion getVersion()
    {
        return SiteVersion.V3;
    }

    public String getDateColor()
    {
        if (Page.ABOUT_ME_UUID.equals(getPost().getUuid()))
        {
            return "#666660";
        }
        return "#0066ff";
    }

    public String getPostDate()
    {
        if (Page.ABOUT_ME_UUID.equals(getPost().getUuid()))
        {
            return "Dyre";
        }
        return super.getPostDate();
    }
}
