package com.schlock.website.services.apps.pocket;

import com.schlock.website.entities.apps.pocket.PocketCore;
import com.schlock.website.entities.apps.pocket.PocketGame;

import java.util.List;

public interface PocketDataService
{
    List<PocketGame> getGames();

    List<PocketGame> getGamesByCore(PocketCore core);

    List<PocketCore> getCores();

    List<PocketCore> getCoresByCategory(String category);


    List<String> getGameGenres();
}
