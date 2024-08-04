package com.schlock.website.services.database.apps.ps2.impl;

import com.schlock.website.entities.apps.ps2.PlaystationGame;
import com.schlock.website.entities.apps.ps2.PlaystationPlatform;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.apps.ps2.PlaystationGameDAO;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class PlaystationGameDAOImpl extends BaseDAOImpl<PlaystationGame> implements PlaystationGameDAO
{
    public PlaystationGameDAOImpl(Session session)
    {
        super(PlaystationGame.class, session);
    }

    public PlaystationGame getByGameId(String gameId)
    {
        String text = " select g " +
                        " from PlaystationGame g " +
                        " where g.gameId = :gameId ";

        Query query = session.createQuery(text);
        query.setParameter("gameId", gameId);

        return (PlaystationGame) singleResult(query);
    }

    public PlaystationGame getByGameIdAndFilename(String gameId, String gameName)
    {
        String text = " select g " +
                " from PlaystationGame g " +
                " where g.gameId = :gameId " +
                " and g.gameName = :gameName ";

        Query query = session.createQuery(text);
        query.setParameter("gameId", gameId);
        query.setParameter("gameName", gameName);

        return (PlaystationGame) singleResult(query);
    }

    public List<PlaystationGame> getAllWithSave()
    {
        String text = "select g " +
                        " from PlaystationGame g " +
                        " where g.haveSave is true ";
        
        Query query = session.createQuery(text);

        return query.list();
    }

    public List<PlaystationGame> getCombinedAvailableGames()
    {
        return getCombinedAvailableGamesByPlatformGenre(null, null);
    }

    public List<PlaystationGame> getCombinedAvailableGamesByPlatformGenre(PlaystationPlatform platform, String genre)
    {
        String text = " select g " +
                        " from PlaystationGame g " +
                        " where g.drive != null " +
                        " and g.subDisc is false ";

        if (platform != null)
        {
            text += " and g.platform = :platform ";
        }
        if (genre != null)
        {
            text += " and g.genre = :genre ";
        }

        text += " order by g.title asc ";

        Query query = session.createQuery(text);

        if (platform != null)
        {
            query.setParameter("platform", platform);
        }
        if (genre != null)
        {
            query.setParameter("genre", genre);
        }

        return query.list();
    }

    public List<PlaystationGame> getCombinedAvailableGamesWithSaves(PlaystationPlatform platform)
    {
        String text = "select g " +
                " from PlaystationGame g " +
                " where g.drive != null " +
                " and g.subDisc is false " +
                " and g.haveSave is true ";

        if (platform != null)
        {
            text += " and g.platform = :platform ";
        }

        text += " order by g.title asc ";

        Query query = session.createQuery(text);
        if (platform != null)
        {
            query.setParameter("platform", platform);
        }

        return query.list();
    }

    public List<String[]> getAllGenres(PlaystationPlatform platform)
    {
        String text = " select g.genre, count(g.id) " +
                        " from PlaystationGame g " +
                        " where g.drive != null " +
                        " and g.subDisc is false " +
                        " and g.genre != null ";

        if (platform != null)
        {
            text += " and g.platform = :platform ";
        }

        text += " group by g.genre ";
        text += " order by g.genre asc ";

        Query query = session.createQuery(text);

        if (platform != null)
        {
            query.setParameter("platform", platform);
        }

        List<Object[]> results = query.list();
        return convertCountResultsToString(results);
    }

    public List<String[]> getCountWithSavesByMostCommonDeveloper(PlaystationPlatform platform, int maxResults)
    {
        return getCountByParameter("g.developer", platform, null, true, maxResults);
    }

    public List<String[]> getCountWithSavesByMostCommonPublisher(PlaystationPlatform platform, int maxResults)
    {
        return getCountByParameter("g.publisher", platform, null, true, maxResults);
    }

    public List<String[]> getCountWithSavesByMostCommonYear(PlaystationPlatform platform, int maxResults)
    {
        return getCountByParameter("year(g.releaseDate)", platform, null, true, maxResults);
    }

    public List<String[]> getCountByMostCommonDeveloper(PlaystationPlatform platform, String genre, int maxResults)
    {
        return getCountByParameter("g.developer", platform, genre, false, maxResults);
    }

    public List<String[]> getCountByMostCommonPublisher(PlaystationPlatform platform, String genre, int maxResults)
    {
        return getCountByParameter("g.publisher", platform, genre, false, maxResults);
    }

    public List<String[]> getCountByMostCommonYear(PlaystationPlatform platform, String genre, int maxResults)
    {
        return getCountByParameter("year(g.releaseDate)", platform, genre, false, maxResults);
    }

    private List<String[]> getCountByParameter(String parameter, PlaystationPlatform platform, String genre, boolean saves, int maxResults)
    {
        String text = " select %s, count(g.id) " +
                " from PlaystationGame g " +
                " where %s is not null " +
                " and g.subDisc is false " +
                " and g.drive != null ";

        if (platform != null)
        {
            text += " and g.platform = :platform ";
        }
        if (genre != null)
        {
            text += " and g.genre = :genre ";
        }
        if (saves)
        {
            text += " and g.haveSave is true ";
        }

        text += " group by %s " +
                " order by count(g.id) desc, %s asc ";

        text = String.format(text, parameter, parameter, parameter, parameter);

        Query query = session.createQuery(text);
        query.setMaxResults(maxResults);

        if (platform != null)
        {
            query.setParameter("platform", platform);
        }
        if (genre != null)
        {
            query.setParameter("genre", genre);
        }

        List<Object[]> results = query.list();
        return convertCountResultsToString(results);
    }
}
