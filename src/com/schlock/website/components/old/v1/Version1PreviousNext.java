package com.schlock.website.components.old.v1;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.pages.old.v1.V1Index;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

public class Version1PreviousNext
{
    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private PostManagement postManagement;

    @Inject
    private PostDAO postDAO;

    @Parameter(required = true)
    private AbstractPost post;



    public boolean isHasNext()
    {
        List<AbstractPost> posts = getNextPosts();
        return posts != null && !posts.isEmpty();
    }

    public String getFirstPostLink()
    {
        return "";
    }

    public String getNextPostLink()
    {
        List<AbstractPost> posts = getNextPosts();
        String nextUuid = posts.get(0).getUuid();

        return linkSource.createPageRenderLinkWithContext(V1Index.class, nextUuid).toURI();
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

    public String getMostRecentPostLink()
    {
        return "";
    }

    public String getPreviousPostLink()
    {
        List<AbstractPost> posts = getPreviousPosts();
        String previousUuid = posts.get(0).getUuid();

        return linkSource.createPageRenderLinkWithContext(V1Index.class, previousUuid).toURI();
    }

    private List<AbstractPost> getPreviousPosts()
    {
        return postManagement.getPreviousPosts(post);
    }

    public String getWidth()
    {
        if (isHasNext() && isHasPrevious())
        {
            return "25%";
        }
        return "50%";
    }
}
