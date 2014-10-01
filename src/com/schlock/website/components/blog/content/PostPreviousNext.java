package com.schlock.website.components.blog.content;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.services.blog.PostManagement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class PostPreviousNext
{
    @Parameter(required = true)
    @Property
    private AbstractPost post;

    @Inject
    private PostManagement postManagement;

    @Property
    private AbstractPost currentPost;


    public boolean isHasNextPosts()
    {
        List<AbstractPost> posts = getNextPosts();
        return posts != null && posts.size() > 0;
    }

    public List<AbstractPost> getNextPosts()
    {
        return postManagement.getNextPosts(post);
    }

    public boolean isHasPreviousPosts()
    {
        List<AbstractPost> posts = getPreviousPosts();
        return posts != null && posts.size() > 0;
    }

    public List<AbstractPost> getPreviousPosts()
    {
        return postManagement.getPreviousPosts(post);
    }

    public boolean isHasNextRelatedPosts()
    {
        List<AbstractPost> posts = getNextRelatedPosts();
        return posts != null && posts.size() > 0;
    }

    private List<AbstractPost> cachedNextRelatedPosts;

    public List<AbstractPost> getNextRelatedPosts()
    {
        if (cachedNextRelatedPosts == null || cachedNextRelatedPosts.isEmpty())
        {
            cachedNextRelatedPosts = postManagement.getNextRelatedPosts(post);
        }
        return cachedNextRelatedPosts;
    }

    public boolean isHasPreviousRelatedPosts()
    {
        List<AbstractPost> posts = getPreviousRelatedPosts();
        return posts != null && posts.size() > 0;
    }

    private List<AbstractPost> cachedPreviousRelatedPosts;

    public List<AbstractPost> getPreviousRelatedPosts()
    {
        if (cachedPreviousRelatedPosts == null || cachedPreviousRelatedPosts.isEmpty())
        {
            cachedPreviousRelatedPosts = postManagement.getPreviousRelatedPosts(post);
        }
        return cachedPreviousRelatedPosts;
    }

    public boolean isHasRelatedPosts()
    {
        return isHasNextRelatedPosts() || isHasPreviousRelatedPosts();
    }
}
