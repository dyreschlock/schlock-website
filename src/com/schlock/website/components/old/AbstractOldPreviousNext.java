package com.schlock.website.components.old;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

public abstract class AbstractOldPreviousNext
{
    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private PostManagement postManagement;

    @Inject
    private PostDAO postDAO;

    @Parameter(required = true)
    private AbstractPost post;


    abstract protected SiteVersion getVersion();


    public boolean isNoPost()
    {
        return post == null;
    }

    public String getFirstPostLink()
    {
        AbstractPost post = postManagement.getFirstAvailablePost();
        String uuid = post.getUuid();

        return linkSource.createPageRenderLinkWithContext(getVersion().indexClass(), uuid).toURI();
    }

    public String getMostRecentPostLink()
    {
        AbstractPost post = postManagement.getMostRecentPost();
        String uuid = post.getUuid();

        return linkSource.createPageRenderLinkWithContext(getVersion().indexClass(), uuid).toURI();
    }


    public boolean isHasNext()
    {
        List<AbstractPost> posts = getNextPosts();
        return posts != null && !posts.isEmpty();
    }

    public String getNextPostLink()
    {
        List<AbstractPost> posts = getNextPosts();
        String nextUuid = posts.get(0).getUuid();

        return linkSource.createPageRenderLinkWithContext(getVersion().indexClass(), nextUuid).toURI();
    }

    protected List<AbstractPost> getNextPosts()
    {
        return postManagement.getNextPosts(post);
    }


    public boolean isHasPrevious()
    {
        List<AbstractPost> posts = getPreviousPosts();
        return posts != null && !posts.isEmpty();
    }

    public String getPreviousPostLink()
    {
        List<AbstractPost> posts = getPreviousPosts();
        String previousUuid = posts.get(0).getUuid();

        return linkSource.createPageRenderLinkWithContext(getVersion().indexClass(), previousUuid).toURI();
    }

    protected List<AbstractPost> getPreviousPosts()
    {
        return postManagement.getPreviousPosts(post);
    }
}
