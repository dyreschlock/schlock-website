package com.schlock.website.components.old.v5;

import com.schlock.website.pages.old.AbstractOldVersionPage;
import com.schlock.website.pages.old.v5.V5Index;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class Version5ClubPanel
{
    public static final String UPCOMING = "upcoming";
    public static final String PHOTO = "photo";
    public static final String PAST = "past";
    public static final String WISHLIST = "wishlist";
    public static final String CHECKLIST = "checklist";


    @Parameter(required = true)
    private String subpage;


    @Inject
    private PageRenderLinkSource linkSource;









    public boolean isUpcomingEventsPage()
    {
        return UPCOMING.equals(subpage);
    }

    public boolean isPhotoPage()
    {
        return PHOTO.equals(subpage);
    }

    public boolean isPastEventsPage()
    {
        return PAST.equals(subpage);
    }

    public boolean isWishlistPage()
    {
        return WISHLIST.equals(subpage);
    }

    public boolean isChecklistPage()
    {
        return CHECKLIST.equals(subpage);
    }


    public String getUpcomingEventsLink()
    {
        return getLink(UPCOMING);
    }

    public String getPhotoLink()
    {
        return getLink(PHOTO);
    }

    public String getPastEventsLink()
    {
        return getLink(PAST);
    }

    public String getWishlistLink()
    {
        return getLink(WISHLIST);
    }

    public String getChecklistLink()
    {
        return getLink(CHECKLIST);
    }

    private String getLink(String subpage)
    {
        return linkSource.createPageRenderLinkWithContext(V5Index.class, AbstractOldVersionPage.CLUB_PAGE, subpage).toURI();
    }
}
