package com.schlock.website.components.old.v5;

import com.schlock.website.entities.blog.ClubPost;
import com.schlock.website.pages.old.AbstractOldVersionPage;
import com.schlock.website.pages.old.v5.V5Index;
import com.schlock.website.pages.old.v5.V5PhotoPopup;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

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
    private DateFormatter dateFormatter;

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private ImageManagement imageManagement;

    @Inject
    private PostDAO postDAO;


    @Property
    private ClubPost currentPost;

    @Property
    private Integer currentIndex;


    public List<ClubPost> getClubPosts()
    {
        List<ClubPost> results = postDAO.getAllClubPosts(true);
        return results;
    }

    public String getBgColor()
    {
        int index = currentIndex +1;
        if (index % 2 == 0)
        {
            return "f9f9f9";
        }
        return "";
    }

    public String getCurrentPostLink()
    {
        String uuid = currentPost.getUuid();
        return linkSource.createPageRenderLinkWithContext(V5PhotoPopup.class, uuid).toURI();
    }

    public String getCurrentEventDate()
    {
        return dateFormatter.lowercaseFormat(currentPost.getEventDate());
    }

    public String getCurrentTitleHTML()
    {
//        <font class="name">sasha</font> + three + jack trash<br>
//        club escape / minneapolis <font class="title">? pictures</font>

        String bold = "<font class=\"name\">%s</font>";
        String newline = "<br/>";
        String photos = " <font class=\"title\">%s pictures</font>";

        String title = currentPost.getTitle();
        int size = imageManagement.getGalleryImages(currentPost).size();


        String html = "";

        html += String.format(bold, title.split("@")[0]);
        html += newline;

        if (title.contains("@"))
        {
            html += title.split("@")[1];
        }

        html += String.format(photos, size);
        return html.toLowerCase();
    }



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
