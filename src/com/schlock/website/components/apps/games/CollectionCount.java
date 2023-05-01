package com.schlock.website.components.apps.games;

import com.schlock.website.entities.apps.games.Condition;
import com.schlock.website.entities.apps.games.Region;
import com.schlock.website.entities.apps.games.VideoGame;
import com.schlock.website.entities.apps.games.VideoGameConsole;
import com.schlock.website.services.database.apps.games.VideoGameConsoleDAO;
import com.schlock.website.services.database.apps.games.VideoGameDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class CollectionCount
{
    @Inject
    private VideoGameDAO gameDAO;

    @Inject
    private VideoGameConsoleDAO consoleDAO;

    @Inject
    private Messages messages;

    @Property
    private Region currentRegion;

    @Property
    private Condition currentCondition;

    @Property
    private Integer currentIndex;

    public String getTotalCount()
    {
        List<VideoGame> games = gameDAO.getAll();

        return Integer.toString(games.size());
    }

    public String getTotalConsoles()
    {
        List<VideoGameConsole> consoles = consoleDAO.getAll();

        int count = 0;
        for(VideoGameConsole console : consoles)
        {
            if (!console.getGames().isEmpty())
            {
                count++;
            }
        }
        return Integer.toString(count);
    }

    public String getAverageGames()
    {
        double totalGames = Double.parseDouble(getTotalCount());
        double totalConsoles = Double.parseDouble(getTotalConsoles());

        double average = totalGames / totalConsoles;

        return new DecimalFormat("#.0#").format(average);
    }

    public List<Region> getRegions()
    {
        return Arrays.asList(Region.values());
    }

    public String getRegionName()
    {
        return messages.get(currentRegion.name().toLowerCase());
    }

    public String getRegionCount()
    {
        List<VideoGame> games = gameDAO.getByRegion(currentRegion);

        return Integer.toString(games.size());
    }

    public List<Condition> getConditions()
    {
        return Arrays.asList(Condition.values());
    }

    public String getConditionName()
    {
        return messages.get(currentCondition.name().toLowerCase());
    }

    public String getConditionCount()
    {
        List<VideoGame> games = gameDAO.getByCondition(currentCondition);

        return Integer.toString(games.size());
    }

    public String getEvenOdd()
    {
        if (currentIndex % 2 == 0)
        {
            return PlatformCount.EVEN;
        }
        return PlatformCount.ODD;
    }
}
