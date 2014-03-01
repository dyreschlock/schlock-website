package com.schlock.website.pages;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.Request;

public class Blog
{
    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private Request request;

    @Inject
    private PostDAO postDAO;

    @InjectPage
    private Index index;

    /**
     * /blog/?p=***
     *
     * This is the URL pattern for the archives on the Wordpress
     *  blog I used from 2006 to 2013.  This will take the ID from
     *  the page and redirect to the current entry.
     */
    Object onActivate()
    {
        String wpid = request.getParameter("p");

        Post post = postDAO.getByWpid(wpid);
        if (post != null)
        {
            return linkSource.createPageRenderLinkWithContext(Index.class, post.getUuid());
        }
        return index;
    }
}
