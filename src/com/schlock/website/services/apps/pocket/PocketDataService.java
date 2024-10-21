package com.schlock.website.services.apps.pocket;

import com.schlock.website.entities.apps.games.DataPanelData;
import com.schlock.website.entities.apps.pocket.Device;
import com.schlock.website.entities.apps.pocket.PocketCore;
import com.schlock.website.entities.apps.pocket.PocketGame;

import java.util.List;

public interface PocketDataService
{
    List<PocketGame> getGames();

    List<PocketGame> getGamesByCore(PocketCore core);

    List<PocketGame> getGamesByDeviceCoreGenre(Device device, PocketCore core, String genre);

    List<PocketCore> getCores();

    List<PocketCore> getCoresByCategory(String category);

    List<PocketCore> getCoresByCategory(String cat1, String cat2);

    PocketCore getCoreByPlatformId(String platformId);

    List<String> getGameGenres();

    List<DataPanelData> getCountByMostCommonDeveloper(Device device, PocketCore core, String genre, Integer maxResults);

    List<DataPanelData> getCountByMostCommonPublisher(Device device, PocketCore core, String genre, Integer maxResults);

    List<DataPanelData> getCountByMostCommonYear(Device device, PocketCore core, String genre, Integer maxResults);

}
