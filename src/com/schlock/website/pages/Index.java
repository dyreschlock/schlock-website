package com.schlock.website.pages;

import com.schlock.website.model.blog.Post;
import com.schlock.website.model.blog.ViewState;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class Index
{
    private static final String UNPUBLISHED_FLAG = "u";

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private Messages messages;

    @Inject
    private PostDAO postDAO;

    private Post currentPost;

    @SessionState
    private ViewState viewState;

    Object onActivate()
    {
        viewState.setCurrentCategory(null);
        viewState.setShowUnpublished(false);

        currentPost = postDAO.getMostRecentPost(false);

        return true;
    }

    Object onActivate(String parameter)
    {
        if (StringUtils.equals(UNPUBLISHED_FLAG, parameter))
        {
            viewState.setShowUnpublished(true);
        }

        return true;
    }

    public Post getPost()
    {
        return currentPost;
    }

    public String getPageTitle()
    {
        return messages.get("website-title");
    }





    Object onCodejamLink()
    {
        return linkSource.createPageRenderLink(com.schlock.website.pages.codejam.may2012.Index.class);
    }
}
