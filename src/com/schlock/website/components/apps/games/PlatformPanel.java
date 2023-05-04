package com.schlock.website.components.apps.games;

import com.schlock.website.entities.apps.games.Condition;
import com.schlock.website.entities.apps.games.Region;
import com.schlock.website.entities.apps.games.VideoGameConsole;
import com.schlock.website.pages.apps.games.Index;
import com.schlock.website.services.database.apps.games.VideoGameConsoleDAO;
import com.schlock.website.services.database.apps.games.VideoGameDAO;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PlatformPanel
{
    protected static final String EVEN = "even";
    protected static final String ODD = "odd";

    @Inject
    private VideoGameConsoleDAO consoleDAO;

    @Inject
    private VideoGameDAO gameDAO;

    @Parameter(required = true)
    private String platformGroup;

    @Parameter
    private Condition condition;

    @Parameter
    private Region region;


    @Property
    private VideoGameConsole currentConsole;

    @Property
    private Integer currentIndex;

    public boolean isAll()
    {
        return VideoGameConsole.PLATFORM_CO_ALL.equalsIgnoreCase(platformGroup);
    }

    private List<VideoGameConsole> cachedData = null;

    public String getPanelTitle()
    {
        String title = platformGroup;
        if (isAll())
        {
            String html = "<a href=\"%s\">%s</a>";
            String link = Index.getPageLink(null, null, null);

            title = String.format(html, link, title);
        }
        return title;
    }

    public List<VideoGameConsole> getConsoleData()
    {
        if (cachedData == null)
        {
            if (!isAll())
            {
                cachedData = consoleDAO.getByCompany(platformGroup);
            }
            else
            {
                cachedData = consoleDAO.getAll();
                Collections.sort(cachedData, new Comparator<VideoGameConsole>()
                {
                    @Override
                    public int compare(VideoGameConsole o1, VideoGameConsole o2)
                    {
                        Integer c1 = o1.getGames().size();
                        Integer c2 = o2.getGames().size();

                        return c2.compareTo(c1);
                    }
                });
            }
        }
        return cachedData;
    }

    public String getCurrentConsoleNameHTML()
    {
        String html = "<a href=\"%s\">%s</a>";
        String name = currentConsole.getName();
        String link = Index.getPageLink(currentConsole, condition, region);

        return String.format(html, link, name);
    }

    public String getCurrentConsoleCount()
    {
        int count = getGameCount(currentConsole);
        return Integer.toString(count);
    }

    private int getGameCount(VideoGameConsole console)
    {
        int count;
        if (condition != null)
        {
            count = gameDAO.getByConsoleCondition(console, condition).size();
        }
        else if (region != null)
        {
            count = gameDAO.getByConsoleRegion(console, region).size();
        }
        else
        {
            count = console.getGames().size();
        }
        return count;
    }

    public String getTotalCount()
    {
        int count = 0;
        for(VideoGameConsole console : getConsoleData())
        {
            count += getGameCount(console);
        }
        return Integer.toString(count);
    }

    public String getOuterLeftCss()
    {
        String css = "outerLeft ";

        if (currentIndex == getConsoleData().size() - 1)
        {
            css += "outerBottom";
        }
        return css;
    }

    public String getOuterRightCss()
    {
        String css = "outerRight ";

        if (currentIndex == 0)
        {
            css += "outerTop";
        }
        return css;
    }

    public String getEvenOdd()
    {
        if (currentIndex % 2 == 0)
        {
            return EVEN;
        }
        return ODD;
    }
}
