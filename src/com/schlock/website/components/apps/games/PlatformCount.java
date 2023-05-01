package com.schlock.website.components.apps.games;

import com.schlock.website.entities.apps.games.VideoGameConsole;
import com.schlock.website.services.database.apps.games.VideoGameConsoleDAO;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.Collections;
import java.util.List;

public class PlatformCount
{
    @Inject
    private VideoGameConsoleDAO consoleDAO;

    @Parameter(required = true)
    @Property
    private String platformGroup;

    @Property
    private VideoGameConsole currentConsole;

    private boolean isAll()
    {
        return VideoGameConsole.PLATFORM_CO_ALL.equalsIgnoreCase(platformGroup);
    }

    public List<VideoGameConsole> getConsoleData()
    {
        if (!isAll())
        {
            return consoleDAO.getByCompany(platformGroup);
        }
        return Collections.EMPTY_LIST;
    }

    public String getCurrentConsoleName()
    {
        return currentConsole.getName();
    }

    public String getCurrentConsoleCount()
    {
        Integer count = currentConsole.getGames().size();

        return count.toString();
    }
}
