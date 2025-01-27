package com.schlock.website.services.impl;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.services.SiteGenerationCache;
import com.schlock.website.services.database.blog.PostDAO;

import java.util.*;

public class SiteGenerationCacheImpl implements SiteGenerationCache
{
    private final PostDAO postDAO;


    private HashMap<String, Set<Long>> postIdCache = new HashMap<>();
    private HashMap<String, List<String>> stringCache = new HashMap<>();

    public SiteGenerationCacheImpl(PostDAO postDAO)
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
        postIdCache.put(key, ids);
    }

    public List<Post> getCachedPosts(String key)
    {
        Set<Long> ids = postIdCache.get(key);
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

    public void addToStringListCache(String key, List<String> results)
    {
        stringCache.put(key, results);
    }

    public List<String> getCachedStringList(String key)
    {
        return stringCache.get(key);
    }
}
