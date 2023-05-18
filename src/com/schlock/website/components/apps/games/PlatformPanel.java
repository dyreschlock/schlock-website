package com.schlock.website.components.apps.games;

import com.schlock.website.entities.apps.games.Condition;
import com.schlock.website.entities.apps.games.Region;
import com.schlock.website.entities.apps.games.VideoGamePlatform;
import com.schlock.website.pages.apps.games.Index;
import com.schlock.website.services.database.apps.games.VideoGamePlatformDAO;
import com.schlock.website.services.database.apps.games.VideoGameDAO;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PlatformPanel
{
    protected static final String EVEN = "even";
    protected static final String ODD = "odd";

    @Inject
    private VideoGamePlatformDAO platformDAO;

    @Inject
    private VideoGameDAO gameDAO;

    @Inject
    private Messages messages;

    @Parameter(required = true)
    private String platformGroup;

    @Parameter
    private Condition condition;

    @Parameter
    private Region region;


    @Property
    private VideoGamePlatform currentPlatform;

    @Property
    private Integer currentIndex;

    public boolean isAll()
    {
        return VideoGamePlatform.PLATFORM_CO_ALL.equalsIgnoreCase(platformGroup);
    }

    private List<VideoGamePlatform> cachedData = null;

    public String getPanelTitle()
    {
        String title = messages.get(platformGroup.toLowerCase());
        if (isAll())
        {
            String html = "<a href=\"%s\">%s</a>";
            String link = Index.getPageLink(null, null, null);

            title = String.format(html, link, title);
        }
        return title;
    }

    public List<VideoGamePlatform> getPlatformData()
    {
        if (cachedData == null)
        {
            if (!isAll())
            {
                cachedData = platformDAO.getByCompany(platformGroup);
            }
            else
            {
                cachedData = platformDAO.getAll();
                Collections.sort(cachedData, new Comparator<VideoGamePlatform>()
                {
                    @Override
                    public int compare(VideoGamePlatform o1, VideoGamePlatform o2)
                    {
                        Integer c1 = o1.getGames().size();
                        Integer c2 = o2.getGames().size();

                        return c2.compareTo(c1);
                    }
                });
            }
            cachedData = filterBlank(cachedData);
        }
        return cachedData;
    }

    private List<VideoGamePlatform> filterBlank(List<VideoGamePlatform> platforms)
    {
        List<VideoGamePlatform> filtered = new ArrayList<VideoGamePlatform>();

        for(VideoGamePlatform platform : platforms)
        {
            if (!VideoGamePlatform.PLATFORM_BLANK.equals(platform.getCode()))
            {
                filtered.add(platform);
            }
        }
        return filtered;
    }

    public String getCurrentPlatformNameHTML()
    {
        String html = "<a href=\"%s\">%s</a>";
        String name = currentPlatform.getName();
        String link = Index.getPageLink(currentPlatform, condition, region);

        return String.format(html, link, name);
    }

    public String getCurrentPlatformGameCount()
    {
        int count = getGameCount(currentPlatform);
        return Integer.toString(count);
    }

    private int getGameCount(VideoGamePlatform platform)
    {
        int count;
        if (condition != null)
        {
            count = gameDAO.getByPlatformCondition(platform, condition).size();
        }
        else if (region != null)
        {
            count = gameDAO.getByPlatformRegion(platform, region).size();
        }
        else
        {
            count = platform.getGames().size();
        }
        return count;
    }

    public String getTotalCount()
    {
        int count = 0;
        for(VideoGamePlatform platform : getPlatformData())
        {
            count += getGameCount(platform);
        }
        return Integer.toString(count);
    }

    public String getOuterLeftCss()
    {
        String css = "outerLeft ";

        if (currentIndex == getPlatformData().size() - 1)
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
