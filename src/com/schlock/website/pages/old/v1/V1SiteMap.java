package com.schlock.website.pages.old.v1;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class V1SiteMap extends AbstractVersion1Page
{
    @Inject
    private PageRenderLinkSource linkSource;

    public String getHomeLink()
    {
        return linkSource.createPageRenderLink(V1Index.class).toURI();
    }

    public String getRadioLink()
    {
        return linkSource.createPageRenderLink(V1Radio.class).toURI();
    }

    public String getHotlineLink()
    {
        return linkSource.createPageRenderLink(V1Hotline.class).toURI();
    }

    public String getGuestBookLink()
    {
        return linkSource.createPageRenderLink(V1GuestBook.class).toURI();
    }

    public String getAnimeLink()
    {
        return linkSource.createPageRenderLink(V1Anime.class).toURI();
    }

    public String getReviewLink()
    {
        return linkSource.createPageRenderLink(V1Review.class).toURI();
    }
}
