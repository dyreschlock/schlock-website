package com.schlock.website.pages.apps.pocket;

import com.schlock.website.entities.apps.games.DataPanelData;
import com.schlock.website.entities.apps.pocket.PocketCore;
import com.schlock.website.services.apps.pocket.PocketDataService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;

import javax.inject.Inject;
import java.util.List;

public class Index
{
    private static final String POST_UUID = "curated-games-list-analogue-pocket";

    private static final String TITLE_KEY = "title";

    private static final Integer STATS_FULL_MAX_RESULTS = 10;
    private static final Integer STATS_HALF_MAX_RESULTS = 5;

    @Inject
    private PocketDataService pocketDataService;

    @Inject
    private Messages messages;

    @Property
    private PocketCore selectedCore;

    @Property
    private String selectedGenre;

    Object onActivate()
    {
        return onActivate(null);
    }

    Object onActivate(String parameter)
    {
        selectedCore = pocketDataService.getCoreByNamespace(parameter);

        return true;
    }

    Object onActivate(String p1, String p2)
    {
        selectedCore = pocketDataService.getCoreByNamespace(p1);
        selectedGenre = p2;

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
        if (selectedGenre != null)
        {
            title += " // " + selectedGenre;
        }
        return title;
    }

    public String getTitle()
    {
        final String LINK_HTML = "<a href=\"%s\">%s</a>";

        String title = messages.get(TITLE_KEY);
        if (selectedCore != null)
        {
            String link = Index.getPageLink(null, null);

            title = String.format(LINK_HTML, link, title);
        }

        if (selectedGenre != null)
        {
            title += " // " + messages.get(selectedGenre);
        }
        return title;
    }

    public boolean isCoreSelected()
    {
        return selectedCore != null;
    }


    public List<DataPanelData> getDevData()
    {
        return pocketDataService.getCountByMostCommonDeveloper(selectedCore, getMaxResults());
    }

    public List<DataPanelData> getPubData()
    {
        return pocketDataService.getCountByMostCommonPublisher(selectedCore, getMaxResults());
    }

    public List<DataPanelData> getYearData()
    {
        return pocketDataService.getCountByMostCommonYear(selectedCore, getMaxResults());
    }

    private int getMaxResults()
    {
        if (selectedCore != null)
        {
            return STATS_HALF_MAX_RESULTS;
        }
        return STATS_FULL_MAX_RESULTS;
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


    public static String getPageLink(PocketCore core, String genre)
    {
        String link = "/apps/pocket";

        if (core != null)
        {
            link += "/" + core.getNamespace();
        }
        if (genre != null)
        {
            link += "/" + genre;
        }
        return link;
    }
}

