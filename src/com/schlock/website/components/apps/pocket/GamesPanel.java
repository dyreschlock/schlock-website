package com.schlock.website.components.apps.pocket;

import com.schlock.website.entities.apps.pocket.PocketCore;
import com.schlock.website.entities.apps.pocket.PocketGame;
import com.schlock.website.services.apps.pocket.PocketDataService;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class GamesPanel
{
    @Inject
    private PocketDataService pocketDataService;

    @Parameter
    @Property
    private PocketCore core;

    @Property
    private PocketGame currentGame;

    @Property
    private Integer currentIndex;


    public List<PocketGame> getGames()
    {
        return pocketDataService.getGamesByCore(core);
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
