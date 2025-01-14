package com.schlock.website.components.old.v4;

import com.schlock.website.components.old.AbstractOldPostDisplay;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class Version4PostDisplay extends AbstractOldPostDisplay
{
    @Parameter(required = true)
    @Property
    private String page;


    public String getPostDate()
    {
        if(AbstractOldVersionPage.REVIEWS_PAGE.equals(page) || Page.ABOUT_ME_UUID.equals(getPost().getUuid()))
        {
            return getPost().getTitle();
        }
        return super.getPostDate();
    }

    protected SiteVersion getVersion()
    {
        return SiteVersion.V4;
    }
}
