package com.schlock.website.services;

import com.schlock.website.entities.apps.pokemon.RaidBossPokemon;
import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Post;

import java.util.List;
import java.util.Set;

public interface SiteGenerationCache
{
    String GENERATED_POST_HTML = "generatedPostHTML";
    String RAW_POST_HTML = "rawPostHTML";

    String POST_IMAGE_DIRECT_LINK = "postImageDirectLink";
    String HEADER_JAVASCRIPT = "headerJavascript";

    String YEARLY_MONTHLY_ITERATIONS = "yearlyMonthlyIterations";

    String FOOTER_POST_DETAILS = "footerPostDetails";
    String FOOTER_PINNED_DETAILS = "footerPinnedDetails";
    String FOOTER_PAGE_DETAILS = "footerPageDetails";

    String IMAGE_POST_DETAILS = "imagePostDetails";

    String TOP_POSTS = "topPosts";
    String PAGED_CACHED = "pagedCache";
    String ARCHIVED_POSTS = "archivedPosts";

    String NEXT_POSTS = "nextPosts";
    String PREVIOUS_POSTS = "previousPosts";

    String getCachedString(String cache, Object... params);

    void addToStringCache(String value, String cache, Object... params);

    List<String> getCachedStringList(String cache, Object... params);

    void addToStringListCache(List<String> value, String cache, Object... params);

    Object getCachedObject(String cache, Object... params);

    void addToObjectCache(Object value, String cache, Object... params);

    List<Post> getCachedPosts(String cache, Object... params);

    List<AbstractPost> getCachedAbstractPosts(String cache, Object... params);

    Set<Long> getCachedPostIds(String cache, Object... params);

    void addToPostCache(List<? extends AbstractPost> results, String cache, Object... params);


    RaidBossPokemon getCachedRaidBoss(String nameId);

    void addPokemonRaidCache(List<RaidBossPokemon> raidBosses);

    List<RaidBossPokemon> getPokemonRaidCache();
}
