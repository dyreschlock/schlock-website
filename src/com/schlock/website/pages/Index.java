package com.schlock.website.pages;

import com.schlock.website.entities.blog.Category;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.ViewState;
import com.schlock.website.services.blog.ConvertWordpress;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class Index
{
    private static final String UNPUBLISHED_FLAG = "u";

    @Inject
    private ConvertWordpress convertWordpress;

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private Messages messages;

    @Inject
    private PostDAO postDAO;

    @Persist
    @Property
    private Post currentPost;

    @SessionState
    private ViewState viewState;

    Object onActivate()
    {
        viewState.setCurrentCategory(null);
        viewState.setShowUnpublished(false);

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
            Category category = viewState.getCurrentCategory();

            Post post = postDAO.getByUuid(parameter);
            if(post == null)
            {
                post = postDAO.getMostRecentPost(unpublished, category);
            }
            currentPost = post;
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
