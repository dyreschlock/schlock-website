package com.schlock.website.services.blog.impl;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.services.blog.PostSearchCache;
import com.schlock.website.services.database.blog.PostDAO;

import java.util.*;

public class PostSearchCacheImpl implements PostSearchCache
{
    private final PostDAO postDAO;


    private HashMap<String, Set<Long>> pagedPostsCache = new HashMap<>();

    public PostSearchCacheImpl(PostDAO postDAO)
    {
        this.postDAO = postDAO;
    }

    public String createKey(String cache, Object... params)
    {
        String key = cache;
        for(Object param : params)
        {
            if (param == null)
            {
                key += "null";
            }
            else
            {
                key += param.toString();
            }
        }
        return key;
    }

    public void addToPostCache(String key, List<Post> results)
    {
        Set<Long> ids = new HashSet<>();
        for(Post post : results)
        {
            ids.add(post.getId());
        }
        pagedPostsCache.put(key, ids);
    }

    public List<Post> getCachedPosts(String key)
    {
        Set<Long> ids = pagedPostsCache.get(key);
        if (ids != null)
        {
            if (ids.isEmpty())
            {
                return Collections.EMPTY_LIST;
            }
            return postDAO.getByIds(ids);
        }
        return null;
    }
}
