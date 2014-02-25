package com.schlock.website.components.blog.link;

import com.schlock.website.model.blog.Category;
import com.schlock.website.model.blog.Post;
import com.schlock.website.model.blog.ViewState;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SessionState;

import javax.inject.Inject;

public class NextPostLink
{
    @Parameter(required = true)
    private Post currentPost;

    @Inject
    private PostDAO postDAO;

    @SessionState
    private ViewState viewState;

    public Post getNextPost()
    {
        boolean unpublished = viewState.isShowUnpublished();
        Category category = viewState.getCurrentCategory();

        return postDAO.getNextPost(currentPost, unpublished, category);
    }
}
