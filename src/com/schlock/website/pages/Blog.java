package com.schlock.website.pages;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.Request;

public class Blog
{
    private static final String POST_PARAMETER = "p";
    private static final String FEED_PARAMETER = "feed";

    private static final String RSS_PARAMETER = "rss";
    private static final String RSS2_PARAMETER = "rss2";
    private static final String ATOM_PARAMETER = "atom";
    private static final String RDF_PARAMETER = "rdf";

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private Request request;

    @Inject
    private PostDAO postDAO;


    @InjectPage
    private Index index;

    @InjectPage
    private Feed feed;


    /**
     * /blog/?p=***
     *
     * This is the URL pattern for the archives on the Wordpress
     *  blog I used from 2006 to 2013.  *** is the ID of the page.
     *  This maps to the WPID on a Post.
     */

    /**
     * /blog/?feed=rss.xml
     * /blog/?feed=rss2.xml
     * /blog/?feed=rdf.xml
     * /blog/?feed=atom.xml
     *
     * /blog/feed
     * /blog/feed/rss
     * /blog/feed/rss2
     * /blog/feed/rdf
     * /blog/feed/atom
     *
     * These are the URL patterns for the feeds from wordpress.
     */
    Object onActivate()
    {
        String wpid = request.getParameter(POST_PARAMETER);
        if (StringUtils.isNotBlank(wpid))
        {
            AbstractPost post = postDAO.getByWpid(wpid);
            if (post != null)
            {
                return linkSource.createPageRenderLinkWithContext(Index.class, post.getUuid());
            }
        }

        String feedParam = request.getParameter(FEED_PARAMETER);
        if (StringUtils.isNotBlank(feedParam))
        {
            return feed;
        }

        return index;
    }

    Object onActivate(String parameter)
    {
        if (StringUtils.equals(FEED_PARAMETER, parameter))
        {
            return feed;
        }
        return index;
    }

    Object onActivate(String parameter, String parameter2)
    {
        if (StringUtils.equals(FEED_PARAMETER, parameter))
        {
            return feed;
        }
        return index;
    }
}
