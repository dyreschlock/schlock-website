package com.schlock.website.components.old.v2;

import com.schlock.website.components.old.AbstractOldRecentPosts;
import com.schlock.website.entities.blog.AbstractCategory;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.PostCategory;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionIndex;
import com.schlock.website.services.database.blog.CategoryDAO;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class Version2RecentPosts extends AbstractOldRecentPosts
{
    @Inject
    private CategoryDAO categoryDAO;

    @Parameter
    private String page;


    protected SiteVersion getVersion()
    {
        return SiteVersion.V2;
    }



    private boolean isGamesPage()
    {
        return AbstractOldVersionIndex.GAMES_PAGE.equals(page);
    }


    public List<Post> getPosts()
    {
        if (isGamesPage())
        {
            final int COUNT = 6;
            AbstractCategory cat = categoryDAO.getByUuid(PostCategory.class, AbstractOldVersionIndex.GAMES_PAGE);
            Long categoryId = cat.getId();

            return postDAO().getMostRecentPosts(COUNT, false, null, null, categoryId);
        }
        return getMostRecentPosts();
    }
}
