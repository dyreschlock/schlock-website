package com.schlock.website.pages;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.ViewState;
import com.schlock.website.services.blog.ConvertWordpress;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class Index
{
    private static final String UNPUBLISHED_FLAG = "u";

    @Inject
    private ConvertWordpress convertWordpress;

    @Inject
    private PostManagement postManagement;

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private Messages messages;

    @Inject
    private PostDAO postDAO;

    @Inject
    private CategoryDAO categoryDAO;

    @Persist
    private Post currentPost;

    @SessionState
    private ViewState viewState;

    Object onActivate()
    {
        viewState.reset();

        currentPost = postDAO.getMostRecentPost(false, null);

        return true;
    }

    Object onActivate(String parameter)
    {
        if (StringUtils.equals(UNPUBLISHED_FLAG, parameter))
        {
            viewState.setShowUnpublished(true);
        }
        else
        {
            boolean unpublished = viewState.isShowUnpublished();
            Long categoryId = viewState.getCurrentCategoryId();

            Post post = postDAO.getByUuid(parameter);
            if(post == null)
            {
                post = postDAO.getMostRecentPost(unpublished, categoryId);
            }
            currentPost = post;
        }

        return true;
    }

    Object onPassivate()
    {
        if(currentPost != null)
        {
            return new Object[] { currentPost.getUuid() };
        }
        return null;
    }

    public Post getCurrentPost()
    {
        if(currentPost == null)
        {
            currentPost = postDAO.getMostRecentPost(false, null);
        }
        return currentPost;
    }

    public String getPageTitle()
    {
        return messages.get("website-title");
    }



    //@CommitAfter
    void onConvertWordpress()
    {
        //convertWordpress.startProcess();
    }

    @CommitAfter
    void onRegeneratePostHTML()
    {
        postManagement.regenerateAllPostHTML();
    }

    Object onCodejamLink()
    {
        return linkSource.createPageRenderLink(com.schlock.website.pages.codejam.may2012.Index.class);
    }
}
