package com.schlock.website.pages.apps.games;

import com.schlock.website.entities.apps.games.Condition;
import com.schlock.website.entities.apps.games.DataPanelData;
import com.schlock.website.entities.apps.games.Region;
import com.schlock.website.entities.apps.games.VideoGamePlatform;
import com.schlock.website.services.database.apps.games.VideoGamePlatformDAO;
import com.schlock.website.services.database.apps.games.VideoGameDAO;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.List;

public class Index
{
    private static final String GAMES_POST_UUID = "video-game-collection";

    private static final String TITLE_KEY = "title";

    private static final int MAX_RESULTS_MAIN = 10;
    private static final int MAX_RESULTS_CONSOLE = 5;

    @Inject
    private Messages messages;

    @Inject
    private VideoGamePlatformDAO platformDAO;

    @Inject
    private VideoGameDAO gameDAO;

    @Property
    @Persist
    private VideoGamePlatform selectedPlatform;

    @Property
    @Persist
    private Region selectedRegion;

    @Property
    @Persist
    private Condition selectedCondition;

    Object onActivate()
    {
        return onActivate(null);
    }

    Object onActivate(String parameter)
    {
        selectedPlatform = platformDAO.getByCode(parameter);
        selectedCondition = Condition.parse(parameter);
        selectedRegion = Region.parse(parameter);

        return true;
    }

    Object onActivate(String parameter1, String parameter2)
    {
        selectedPlatform = platformDAO.getByCode(parameter1);
        selectedCondition = Condition.parse(parameter2);
        selectedRegion = Region.parse(parameter2);

        return true;
    }

    public String getGamesPostUuid()
    {
        return GAMES_POST_UUID;
    }

    public String getPlainTitle()
    {
        String title = messages.get(TITLE_KEY);
        if (selectedPlatform != null)
        {
            title += " // " + selectedPlatform.getName();
        }
        if (selectedRegion != null)
        {
            title += " // " + messages.get(selectedRegion.name().toLowerCase());
        }
        if (selectedCondition != null)
        {
            title += " // " + messages.get(selectedCondition.name().toLowerCase());
        }
        return title;
    }

    public String getTitle()
    {
        final String LINK_HTML = "<a href=\"%s\">%s</a>";

        String pageTitle = messages.get(TITLE_KEY);
        if (selectedPlatform != null || selectedCondition != null || selectedRegion != null)
        {
            String link = Index.getPageLink(null, null, null);

            pageTitle = String.format(LINK_HTML, link, pageTitle);
        }

        String titleBar = pageTitle;

        if (selectedPlatform != null)
        {
            String consoleTitle = "<span class=\"%s\">%s</span>";
            consoleTitle = String.format(consoleTitle, selectedPlatform.getCode(), selectedPlatform.getName());

            if (selectedCondition != null || selectedRegion != null)
            {
                String link = Index.getPageLink(selectedPlatform, null, null);

                consoleTitle = String.format(LINK_HTML, link, consoleTitle);
            }

            titleBar += " // " + consoleTitle;
        }
        if (selectedCondition != null)
        {
            titleBar += " // " + messages.get(selectedCondition.key());
        }
        if (selectedRegion != null)
        {
            titleBar += " // " + messages.get(selectedRegion.key());
        }
        return titleBar;
    }

    public boolean isConsoleSelected()
    {
        return selectedPlatform != null;
    }

    public int maxResults()
    {
        if (isConsoleSelected())
        {
            return MAX_RESULTS_CONSOLE;
        }
        return MAX_RESULTS_MAIN;
    }

    public List<DataPanelData> getDevData()
    {
        List<String[]> devData = gameDAO.getCountByMostCommonDeveloper(selectedPlatform, maxResults());
        return createPanelData(devData);
    }

    public List<DataPanelData> getPubData()
    {
        List<String[]> devData = gameDAO.getCountByMostCommonPublisher(selectedPlatform, maxResults());
        return createPanelData(devData);
    }

    public List<DataPanelData> getYearData()
    {
        List<String[]> devData = gameDAO.getCountByMostCommonYear(selectedPlatform, maxResults());
        return createPanelData(devData);
    }

    private List<DataPanelData> createPanelData(List<String[]> results)
    {
        List<DataPanelData> data = new ArrayList<DataPanelData>();
        for(String[] d : results)
        {
            String name = d[0];
            String count = d[1];

            data.add(new DataPanelData(name, count));
        }
        return data;
    }

    public String getAll()
    {
        return VideoGamePlatform.PLATFORM_CO_ALL;
    }

    public String getNintendo()
    {
        return VideoGamePlatform.PLATFORM_CO_NINTENDO;
    }

    public String getSony()
    {
        return VideoGamePlatform.PLATFORM_CO_SONY;
    }

    public String getSega()
    {
        return VideoGamePlatform.PLATFORM_CO_SEGA;
    }

    public String getMicrosoft()
    {
        return VideoGamePlatform.PLATFORM_CO_MICROSOFT;
    }

    public String getOther()
    {
        return VideoGamePlatform.PLATFORM_CO_OTHER;
    }

    public static String getPageLink(VideoGamePlatform console, Condition condition, Region region)
    {
        String link = "/apps/games";

        if (console != null)
        {
            link += "/" + console.getCode();
        }
        if (condition != null)
        {
            link += "/" + condition.key();
        }
        if (region != null)
        {
            link += "/" + region.key();
        }
        return link;
    }
}
