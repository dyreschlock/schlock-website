package com.schlock.website.services.blog;

public interface CssCache
{
    String getPrimaryCss();
    String getSecondaryCss();

    String getAllCss(String blogPostUUid);

    String getCssForNotFibbage();
    String getCssForGames();
    String getCssForPokemon();
}
