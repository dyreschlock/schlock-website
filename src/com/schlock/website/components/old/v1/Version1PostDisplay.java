package com.schlock.website.components.old.v1;

import com.schlock.website.components.old.AbstractOldPostDisplay;
import com.schlock.website.entities.old.SiteVersion;

public class Version1PostDisplay extends AbstractOldPostDisplay
{
    protected SiteVersion getVersion()
    {
        return SiteVersion.V1;
    }

    public boolean isShowPostDetails()
    {
        if (getPhoto() || getProject())
        {
            return false;
        }
        return super.isShowPostDetails();
    }
}
