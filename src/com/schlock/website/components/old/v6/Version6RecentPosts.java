package com.schlock.website.components.old.v6;

import com.schlock.website.components.old.AbstractOldRecentPosts;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.services.blog.PostArchiveManagement;

import javax.inject.Inject;
import java.util.List;

public class Version6RecentPosts extends AbstractOldRecentPosts
{
    @Inject
    private PostArchiveManagement archiveManagement;

    public List<Post> getPosts()
    {
        String iter = super.getPage();
        if (iter != null)
        {
            List<Post> posts = archiveManagement.getPosts(iter);
            if (posts.size() > getPostCountMax())
            {
                posts = posts.subList(0, getPostCountMax());
            }
            return posts;
        }
        return super.getPosts();
    }

    protected SiteVersion getVersion()
    {
        return SiteVersion.V6;
    }

    public String getPage()
    {
        return null;
    }

    public int getPostCountMax()
    {
        return 10;
    }
}
