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

    public List<AbstractPost> getNextRelatedPosts()
    {
        return postManagement.getNextRelatedPosts(post);
    }

    public boolean isHasPreviousRelatedPosts()
    {
        List<AbstractPost> posts = getPreviousRelatedPosts();
        return posts != null && posts.size() > 0;
    }

    public List<AbstractPost> getPreviousRelatedPosts()
    {
        return postManagement.getPreviousRelatedPosts(post);
    }

    public boolean isHasRelatedPosts()
    {
        return isHasNextRelatedPosts() || isHasPreviousRelatedPosts();
    }
}
