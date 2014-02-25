package com.schlock.website.services.database.blog;

import com.schlock.website.model.blog.Post;
import com.schlock.website.services.database.BaseDAO;

import java.util.List;

public interface PostDAO extends BaseDAO<Post>
{
    public Post getMostRecentPost(boolean withUnpublished);

    public Post getNextPost(boolean withUnpublished);

    public Post getPreviousPost(boolean withUnplished);

    public List<Post> getRecentPosts(boolean withUnpublished);

    public List<Post> getRecentPinnedPosts(boolean withUnpublished);

    public List<Post> getAllPages(boolean withUnpublished);
}
