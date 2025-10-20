package com.schlock.website.components.old;

import com.schlock.website.entities.blog.*;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.*;

public abstract class AbstractOldRecentPosts
{
    @Parameter(required = true)
    private String page;

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private PostManagement postManagement;

    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    private PostDAO postDAO;

    @SessionState
    private ViewState viewState;


    @Property
    private Post currentPost;


    abstract protected SiteVersion getVersion();

    public int getPostCountMax()
    {
        return 12;
    }

    public String getPage()
    {
        return page;
    }

    public boolean isProjectsPage()
    {
        return AbstractOldVersionPage.PROJECTS_PAGE.equals(getPage()) ||
                AbstractOldVersionPage.PHOTO_PAGE.equals(getPage());
    }

    private boolean isGamesPage()
    {
        return AbstractOldVersionPage.REVIEWS_PAGE.equals(getPage());
    }


    public List<Post> getPosts()
    {
        String page = getPage();

        boolean unpublished = viewState.isShowUnpublished();
        int count = getPostCountMax();

        List<String> uuids = new ArrayList<>();
        if(isProjectsPage())
        {
             uuids = Arrays.asList("travel", "hida-takayama", "events", "america");
        }
        if (isGamesPage())
        {
            count = 6;

            uuids = Arrays.asList("game-reviews", "film-tv", "books", "anime", "toys");
        }

        Set<Long> categoryIds = new HashSet<>();
        for(String uuid : uuids)
        {
            AbstractCategory cat = categoryDAO.getByUuid(PostCategory.class, uuid);
            categoryIds.add(cat.getId());
        }
        return postDAO.getMostRecentPosts(count, unpublished, null, null, categoryIds);
    }

    public String getCurrentPostLink()
    {
        String uuid = currentPost.getUuid();
        if (getPage() != null)
        {
            return linkSource.createPageRenderLinkWithContext(getVersion().indexClass(), getPage(), uuid).toURI();
        }
        return linkSource.createPageRenderLinkWithContext(getVersion().indexClass(), uuid).toURI();
    }

    public String getCurrentPostTitle()
    {
        return currentPost.getTitle();
    }

    public String getCurrentPostDescription()
    {
        return postManagement.generatePostDescription(currentPost);
    }


    protected PostDAO postDAO()
    {
        return postDAO;
    }
}
