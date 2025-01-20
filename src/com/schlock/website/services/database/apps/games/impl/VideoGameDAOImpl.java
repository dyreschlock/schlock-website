package com.schlock.website.services.database.apps.games.impl;

import com.schlock.website.entities.apps.games.Condition;
import com.schlock.website.entities.apps.games.Region;
import com.schlock.website.entities.apps.games.VideoGame;
import com.schlock.website.entities.apps.games.VideoGamePlatform;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.apps.games.VideoGameDAO;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class VideoGameDAOImpl extends BaseDAOImpl<VideoGame> implements VideoGameDAO
{
    public VideoGameDAOImpl(Session session)
    {
        super(VideoGame.class, session);
    }

    public List<VideoGame> getAll()
    {
        String text = " from VideoGame g " +
                " where g.sold is not true ";

        Query query = session.createQuery(text);
        return query.list();
    }

    public List<VideoGame> getByCondition(Condition condition)
    {
        String text = " from VideoGame g " +
                " where g.sold is not true " +
                " and g.condition = :cond ";

        Query query = session.createQuery(text);
        query.setParameter("cond", condition);

        return query.list();
    }

    public List<VideoGame> getByPlatformCondition(VideoGamePlatform platform, Condition condition)
    {
        String text = " select g " +
                " from VideoGamePlatform p " +
                " join p.games g " +
                " where g.sold is not true " +
                " and p.id = :platformId " +
                " and g.condition = :cond ";

        Query query = session.createQuery(text);
        query.setParameter("platformId", platform.getId());
        query.setParameter("cond", condition);

        return query.list();
    }

    public List<VideoGame> getByRegion(Region region)
    {
        String text = " from VideoGame g " +
                " where g.sold is not true " +
                " and g.region = :region ";

        Query query = session.createQuery(text);
        query.setParameter("region", region);

        return query.list();
    }

    public List<VideoGame> getByPlatformRegion(VideoGamePlatform platform, Region region)
    {
        String text = " select g " +
                " from VideoGamePlatform p " +
                " join p.games g " +
                " where g.sold is not true " +
                " and p.id = :platformId " +
                " and g.region = :region ";

        Query query = session.createQuery(text);
        query.setParameter("platformId", platform.getId());
        query.setParameter("region", region);

        return query.list();
    }

    public List<String[]> getCountByMostCommonDeveloper(VideoGamePlatform console, int maxResults)
    {
        return getCountByParameter("g.developer", console, maxResults);
    }

    public List<String[]> getCountByMostCommonPublisher(VideoGamePlatform console, int maxResults)
    {
        return getCountByParameter("g.publisher", console, maxResults);
    }

    public List<String[]> getCountByMostCommonYear(VideoGamePlatform console, int maxResults)
    {
        return getCountByParameter("year(g.releaseDate)", console, maxResults);
    }

    private List<String[]> getCountByParameter(String parameter, VideoGamePlatform platform, int maxResults)
    {
        String text = " select %s, count(g.id) " +
                " from VideoGamePlatform p " +
                " join p.games g " +
                " where g.sold is not true " +
                " and %s is not null ";

        if (platform != null)
        {
            text += " and p.id = :platformId ";
        }

        text += " group by %s " +
                " order by count(g.id) desc, %s asc ";

        text = String.format(text, parameter, parameter, parameter, parameter);

        Query query = session.createQuery(text);
        query.setMaxResults(maxResults);

        if (platform != null)
        {
            query.setParameter("platformId", platform.getId());
        }

        List<Object[]> results = query.list();
        return convertCountResultsToString(results);
    }
}
