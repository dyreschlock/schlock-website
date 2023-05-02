package com.schlock.website.components.apps.games;

import com.schlock.website.entities.apps.games.Condition;
import com.schlock.website.entities.apps.games.Region;
import com.schlock.website.entities.apps.games.VideoGame;
import com.schlock.website.entities.apps.games.VideoGameConsole;
import com.schlock.website.services.database.apps.games.VideoGameConsoleDAO;
import com.schlock.website.services.database.apps.games.VideoGameDAO;
import org.apache.tapestry5.annotations.Parameter;
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

    @Parameter
    private VideoGameConsole currentConsole;

    @Property
    private Region currentRegion;

    @Property
    private Condition currentCondition;

    @Property
    private Integer currentIndex;


    public boolean isNotConsoleSelected()
    {
        return currentConsole == null;
    }

    public String getTotalCount()
    {
        int count;
        if (currentConsole == null)
        {
            List<VideoGame> games = gameDAO.getAll();
            count = games.size();
        }
        else
        {
            count = currentConsole.getGames().size();
        }
        return Integer.toString(count);
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
        int count;
        if (currentConsole == null)
        {
            List<VideoGame> games = gameDAO.getByRegion(currentRegion);
            count = games.size();
        }
        else
        {
            count = 0;
            for(VideoGame game : currentConsole.getGames())
            {
                if (currentRegion.equals(game.getRegion()))
                {
                    count++;
                }
            }
        }
        return Integer.toString(count);
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
        int count;
        if (currentConsole == null)
        {
            List<VideoGame> games = gameDAO.getByCondition(currentCondition);
            count = games.size();
        }
        else
        {
            count = 0;
            for(VideoGame game : currentConsole.getGames())
            {
                if (currentCondition.equals(game.getCondition()))
                {
                    count++;
                }
            }
        }

        return Integer.toString(count);
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
