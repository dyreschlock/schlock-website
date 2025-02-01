package com.schlock.website.services.impl;

import com.schlock.website.entities.apps.pokemon.RaidBossPokemon;
import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.SiteGenerationCache;
import com.schlock.website.services.database.blog.PostDAO;

import java.util.*;

public class SiteGenerationCacheImpl implements SiteGenerationCache
{
    private final DeploymentContext context;

    private final PostDAO postDAO;


    private HashMap<String, String> stringCache = new HashMap<>();

    private HashMap<String, List<String>> stringListCache = new HashMap<>();

    private HashMap<String, Object> objectCache = new HashMap<>();

    private HashMap<String, Set<Long>> postIdCache = new HashMap<>();

    private List<RaidBossPokemon> raidPokemonCacheList;
    private Map<String, RaidBossPokemon> raidPokemonCacheMap = new HashMap<>();


    public SiteGenerationCacheImpl(PostDAO postDAO,
                                   DeploymentContext context)
    {
        this.context = context;
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

    public void addToStringListCache(List<String> value, String cache, Object... params)
    {
        String key = createKey(cache, params);
        stringListCache.put(key, value);
    }

    public Object getCachedObject(String cache, Object... params)
    {
        String key = createKey(cache, params);
        return objectCache.get(key);
    }

    public void addToObjectCache(Object value, String cache, Object... params)
    {
        String key = createKey(cache, params);
        objectCache.put(key, value);
    }

    public List<AbstractPost> getCachedAbstractPosts(String cache, Object... params)
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

    public List<Post> getCachedPosts(String cache, Object... params)
    {
        List<AbstractPost> posts = getCachedAbstractPosts(cache, params);
        if (posts != null)
        {
            List<Post> results = new ArrayList<>();
            for(AbstractPost post : posts)
            {
                if (post.isPost())
                {
                    results.add((Post) post);
                }
            }
            return results;
        }
        return null;
    }

    public Set<Long> getCachedPostIds(String cache, Object... params)
    {
        String key = createKey(cache, params);

        Set<Long> ids = new HashSet<>();
        ids.addAll(postIdCache.get(key));

        return ids;
    }

    public void addToPostCache(List<? extends AbstractPost> results, String cache, Object... params)
    {
        Set<Long> ids = new HashSet<>();
        for(AbstractPost post : results)
        {
            ids.add(post.getId());
        }

        String key = createKey(cache, params);
        postIdCache.put(key, ids);
    }


    public RaidBossPokemon getCachedRaidBoss(String nameId)
    {
        return raidPokemonCacheMap.get(nameId);
    }

    public void addPokemonRaidCache(List<RaidBossPokemon> raidBosses)
    {
        if(!context.isCachingPokemonRaidCounters())
        {
            return;
        }

        raidPokemonCacheList = raidBosses;

        raidPokemonCacheMap = new HashMap<>();
        for(RaidBossPokemon pokemon : raidPokemonCacheList)
        {
            raidPokemonCacheMap.put(pokemon.getNameId(), pokemon);
        }
    }

    public List<RaidBossPokemon> getPokemonRaidCache()
    {
        return raidPokemonCacheList;
    }
}
