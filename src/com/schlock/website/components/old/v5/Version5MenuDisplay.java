package com.schlock.website.components.old.v5;

import com.schlock.website.components.old.AbstractOldLinks;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;
import org.apache.tapestry5.annotations.Parameter;

public class Version5MenuDisplay extends AbstractOldLinks
{
    @Parameter(required = true)
    private String page;


    public boolean isArchivePage()
    {
        return AbstractOldVersionPage.ARCHIVE_PAGE.equals(page);
    }

    public boolean isPhotoPage()
    {
        return AbstractOldVersionPage.PHOTO_PAGE.equals(page);
    }

    public boolean isClubPage()
    {
        return AbstractOldVersionPage.CLUB_PAGE.equals(page);
    }

    public boolean isReviewsPage()
    {
        return AbstractOldVersionPage.REVIEWS_PAGE.equals(page);
    }

    public boolean isSiteMapPage()
    {
        return AbstractOldVersionPage.SITE_MAP_PAGE.equals(page);
    }

    public boolean isNotPhotoAndClubPage()
    {
        return !isPhotoPage() && !isClubPage();
    }


    public SiteVersion getVersion()
    {
        return SiteVersion.V5;
    }
}
