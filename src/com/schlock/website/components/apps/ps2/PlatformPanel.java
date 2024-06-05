package com.schlock.website.components.apps.ps2;

import com.schlock.website.entities.apps.ps2.PlaystationPlatform;
import com.schlock.website.pages.apps.ps2.Index;
import com.schlock.website.services.database.apps.ps2.PlaystationGameDAO;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PlatformPanel
{
    private static final String COUNT_MESSAGE = "game-count";

    @Inject
    private PlaystationGameDAO gameDAO;

    @Inject
    private Messages messages;


    @Parameter
    private PlaystationPlatform platform;

    @Parameter
    private PlaystationPlatform selectedPlatform;

    @Parameter
    private String genre;

    @Parameter
    private Boolean imageView;


    public String getImageLink()
    {
        return platform.getImageLink();
    }

    public String getPlatformLink()
    {
        if (platform.equals(selectedPlatform))
        {
            return Index.getPageLink(imageView, null, genre);
        }
        return Index.getPageLink(imageView, platform, genre);
    }

    public String getImageCssClass()
    {
        if (selectedPlatform != null && !platform.equals(selectedPlatform))
        {
            return "dim";
        }
        return "";
    }


    public String getGamesCount()
    {
        final String SPAN_HTML = "<span class=\"totalSubCount\">%s</span>";

        int totalCount = gameDAO.getAvailableGamesByPlatformGenre(platform, null).size();

        String output;
        if (genre != null)
        {
            int count = gameDAO.getAvailableGamesByPlatformGenre(platform, genre).size();
            output = String.format(SPAN_HTML, count) + " / " + totalCount;
        }
        else
        {
            output = String.format(SPAN_HTML, totalCount);
        }
        return messages.format(COUNT_MESSAGE, output);
    }
}
