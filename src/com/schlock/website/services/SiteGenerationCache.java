package com.schlock.website.services;

import com.schlock.website.entities.apps.pokemon.RaidBossPokemon;
import com.schlock.website.entities.blog.Post;

import java.util.List;

public interface SiteGenerationCache
{
    String GENERATED_POST_HTML = "generatedPostHTML";

    String YEARLY_MONTHLY_ITERATIONS = "yearlyMonthlyIterations";

    String TOP_POSTS = "topPosts";
    String PAGED_CACHED = "pagedCache";
    String ARCHIVED_POSTS = "archivedPosts";


    String getCachedString(String cache, Object... params);

    void addToStringCache(String value, String cache, Object... params);

    List<String> getCachedStringList(String cache, Object... params);

    void addToStringListCache(List<String> results, String cache, Object... params);

    List<Post> getCachedPosts(String cache, Object... params);

    void addToPostCache(List<Post> results, String cache, Object... params);


    RaidBossPokemon getCachedRaidBoss(String nameId);

    void addPokemonRaidCache(List<RaidBossPokemon> raidBosses);

    List<RaidBossPokemon> getPokemonRaidCache();
}
