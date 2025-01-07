package com.schlock.website.components.old;

import com.schlock.website.entities.blog.*;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AbstractOldPostListingDisplay
{
    private static final String ARCHIVE_PAGE = AbstractOldVersionPage.ARCHIVE_PAGE;
    private static final String PROJECTS_PAGE = AbstractOldVersionPage.PROJECTS_PAGE;
    private static final String GAMES_PAGE = AbstractOldVersionPage.REVIEWS_PAGE;
    private static final String MUSIC_PAGE = AbstractOldVersionPage.MUSIC_PAGE;


    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private Messages messages;

    @Inject
    private DateFormatter dateFormatter;

    @Inject
    private PostManagement postManagement;

    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    private PostDAO postDAO;


    @Parameter(required = true)
    private String page;

    @Property
    private AbstractPost currentPost;

    @Property
    private Integer currentIndex;


    abstract protected SiteVersion getVersion();


    public boolean isArchivePage()
    {
        return ARCHIVE_PAGE.equals(page);
    }

    public boolean isMusicPage()
    {
        return MUSIC_PAGE.equals(page);
    }

    public boolean isPageShowDescription()
    {
        return PROJECTS_PAGE.equals(page) || GAMES_PAGE.equals(page);
    }

    public List<AbstractPost> getPosts()
    {
        if (ARCHIVE_PAGE.equals(page))
        {
            List<Post> posts = postDAO.getAllPublished();

            List<AbstractPost> results = new ArrayList<>();
            results.addAll(posts);
            return results;
        }
        if (PROJECTS_PAGE.equals(page))
        {
            return postDAO.getAllProjectsByCategory(false, null);
        }
        if (GAMES_PAGE.equals(page))
        {
            AbstractCategory cat = categoryDAO.getByUuid(PostCategory.class, GAMES_PAGE);
            Long categoryId = cat.getId();

            List<Post> posts = postDAO.getMostRecentPosts(null, false, null, null, categoryId);

            List<AbstractPost> results = new ArrayList<>();
            results.addAll(posts);
            return results;
        }
        if (MUSIC_PAGE.equals(page))
        {
            List<ClubPost> posts = postDAO.getAllClubPosts(true);

            List<AbstractPost> results = new ArrayList<>();
            results.addAll(posts);
            return results;
        }
        return null;
    }


    public String getPageTitle()
    {
        return messages.get(page);
    }



    public String getCurrentPostDescription()
    {
        return postManagement.generatePostDescription(currentPost);
    }

    public String getCurrentPostLink()
    {
        String uuid = currentPost.getUuid();
        return linkSource.createPageRenderLinkWithContext(getVersion().indexClass(), uuid).toURI();
    }

    public String getCurrentClubDate()
    {
        if(currentPost.isClubPost())
        {
            Date date = ((ClubPost) currentPost).getEventDate();
            return dateFormatter.shortDateFormat(date);
        }
        return "";
    }

    public String getNewLine()
    {
        if (currentIndex != 0 && currentIndex % 2 == 0)
        {
            return "</tr><tr>";
        }
        return "";
    }
}
