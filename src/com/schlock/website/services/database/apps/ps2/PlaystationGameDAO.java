package com.schlock.website.services.database.apps.ps2;

import com.schlock.website.entities.apps.ps2.PlaystationGame;
import com.schlock.website.entities.apps.ps2.PlaystationPlatform;
import com.schlock.website.services.database.BaseDAO;

import java.util.List;

public interface PlaystationGameDAO extends BaseDAO<PlaystationGame>
{
    PlaystationGame getByGameId(String gameId);

    PlaystationGame getByGameIdAndFilename(String gameId, String gameName);

    List<PlaystationGame> getAvailableGames();

    List<PlaystationGame> getAvailableGamesByPlatformGenre(PlaystationPlatform platform, String genre);

    List<String[]> getAllGenres();

    List<String[]> getCountByMostCommonDeveloper(PlaystationPlatform platform, String genre, int maxResults);

    List<String[]> getCountByMostCommonPublisher(PlaystationPlatform platform, String genre, int maxResults);

    List<String[]> getCountByMostCommonYear(PlaystationPlatform platform, String genre, int maxResults);


}
