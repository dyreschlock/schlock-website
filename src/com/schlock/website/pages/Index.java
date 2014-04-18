package com.schlock.website.pages;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.blog.ViewState;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.Arrays;
import java.util.List;

public class Index
{
    private static final String UNPUBLISHED_FLAG = "u";
    private static final List<String> RSS_FLAGS = Arrays.asList("rss", "rss2", "rss.xml", "rss2.xml");

    private static final String EVENT = "events.html";

    @Inject
    private Messages messages;

    @Inject
    private PostDAO postDAO;

    @Inject
    private CategoryDAO categoryDAO;


    @Persist
    private AbstractPost currentPost;



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
        if (StringUtils.equalsIgnoreCase(UNPUBLISHED_FLAG, parameter))
        {
            viewState.setShowUnpublished(true);
        }
        else if (RSS_FLAGS.contains(parameter.toLowerCase()))
        {
            return Feed.class;
        }
        else if (StringUtils.equalsIgnoreCase(Page.ABOUT_ME_UUID, parameter))
        {
            return AboutMe.class;
        }
        else if (StringUtils.equalsIgnoreCase(Page.ALT_MATERIALS_UUID, parameter))
        {
            return Alt.class;
        }
        else if (StringUtils.equalsIgnoreCase(EVENT, parameter))
        {
            return Club.class;
        }
        else
        {
            boolean unpublished = viewState.isShowUnpublished();
            Long categoryId = viewState.getCurrentCategoryId();

            AbstractPost post = postDAO.getByUuid(parameter);
            if (post == null)
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

    public AbstractPost getCurrentPost()
    {
        if(currentPost == null)
        {
            currentPost = postDAO.getMostRecentPost(false, null);
        }
        return currentPost;
    }
}
