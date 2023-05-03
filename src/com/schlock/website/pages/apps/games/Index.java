package com.schlock.website.pages.apps.games;

import com.schlock.website.entities.apps.games.DataPanelData;
import com.schlock.website.entities.apps.games.VideoGameConsole;
import com.schlock.website.services.blog.CssCache;
import com.schlock.website.services.database.apps.games.VideoGameConsoleDAO;
import com.schlock.website.services.database.apps.games.VideoGameDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.List;

public class Index
{
    private static final String TITLE_KEY = "title";

    private static final int MAX_RESULTS_MAIN = 10;
    private static final int MAX_RESULTS_CONSOLE = 5;

    @Inject
    private CssCache cssCashe;

    @Inject
    private Messages messages;

    @Inject
    private VideoGameConsoleDAO consoleDAO;

    @Inject
    private VideoGameDAO gameDAO;

    @Property
    @Persist
    private VideoGameConsole selectedConsole;

    Object onActivate()
    {
        return onActivate(null);
    }

    Object onActivate(String parameter)
    {
        if (StringUtils.isBlank(parameter))
        {
            selectedConsole = null;
        }
        else
        {
            selectedConsole = consoleDAO.getByCode(parameter);
        }
        return true;
    }


    public String getCss()
    {
        return cssCashe.getCssForGames();
    }

    public String getPlainTitle()
    {
        String title = messages.get(TITLE_KEY);
        if (selectedConsole != null)
        {
            title += " // " + selectedConsole.getName();
        }
        return title;
    }

    public String getTitle()
    {
        String title = messages.get(TITLE_KEY);

        if (selectedConsole != null)
        {
            title = "<a href=\"/apps/games\">" + title + "</a>";

            title += " // ";
            title += "<span class=\"" + selectedConsole.getCode() + "\">" + selectedConsole.getName() + "</span>";
        }
        return title;
    }

    public boolean isConsoleSelected()
    {
        return selectedConsole != null;
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
        List<String[]> devData = gameDAO.getCountByMostCommonDeveloper(selectedConsole, maxResults());
        return createPanelData(devData);
    }

    public List<DataPanelData> getPubData()
    {
        List<String[]> devData = gameDAO.getCountByMostCommonPublisher(selectedConsole, maxResults());
        return createPanelData(devData);
    }

    public List<DataPanelData> getYearData()
    {
        List<String[]> devData = gameDAO.getCountByMostCommonYear(selectedConsole, maxResults());
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
