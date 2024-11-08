package com.schlock.website.services.blog;

public interface CssCache
{
    String getAllCss(String blogPostUUid);

    String getCssForNotFibbage();
    String getCssForGames();
    String getCssForPokemon();
}
