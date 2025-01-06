package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.old.SiteVersion;

import java.util.List;

public interface CssCache
{
    String getAllCss(AbstractPost post);

    String getCssOldVersions(AbstractPost post, SiteVersion version);
    String getCssOldVersions(List<AbstractPost> post, SiteVersion version);

    String getCssForTwitter();
    String getCssForNotFibbage();
    String getCssForGames();
    String getCssForPokemon();
}
