package com.schlock.website.components.blog.content;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.ViewState;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PostDisplay
{
    @Parameter(required = true)
    @Property
    private Post post;

    @Inject
    private PostDAO postDAO;

    @SessionState
    private ViewState viewState;

    private Post cachedNextPost;
    private Post cachedPreviousPost;


    public boolean isHasNextPost()
    {
        return getNextPost() != null;
    }

    public Post getNextPost()
    {
        if(cachedNextPost == null)
        {
            boolean unpublished = viewState.isShowUnpublished();
            Long categoryId = viewState.getCurrentCategoryId();

            cachedNextPost = postDAO.getNextPost(post, unpublished, categoryId);
        }
        return cachedNextPost;
    }

    public boolean isHasPreviousPost()
    {
        return getPreviousPost() != null;
    }

    public Post getPreviousPost()
    {
        if(cachedPreviousPost == null)
        {
            boolean unpublished = viewState.isShowUnpublished();
            Long categoryId = viewState.getCurrentCategoryId();

            cachedPreviousPost = postDAO.getPreviousPost(post, unpublished, categoryId);
        }
        return cachedPreviousPost;
    }
}
