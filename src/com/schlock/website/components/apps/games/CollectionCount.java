package com.schlock.website.components.apps.games;

import com.schlock.website.entities.apps.games.*;
import com.schlock.website.pages.apps.games.Index;
import com.schlock.website.services.database.apps.games.VideoGameConsoleDAO;
import com.schlock.website.services.database.apps.games.VideoGameDAO;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CollectionCount
{
    private static final String CONDITION_KEY = "condition";
    private static final String REGION_KEY = "region";

    @Inject
    private VideoGameDAO gameDAO;

    @Inject
    private VideoGameConsoleDAO consoleDAO;

    @Inject
    private Messages messages;

    @Parameter
    private VideoGameConsole currentConsole;


    public boolean isConsoleSelected()
    {
        return currentConsole != null;
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

    public String getConditionTitle()
    {
        return messages.get(CONDITION_KEY);
    }

    public List<DataPanelData> getConditionData()
    {
        List<DataPanelData> data = new ArrayList<DataPanelData>();

        for(Condition condition : Condition.values())
        {
            String name = messages.get(condition.key());
            String count = getConditionCount(condition);
            String link = Index.getPageLink(currentConsole, condition, null);

            data.add(new DataPanelData(name, count, link));
        }
        return data;
    }

    public String getRegionTitle()
    {
        return messages.get(REGION_KEY);
    }

    public List<DataPanelData> getRegionData()
    {
        List<DataPanelData> data = new ArrayList<DataPanelData>();

        for(Region region : Region.values())
        {
            String name = messages.get(region.key());
            String count = getRegionCount(region);
            String link = Index.getPageLink(currentConsole, null, region);

            data.add(new DataPanelData(name, count, link));
        }
        return data;
    }


    public String getRegionCount(Region region)
    {
        int count;
        if (currentConsole == null)
        {
            List<VideoGame> games = gameDAO.getByRegion(region);
            count = games.size();
        }
        else
        {
            count = 0;
            for(VideoGame game : currentConsole.getGames())
            {
                if (region.equals(game.getRegion()))
                {
                    count++;
                }
            }
        }
        return Integer.toString(count);
    }

    public String getConditionCount(Condition condition)
    {
        int count;
        if (currentConsole == null)
        {
            List<VideoGame> games = gameDAO.getByCondition(condition);
            count = games.size();
        }
        else
        {
            count = 0;
            for(VideoGame game : currentConsole.getGames())
            {
                if (condition.equals(game.getCondition()))
                {
                    count++;
                }
            }
        }
        return Integer.toString(count);
    }
}
