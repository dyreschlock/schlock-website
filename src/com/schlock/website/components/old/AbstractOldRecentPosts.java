package com.schlock.website.components.old;

import com.schlock.website.entities.blog.AbstractCategory;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.PostCategory;
import com.schlock.website.entities.blog.ViewState;
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

import java.util.Collections;
import java.util.List;

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

    public boolean isProjectsPage()
    {
        return AbstractOldVersionPage.PROJECTS_PAGE.equals(page);
    }

    private boolean isGamesPage()
    {
        return AbstractOldVersionPage.REVIEWS_PAGE.equals(page);
    }


    public List<Post> getPosts()
    {
        if(isProjectsPage())
        {
            final int COUNT = 12;
            boolean unpublished = viewState.isShowUnpublished();

            List<Post> posts = postDAO.getMostRecentPosts(COUNT, unpublished, null, null, null);
            return posts;
        }
        if (isGamesPage())
        {
            final int COUNT = 6;
            AbstractCategory cat = categoryDAO.getByUuid(PostCategory.class, AbstractOldVersionPage.REVIEWS_PAGE);
            Long categoryId = cat.getId();

            return postDAO().getMostRecentPosts(COUNT, false, null, null, categoryId);
        }
        return Collections.EMPTY_LIST;
    }

    public String getCurrentPostLink()
    {
        String uuid = currentPost.getUuid();
        return linkSource.createPageRenderLinkWithContext(getVersion().indexClass(), page, uuid).toURI();
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
