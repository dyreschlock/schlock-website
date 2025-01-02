package com.schlock.website.components.old.v2;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.pages.old.v2.V2Index;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

public class Version2PreviousNext
{
    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private PostManagement postManagement;

    @Inject
    private PostDAO postDAO;


    @Parameter(required = true)
    private AbstractPost post;


    public boolean isNoPost()
    {
        return post == null;
    }

    public String getFirstPostLink()
    {
        AbstractPost post = postManagement.getFirstAvailablePost();
        String uuid = post.getUuid();

        return linkSource.createPageRenderLinkWithContext(V2Index.class, uuid).toURI();
    }

    public String getMostRecentPostLink()
    {
        AbstractPost post = postManagement.getMostRecentPost();
        String uuid = post.getUuid();

        return linkSource.createPageRenderLinkWithContext(V2Index.class, uuid).toURI();
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

        return linkSource.createPageRenderLinkWithContext(V2Index.class, nextUuid).toURI();
    }

    private List<AbstractPost> getNextPosts()
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

        return linkSource.createPageRenderLinkWithContext(V2Index.class, previousUuid).toURI();
    }

    private List<AbstractPost> getPreviousPosts()
    {
        return postManagement.getPreviousPosts(post);
    }
}
