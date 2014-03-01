package com.schlock.website.pages.journal;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.pages.Index;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class Archives
{
    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private PostDAO postDAO;

    @InjectPage
    private Index index;

    Object onActivate()
    {
        return index;
    }

    /**
     * /journal/achives/000***.html
     *
     * This is the URL pattern for the archives on the MoveableType
     *  blog I used from 2003 to 2006.  This will take the ID from
     *  the page and redirect to the current entry.
     */
    Object onActivate(String parameter)
    {
        String mtid = parameter.substring(0, parameter.indexOf("."));
        while (mtid.startsWith("0"))
        {
            mtid = mtid.substring(1);
        }


        Post post = postDAO.getByMtid(mtid);
        if (post != null)
        {
            return linkSource.createPageRenderLinkWithContext(Index.class, post.getUuid());
        }
        return index;
    }
}
