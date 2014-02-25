package com.schlock.website.services.database.blog.impl;

import com.schlock.website.model.blog.Category;
import com.schlock.website.model.blog.Post;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.blog.PostDAO;

import java.util.List;

public class PostDAOImpl extends BaseDAOImpl<Post> implements PostDAO
{
    private static final int TOP_RECENT = 5;

    public Post getMostRecentPost(boolean withUnpublished)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Post getNextPost(Post currentPost, boolean withUnpublished, Category category)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Post getPreviousPost(Post currentPost, boolean withUnplished, Category category)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Post> getRecentPosts(boolean withUnpublished)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Post> getRecentPinnedPosts(boolean withUnpublished)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Post> getAllPages(boolean withUnpublished)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
