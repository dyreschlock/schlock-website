package com.schlock.website.services.database.blog;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.services.database.BaseDAO;

import java.util.List;
import java.util.Set;

public interface PostDAO extends BaseDAO<Post>
{
    public Post getByUuid(String uuid);

    public Post getByWpid(String wpid);

    public Post getByMtid(String mtid);

    public Set<String> getAllUuids();

    public Post getMostRecentPost(boolean withUnpublished, Long categoryId);

    public List<Post> getNextPosts(Post currentPost, boolean withUnpublished, Long categoryId);

    public List<Post> getPreviousPosts(Post currentPost, boolean withUnplished, Long categoryId);

    public List<Integer> getPostYears(boolean withUnpublished, Long categoryId);

    public List<Integer> getPostMonths(int year, boolean withUnpublished, Long categoryId);

    public List<Post> getPostsByYearMonth(int year, int month, boolean withUnpublished, Long categoryId);

    public List<Post> getPinnedPostsByYearMonth(int year, int month, boolean withUnpublished, Long categoryId);

    public List<Post> getRecentPosts(boolean withUnpublished, Long categoryId);

    public List<Post> getRecentPinnedPosts(boolean withUnpublished);

    public List<Post> getAllPages(boolean withUnpublished);
}
