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


    public String createKeyPagedCache(Integer postCount, Integer pageNumber, Object... params)
    {
        String key = "";
        if (postCount != null)
        {
            key += postCount;
        }
        else
        {
            key += "null";
        }
        if (pageNumber != null)
        {
            key += pageNumber;
        }
        else
        {
            key += "null";
        }
        for(Object param : params)
        {
            key += param.toString();
        }
        return key;
    }

    public void addToPagedCache(String key, List<Post> results)
    {
        Set<Long> ids = new HashSet<>();
        for(Post post : results)
        {
            ids.add(post.getId());
        }
        pagedPostsCache.put(key, ids);
    }

    public List<Post> getCachedPagedPosts(String key)
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
