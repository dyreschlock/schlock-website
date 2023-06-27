package com.schlock.website.pages.apps.pocket;

import com.schlock.website.entities.apps.pocket.PocketCore;
import org.apache.tapestry5.ioc.Messages;

import javax.inject.Inject;

public class Index
{
    private static final String POST_UUID = "curated-games-list-analogue-pocket";

    private static final String TITLE_KEY = "title";

    @Inject
    private Messages messages;


    public String getPostUuid()
    {
        return POST_UUID;
    }

    public String getPlainTitle()
    {
        return messages.get(TITLE_KEY);
    }

    public String getTitle()
    {
        return messages.get(TITLE_KEY);
    }





    public String getConsole()
    {
        return PocketCore.CAT_CONSOLE;
    }

    public String getHandheld()
    {
        return PocketCore.CAT_HANDHELD;
    }

    public String getComputer()
    {
        return PocketCore.CAT_COMPUTER;
    }

    public String getArcade()
    {
        return PocketCore.CAT_ARCADE;
    }

    public String getArcadeMulti()
    {
        return PocketCore.CAT_ARCADE_MULTI;
    }
}
