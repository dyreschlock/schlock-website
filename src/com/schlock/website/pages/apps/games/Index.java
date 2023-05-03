package com.schlock.website.pages.apps.games;

import com.schlock.website.entities.apps.games.VideoGameConsole;
import com.schlock.website.services.blog.CssCache;
import com.schlock.website.services.database.apps.games.VideoGameConsoleDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Index
{
    private static final String TITLE_KEY = "title";

    @Inject
    private CssCache cssCashe;

    @Inject
    private Messages messages;

    @Inject
    private VideoGameConsoleDAO consoleDAO;

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
