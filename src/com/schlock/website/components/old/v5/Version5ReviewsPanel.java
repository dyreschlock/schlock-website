package com.schlock.website.components.old.v5;

import com.schlock.website.components.old.AbstractOldPostListingDisplay;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.v5.V5ReviewPopup;

public class Version5ReviewsPanel extends AbstractOldPostListingDisplay
{
    protected SiteVersion getVersion()
    {
        return SiteVersion.V5;
    }

    public String getPage()
    {
        return null;
    }

    protected Class getIndexClass()
    {
        return V5ReviewPopup.class;
    }
}
