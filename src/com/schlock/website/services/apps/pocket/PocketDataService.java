package com.schlock.website.services.apps.pocket;

import com.schlock.website.entities.apps.games.DataPanelData;
import com.schlock.website.entities.apps.pocket.PocketCore;
import com.schlock.website.entities.apps.pocket.PocketGame;

import java.util.List;

public interface PocketDataService
{
    List<PocketGame> getGames();

    List<PocketGame> getGamesByCore(PocketCore core);

    List<PocketGame> getGamesByGenre(String genre);

    List<PocketGame> getGamesByCoreGenre(PocketCore core, String genre);

    List<PocketCore> getCores();

    List<PocketCore> getCoresByCategory(String category);

    PocketCore getCoreByNamespace(String namespace);

    List<String> getGameGenres();

    List<DataPanelData> getCountByMostCommonDeveloper(PocketCore core, String genre, Integer maxResults);

    List<DataPanelData> getCountByMostCommonPublisher(PocketCore core, String genre, Integer maxResults);

    List<DataPanelData> getCountByMostCommonYear(PocketCore core, String genre, Integer maxResults);

}
