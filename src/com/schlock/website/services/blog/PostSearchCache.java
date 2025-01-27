package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.Post;

import java.util.List;

public interface PostSearchCache
{
    String PAGED_CACHED = "pagedCache";


    String createKey(String cache, Object... params);

    void addToPostCache(String key, List<Post> results);

    List<Post> getCachedPosts(String key);
}
