package com.schlock.website.components.apps.pocket;

import com.schlock.website.entities.apps.pocket.PocketCore;
import com.schlock.website.entities.apps.pocket.PocketGame;
import com.schlock.website.services.apps.pocket.PocketDataService;
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
    private Messages messages;

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
        List<PocketGame> games = pocketDataService.getGamesByCoreGenre(core, genre);
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

    public String getCoreNamespace()
    {
        if (core == null)
        {
            return BLANK;
        }
        return core.getNamespace();
    }

    public String getCurrentGameCoreNamespace()
    {
        String name = currentGame.getCore();
        PocketCore core = pocketDataService.getCoreByNamespace(currentGame.getCore());
        if (core.isCategoryArcade())
        {
            return ARCADE_GENERAL;
        }
        return core.getNamespace();
    }

    public String getCurrentGameCoreText()
    {
        String namespace = getCurrentGameCoreNamespace();

        String output = messages.get(namespace);
        if (output.startsWith("[["))
        {
            output = namespace;
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
            return CorePanel.EVEN;
        }
        return CorePanel.ODD;
    }
}
