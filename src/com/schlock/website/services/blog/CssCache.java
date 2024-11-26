package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.AbstractPost;

public interface CssCache
{
    String getAllCss(AbstractPost post);

    String getCssForTwitter();
    String getCssForNotFibbage();
    String getCssForGames();
    String getCssForPokemon();
}
