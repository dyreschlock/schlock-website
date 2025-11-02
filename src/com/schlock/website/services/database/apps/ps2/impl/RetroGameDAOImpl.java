package com.schlock.website.services.database.apps.ps2.impl;

import com.schlock.website.entities.apps.ps2.PlaystationPlatform;
import com.schlock.website.entities.apps.ps2.RetroGame;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.apps.ps2.RetroGameDAO;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class RetroGameDAOImpl extends BaseDAOImpl<RetroGame> implements RetroGameDAO
{
    public RetroGameDAOImpl(Session session)
    {
        super(RetroGame.class, session);
    }


    public List<RetroGame> getCombinedAvailableGames()
    {
        return getCombinedAvailableGamesByPlatformGenre(null, null);
    }

    public List<RetroGame> getCombinedAvailableGamesByPlatformGenre(PlaystationPlatform platform, String genre)
    {
        String text = " select g " +
                        " from RetroGame g " +
                        " where g.available is true " +
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

    public List<RetroGame> getCombinedAvailableGamesWithSaves(PlaystationPlatform platform)
    {
        String text = "select g " +
                        " from RetroGame g " +
                        " where g.available is true " +
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
                        " from RetroGame g " +
                        " where g.available is true " +
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
                " from RetroGame g " +
                " where %s is not null " +
                " and g.subDisc is false " +
                " and g.available is true ";

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
