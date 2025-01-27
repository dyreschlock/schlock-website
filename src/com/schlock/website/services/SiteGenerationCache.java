package com.schlock.website.services;

import com.schlock.website.entities.blog.Post;

import java.util.List;

public interface SiteGenerationCache
{
    String TOP_POSTS = "topPosts";

    String YEARLY_MONTHLY_ITERATIONS = "yearlyMonthlyIterations";
    String PAGED_CACHED = "pagedCache";
    String ARCHIVED_POSTS = "archivedPosts";


    void addToPostCache(List<Post> results, String cache, Object... params);

    List<Post> getCachedPosts(String cache, Object... params);


    void addToStringListCache(List<String> results, String cache, Object... params);

    List<String> getCachedStringList(String cache, Object... params);
}
