package com.schlock.website.services.database.blog;

import com.schlock.website.model.blog.Category;
import com.schlock.website.model.blog.Post;
import com.schlock.website.services.database.BaseDAO;

import java.util.List;

public interface PostDAO extends BaseDAO<Post>
{
    public Post getMostRecentPost(boolean withUnpublished);

    public Post getNextPost(Post currentPost, boolean withUnpublished, Category category);

    public Post getPreviousPost(Post currentPost, boolean withUnplished, Category category);

    public List<Post> getRecentPosts(boolean withUnpublished);

    public List<Post> getRecentPinnedPosts(boolean withUnpublished);

    public List<Post> getAllPages(boolean withUnpublished);
}
