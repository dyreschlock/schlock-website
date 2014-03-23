package com.schlock.website.pages;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Alt
{
    @Inject
    private PostDAO postDAO;





    public String getPageTitle()
    {
        return getPage().getTitle();
    }

    private Post cachedPage;

    public Post getPage()
    {
        if (cachedPage == null)
        {
            cachedPage = postDAO.getByUuid(Post.ALT_MATERIALS_UUID);
        }
        return cachedPage;
    }
}
