package com.schlock.website.components.old.v5;

import com.schlock.website.components.old.AbstractOldPostDisplay;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.old.SiteVersion;

public class Version5PostDisplay extends AbstractOldPostDisplay
{
    public boolean isAboutMe()
    {
        return getPost() != null && Page.ABOUT_ME_UUID.equals(getPost().getUuid());
    }

    protected SiteVersion getVersion()
    {
        return SiteVersion.V5;
    }
}
