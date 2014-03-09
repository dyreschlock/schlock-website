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

    public List<Post> getMostRecentPosts(Integer postCount, boolean withUnpublished);

    public List<Post> getNextPosts(Post currentPost, boolean withUnpublished, Long categoryId);

    public List<Post> getPreviousPosts(Post currentPost, boolean withUnplished, Long categoryId);

    public List<Post> getRecentPostsByYearMonth(Integer postCount, Integer year, Integer month, boolean withUnpublished, Long categoryId);

    public List<Post> getRecentPinnedPostsByYearMonth(Integer postCount, Integer year, Integer month, boolean withUnpublished, Long categoryId);

    public List<Object[]> getPublishedPostCounts();

    public List<Object[]> getCategoryPostCounts(boolean withUnpublished);

    public List<Object[]> getYearsMonthPostCounts(boolean withUnpublished);

    public List<Post> getAllPages(boolean withUnpublished);
}
