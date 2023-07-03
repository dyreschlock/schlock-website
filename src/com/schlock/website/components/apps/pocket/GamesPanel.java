package com.schlock.website.components.apps.pocket;

import com.schlock.website.entities.apps.pocket.PocketCore;
import com.schlock.website.entities.apps.pocket.PocketGame;
import com.schlock.website.services.apps.pocket.PocketDataService;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class GamesPanel
{
    @Inject
    private PocketDataService pocketDataService;

    @Inject
    private Messages messages;

    @Parameter
    @Property
    private PocketCore core;

    @Parameter
    private String genre;

    @Property
    private PocketGame currentGame;

    @Property
    private Integer currentIndex;


    public List<PocketGame> getGames()
    {
        return pocketDataService.getGamesByCoreGenre(core, genre);
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
