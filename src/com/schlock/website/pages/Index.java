package com.schlock.website.pages;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.blog.ViewState;
import com.schlock.website.pages.apps.SubtitleFixer;
import com.schlock.website.pages.apps.kendo.KendoIndex;
import com.schlock.website.pages.apps.pocket.ImageSelector;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

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

    @Inject
    private PageRenderLinkSource linkSource;


    @Persist
    private AbstractPost currentPost;



    @SessionState
    private ViewState viewState;

    Object onActivate()
    {
        viewState.reset();

        AbstractPost post = postDAO.getMostRecentFrontPagePost();
        return linkSource.createPageRenderLinkWithContext(Index.class, post.getUuid());
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
        else if(StringUtils.equalsIgnoreCase(Llms.FILENAME, parameter))
        {
            return Llms.class;
        }
        else if (StringUtils.equalsIgnoreCase(Page.ABOUT_ME_UUID, parameter))
        {
            return AboutMe.class;
        }
        else if (StringUtils.equalsIgnoreCase(AbstractPost.SUBTITLES_UUID, parameter))
        {
            return SubtitleFixer.class;
        }
        else if (StringUtils.equalsIgnoreCase(AbstractPost.CODEJAM_MAY2012_UUID, parameter))
        {
            return com.schlock.website.pages.codejam.may2012.Index.class;
        }
        else if (StringUtils.equalsIgnoreCase(AbstractPost.KENDO_UUID, parameter))
        {
            return KendoIndex.class;
        }
        else if (StringUtils.equalsIgnoreCase(AbstractPost.POCKET_SELECTOR_UUID, parameter))
        {
            return ImageSelector.class;
        }
        else if (StringUtils.equalsIgnoreCase(EVENT, parameter))
        {
            return Club.class;
        }
        else
        {
            AbstractPost requested = null;

            List<AbstractPost> posts = postDAO.getAllByUuid(parameter);
            for(AbstractPost post : posts)
            {
                if (!post.isCoursePage())
                {
                    requested = post;
                }
            }
            if (posts.isEmpty())
            {
                boolean unpublished = viewState.isShowUnpublished();
                requested = postDAO.getMostRecentPost(unpublished);
            }
            currentPost = requested;
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
            currentPost = postDAO.getMostRecentPost(false);
        }
        return currentPost;
    }
}
