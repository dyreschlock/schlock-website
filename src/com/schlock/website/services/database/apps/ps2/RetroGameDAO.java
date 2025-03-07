package com.schlock.website.services.database.apps.ps2;

import com.schlock.website.entities.apps.ps2.PlaystationPlatform;
import com.schlock.website.entities.apps.ps2.RetroGame;
import com.schlock.website.services.database.BaseDAO;

import java.util.List;

public interface RetroGameDAO extends BaseDAO<RetroGame>
{
    List<RetroGame> getAllWithSave();

    List<RetroGame> getCombinedAvailableGames();

    List<RetroGame> getCombinedAvailableGamesWithSaves(PlaystationPlatform platform);

    List<RetroGame> getCombinedAvailableGamesByPlatformGenre(PlaystationPlatform platform, String genre);

    List<String[]> getAllGenres(PlaystationPlatform platform);

    List<String[]> getCountWithSavesByMostCommonDeveloper(PlaystationPlatform platform, int maxResults);

    List<String[]> getCountWithSavesByMostCommonPublisher(PlaystationPlatform platform, int maxResults);

    List<String[]> getCountWithSavesByMostCommonYear(PlaystationPlatform platform, int maxResults);

    List<String[]> getCountByMostCommonDeveloper(PlaystationPlatform platform, String genre, int maxResults);

    List<String[]> getCountByMostCommonPublisher(PlaystationPlatform platform, String genre, int maxResults);

    List<String[]> getCountByMostCommonYear(PlaystationPlatform platform, String genre, int maxResults);
}
