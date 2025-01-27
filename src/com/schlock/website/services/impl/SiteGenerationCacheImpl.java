package com.schlock.website.services.impl;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.services.SiteGenerationCache;
import com.schlock.website.services.database.blog.PostDAO;

import java.util.*;

public class SiteGenerationCacheImpl implements SiteGenerationCache
{
    private final PostDAO postDAO;


    private HashMap<String, String> stringCache = new HashMap<>();

    private HashMap<String, List<String>> stringListCache = new HashMap<>();

    private HashMap<String, Set<Long>> postIdCache = new HashMap<>();

    public SiteGenerationCacheImpl(PostDAO postDAO)
    {
        this.postDAO = postDAO;
    }

    private String createKey(String cache, Object... params)
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

    public String getCachedString(String cache, Object... params)
    {
        String key = createKey(cache, params);
        return stringCache.get(key);
    }

    public void addToStringCache(String value, String cache, Object... params)
    {
        String key = createKey(cache, params);
        stringCache.put(key, value);
    }

    public List<String> getCachedStringList(String cache, Object... params)
    {
        String key = createKey(cache, params);
        return stringListCache.get(key);
    }

    public void addToStringListCache(List<String> results, String cache, Object... params)
    {
        String key = createKey(cache, params);
        stringListCache.put(key, results);
    }

    public List<Post> getCachedPosts(String cache, Object... params)
    {
        String key = createKey(cache, params);
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

    public void addToPostCache(List<Post> results, String cache, Object... params)
    {
        Set<Long> ids = new HashSet<>();
        for(Post post : results)
        {
            ids.add(post.getId());
        }

        String key = createKey(cache, params);
        postIdCache.put(key, ids);
    }
}
