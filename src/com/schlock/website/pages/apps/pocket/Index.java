package com.schlock.website.pages.apps.pocket;

import com.schlock.website.entities.apps.pocket.PocketCore;
import com.schlock.website.services.apps.pocket.PocketDataService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;

import javax.inject.Inject;

public class Index
{
    private static final String POST_UUID = "curated-games-list-analogue-pocket";

    private static final String TITLE_KEY = "title";

    @Inject
    private PocketDataService pocketDataService;

    @Inject
    private Messages messages;

    @Property
    private PocketCore selectedCore;

    Object onActivate()
    {
        return onActivate(null);
    }

    Object onActivate(String parameter)
    {
        selectedCore = pocketDataService.getCoreByNamespace(parameter);

        return true;
    }



    public String getPostUuid()
    {
        return POST_UUID;
    }

    public String getPlainTitle()
    {
        String title = messages.get(TITLE_KEY);
        if (selectedCore != null)
        {
            title += " // " + selectedCore.getName();
        }
        return title;
    }

    public String getTitle()
    {
        String title = messages.get(TITLE_KEY);
        if (selectedCore != null)
        {
            title += " // " + selectedCore.getName();
        }
        return title;
    }



    public boolean isCoreSelected()
    {
        return selectedCore != null;
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


    public static String getPageLink(PocketCore core)
    {
        String link = "/apps/pocket";

        if (core != null)
        {
            link += "/" + core.getNamespace();
        }
        return link;
    }
}
