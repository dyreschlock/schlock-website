package com.schlock.website.components.apps.pocket;

import com.schlock.website.components.apps.games.DataPanel;
import com.schlock.website.entities.apps.pocket.Device;
import com.schlock.website.entities.apps.pocket.PocketCore;
import com.schlock.website.entities.apps.pocket.PocketGame;
import com.schlock.website.services.apps.pocket.PocketDataService;
import com.schlock.website.services.apps.pocket.PocketImageService;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GamesPanel
{
    private static final String ARCADE_GENERAL = "arcade";

    private static final String BLANK = "blank";

    @Inject
    private PocketDataService pocketDataService;

    @Inject
    private PocketImageService pocketImageService;

    @Inject
    private Messages messages;

    @Parameter
    private Device device;

    @Parameter
    @Property
    private PocketCore core;

    @Parameter
    private String genre;

    @Parameter
    @Property
    private Boolean imageView;

    @Property
    private PocketGame currentGame;

    @Property
    private Integer currentIndex;


    public List<PocketGame> getGames()
    {
        List<PocketGame> games = pocketDataService.getGamesByDeviceCoreGenre(device, core, genre);
        Collections.sort(games, new Comparator<PocketGame>()
        {
            public int compare(PocketGame o1, PocketGame o2)
            {
                return o1.getGameName().compareTo(o2.getGameName());
            }
        });
        return games;
    }

    public boolean isCoreSelected()
    {
        return core != null;
    }

    public String getCorePlatformId()
    {
        if (core == null)
        {
            return BLANK;
        }
        return core.getPlatformId();
    }

    public String getCurrentGameCorePlatformId()
    {
        String name = currentGame.getCore();
        PocketCore core = pocketDataService.getCoreByPlatformId(currentGame.getCore());
        if (core == null || core.isCategoryArcade())
        {
            return ARCADE_GENERAL;
        }
        return core.getPlatformId();
    }

    public String getCurrentGameCoreText()
    {
        String platformId = getCurrentGameCorePlatformId();

        String output = messages.get(platformId);
        if (output.startsWith("[["))
        {
            output = platformId;
        }
        return output;
    }

    public boolean isHasGenre()
    {
        return genre != null;
    }

    public String getGenreText()
    {
        return messages.get(genre);
    }


    public String getEvenOdd()
    {
        if (currentIndex % 2 == 0)
        {
            return DataPanel.EVEN;
        }
        return DataPanel.ODD;
    }


    public String getImageHTML()
    {
        List<PocketGame> games = getGames();

        String outputHTML = pocketImageService.generateImageHTMLFromGames(games);
        return outputHTML;
    }
}
