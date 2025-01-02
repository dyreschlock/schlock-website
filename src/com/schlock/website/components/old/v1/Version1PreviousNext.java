package com.schlock.website.components.old.v1;

import com.schlock.website.components.old.AbstractOldPreviousNext;
import com.schlock.website.entities.old.SiteVersion;

public class Version1PreviousNext extends AbstractOldPreviousNext
{
    protected SiteVersion getVersion()
    {
        return SiteVersion.V1;
    }

    public String getWidth()
    {
        if (isHasNext() && isHasPrevious())
        {
            return "25%";
        }
        return "50%";
    }
}
