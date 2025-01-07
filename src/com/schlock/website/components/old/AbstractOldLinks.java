package com.schlock.website.components.old;

import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public abstract class AbstractOldLinks
{
    @Inject
    private PageRenderLinkSource linkSource;

    abstract public SiteVersion getVersion();


    public String getHomeLink()
    {
        return linkSource.createPageRenderLink(getVersion().indexClass()).toURI();
    }

    public String getArchiveLink()
    {
        return getLink(AbstractOldVersionPage.ARCHIVE_PAGE);
    }

    public String getAboutMeLink()
    {
        return getLink(Page.ABOUT_ME_UUID);
    }


    public String getAnimeLink()
    {
        return getLink(AbstractOldVersionPage.ANIME_PAGE);
    }

    public String getGuestBookLink()
    {
        return getLink(AbstractOldVersionPage.GUEST_BOOK_PAGE);
    }

    public String getHotlineLink()
    {
        return getLink(AbstractOldVersionPage.HOTLINE_PAGE);
    }

    public String getMusicLink()
    {
        return getLink(AbstractOldVersionPage.MUSIC_PAGE);
    }

    public String getProjectsLink()
    {
        return getLink(AbstractOldVersionPage.PROJECTS_PAGE);
    }

    public String getRadioLink()
    {
        return getLink(AbstractOldVersionPage.RADIO_PAGE);
    }

    public String getReleasesLink()
    {
        return getLink(AbstractOldVersionPage.RELEASES_PAGE);
    }

    public String getReviewsLink()
    {
        return getLink(AbstractOldVersionPage.REVIEWS_PAGE);
    }

    public String getSiteMapLink()
    {
        return getLink(AbstractOldVersionPage.SITE_MAP_PAGE);
    }

    private String getLink(String page)
    {
        return linkSource.createPageRenderLinkWithContext(getVersion().indexClass(), page).toURI();
    }
}
