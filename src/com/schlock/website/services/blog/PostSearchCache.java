package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.Post;

import java.util.List;

public interface PostSearchCache
{
    String createKeyPagedCache(Integer postCount, Integer pageNumber, Object... params);

    void addToPagedCache(String key, List<Post> results);

    List<Post> getCachedPagedPosts(String key);
}
