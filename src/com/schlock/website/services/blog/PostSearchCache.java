package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.Post;

import java.util.List;

public interface PostSearchCache
{
    String TOP_POSTS = "topPosts";

    String YEARLY_MONTHLY_ITERATIONS = "yearlyMonthlyIterations";
    String PAGED_CACHED = "pagedCache";
    String ARCHIVED_POSTS = "archivedPosts";

    String createKey(String cache, Object... params);

    void addToPostCache(String key, List<Post> results);

    List<Post> getCachedPosts(String key);

    void addToStringCache(String key, List<String> results);

    List<String> getCachedStrings(String key);
}
