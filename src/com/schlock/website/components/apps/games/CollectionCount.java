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

    @Parameter
    private Condition condition;

    @Parameter
    private Region region;

    public boolean isConsoleSelected()
    {
        return currentConsole != null;
    }

    public String getTotalCount()
    {
        final String SPAN_HTML = "<span class=\"totalCount\">%s</span>";

        String output;
        if (currentConsole != null)
        {
            int count = currentConsole.getGames().size();
            if (condition != null)
            {
                int gameCount = gameDAO.getByConsoleCondition(currentConsole, condition).size();
                output = String.format(SPAN_HTML, Integer.toString(gameCount));
                output += " / " + count;
            }
            else if(region != null)
            {
                int gameCount = gameDAO.getByConsoleRegion(currentConsole, region).size();
                output = String.format(SPAN_HTML, Integer.toString(gameCount));
                output += " / " + count;
            }
            else
            {
                output = String.format(SPAN_HTML, Integer.toString(count));
            }
        }
        else
        {
            int totalCount = gameDAO.getAll().size();
            if (condition != null)
            {
                int gameCount = gameDAO.getByCondition(condition).size();
                output = String.format(SPAN_HTML, Integer.toString(gameCount));
                output += " / " + totalCount;
            }
            else if(region != null)
            {
                int gameCount = gameDAO.getByRegion(region).size();
                output = String.format(SPAN_HTML, Integer.toString(gameCount));
                output += " / " + totalCount;
            }
            else
            {
                output = String.format(SPAN_HTML, Integer.toString(totalCount));
            }
        }
        return output;
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
        Integer totalCount = gameDAO.getAll().size();

        double totalGames = totalCount.doubleValue();
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
        final String SPAN_HTML = "<span class=\"bold\">%s</span>";

        List<DataPanelData> data = new ArrayList<DataPanelData>();

        for(Condition condition : Condition.values())
        {
            String name = messages.get(condition.key());
            String count = getConditionCount(condition);
            String link = Index.getPageLink(currentConsole, condition, null);

            if (condition.equals(this.condition))
            {
                name = String.format(SPAN_HTML, name);
                link = Index.getPageLink(currentConsole, null, null);
            }

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
        final String SPAN_HTML = "<span class=\"bold\">%s</span>";

        List<DataPanelData> data = new ArrayList<DataPanelData>();

        for(Region region : Region.values())
        {
            String name = messages.get(region.key());
            String count = getRegionCount(region);
            String link = Index.getPageLink(currentConsole, null, region);

            if (region.equals(this.region))
            {
                name = String.format(SPAN_HTML, name);
                link = Index.getPageLink(currentConsole, null, null);
            }

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
