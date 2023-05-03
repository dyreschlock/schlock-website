package com.schlock.website.components.apps.games;

import com.schlock.website.entities.apps.games.VideoGameConsole;
import com.schlock.website.services.database.apps.games.VideoGameConsoleDAO;
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

    @Parameter(required = true)
    @Property
    private String platformGroup;

    @Property
    private VideoGameConsole currentConsole;

    @Property
    private Integer currentIndex;

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

        List<VideoGameConsole> all = consoleDAO.getAll();
        Collections.sort(all, new Comparator<VideoGameConsole>()
        {
            @Override
            public int compare(VideoGameConsole o1, VideoGameConsole o2)
            {
                Integer c1 = o1.getGames().size();
                Integer c2 = o2.getGames().size();

                return c2.compareTo(c1);
            }
        });

        return all;
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

    public String getTotalCount()
    {
        int count = 0;
        for(VideoGameConsole console : getConsoleData())
        {
            count += console.getGames().size();
        }

        return Integer.toString(count);
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
