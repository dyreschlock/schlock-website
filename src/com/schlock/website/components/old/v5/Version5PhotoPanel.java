package com.schlock.website.components.old.v5;

import com.schlock.website.components.old.AbstractOldPostListingDisplay;
import com.schlock.website.entities.blog.AbstractCategory;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.v5.V5PhotoPopup;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Version5PhotoPanel extends AbstractOldPostListingDisplay
{
    @Inject
    private PostDAO postDAO;

    protected SiteVersion getVersion()
    {
        return SiteVersion.V5;
    }

    public String getPage()
    {
        return null;
    }

    protected Class getIndexClass()
    {
        return V5PhotoPopup.class;
    }

    public List<Post> getPosts()
    {
        Set<Long> categoryIds = new HashSet<>();
        categoryIds.addAll(getCategoryIds());

        List<Post> posts = postDAO.getMostRecentPosts(null, false, null, null, categoryIds);
        return posts;
    }

    public String getCurrentPostNumber()
    {
        if (getCurrentPost().isPost())
        {
            return ((Post) getCurrentPost()).getDisplayNumber();
        }
        return "";
    }

    public String getCurrentPostTitle()
    {
        return getCurrentPost().getTitle().toLowerCase();
    }

    public String getCurrentCategoryClass()
    {
        String name = getCurrentCategoryName().replaceAll(" ", "-");
        return name;
    }

    public String getCurrentCategoryName()
    {
        for(AbstractCategory cat : getCurrentPost().getAllPostCategories())
        {
            if (!cat.isTopCategory())
            {
                return cat.getName().toLowerCase();
            }
        }
        return "";
    }
}
