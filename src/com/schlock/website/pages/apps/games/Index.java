package com.schlock.website.pages.apps.games;

import com.schlock.website.entities.apps.games.VideoGameConsole;
import com.schlock.website.services.blog.CssCache;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Index
{
    @Inject
    private CssCache cssCashe;

    public String getCss()
    {
        return cssCashe.getCssForGames();
    }



    public String getAll()
    {
        return VideoGameConsole.PLATFORM_CO_ALL;
    }

    public String getNintendo()
    {
        return VideoGameConsole.PLATFORM_CO_NINTENDO;
    }

    public String getSony()
    {
        return VideoGameConsole.PLATFORM_CO_SONY;
    }

    public String getSega()
    {
        return VideoGameConsole.PLATFORM_CO_SEGA;
    }

    public String getMicrosoft()
    {
        return VideoGameConsole.PLATFORM_CO_MICROSOFT;
    }
}
