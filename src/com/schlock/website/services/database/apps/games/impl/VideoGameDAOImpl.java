package com.schlock.website.services.database.apps.games.impl;

import com.schlock.website.entities.apps.games.Condition;
import com.schlock.website.entities.apps.games.Region;
import com.schlock.website.entities.apps.games.VideoGame;
import com.schlock.website.entities.apps.games.VideoGameConsole;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.apps.games.VideoGameDAO;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class VideoGameDAOImpl extends BaseDAOImpl<VideoGame> implements VideoGameDAO
{
    public VideoGameDAOImpl(Session session)
    {
        super(VideoGame.class, session);
    }

    public List<VideoGame> getByCondition(Condition condition)
    {
        String text = " from VideoGame g " +
                " where g.condition = :cond ";

        Query query = session.createQuery(text);
        query.setParameter("cond", condition);

        return query.list();
    }

    public List<VideoGame> getByConsoleCondition(VideoGameConsole console, Condition condition)
    {
        String text = " select g " +
                " from VideoGameConsole c " +
                " join c.games g " +
                " where c.id = :consoleId " +
                " and g.condition = :cond ";

        Query query = session.createQuery(text);
        query.setParameter("consoleId", console.getId());
        query.setParameter("cond", condition);

        return query.list();
    }

    public List<VideoGame> getByRegion(Region region)
    {
        String text = " from VideoGame g " +
                " where g.region = :region ";

        Query query = session.createQuery(text);
        query.setParameter("region", region);

        return query.list();
    }

    public List<VideoGame> getByConsoleRegion(VideoGameConsole console, Region region)
    {
        String text = " select g " +
                " from VideoGameConsole c " +
                " join c.games g " +
                " where c.id = :consoleId " +
                " and g.region = :region ";

        Query query = session.createQuery(text);
        query.setParameter("consoleId", console.getId());
        query.setParameter("region", region);

        return query.list();
    }

    public List<String[]> getCountByMostCommonDeveloper(VideoGameConsole console, int maxResults)
    {
        return getCountByParameter("g.developer", console, maxResults);
    }

    public List<String[]> getCountByMostCommonPublisher(VideoGameConsole console, int maxResults)
    {
        return getCountByParameter("g.publisher", console, maxResults);
    }

    public List<String[]> getCountByMostCommonYear(VideoGameConsole console, int maxResults)
    {
        return getCountByParameter("year(g.releaseDate)", console, maxResults);
    }

    private List<String[]> getCountByParameter(String parameter, VideoGameConsole console, int maxResults)
    {
        String text = " select %s, count(g.id) " +
                " from VideoGameConsole c " +
                " join c.games g " +
                " where %s is not null ";

        if (console != null)
        {
            text += " and c.id = :consoleId ";
        }

        text += " group by %s " +
                " order by count(g.id) desc, %s asc ";

        text = String.format(text, parameter, parameter, parameter, parameter);

        Query query = session.createQuery(text);
        query.setMaxResults(maxResults);

        if (console != null)
        {
            query.setParameter("consoleId", console.getId());
        }

        List<Object[]> results = query.list();
        return convertCountResultsToString(results);
    }

    private List<String[]> convertCountResultsToString(List<Object[]> results)
    {
        List<String[]> data = new ArrayList<String[]>();
        for(Object[] result : results)
        {
            String[] d = new String[2];

            d[0] = result[0].toString();
            d[1] = result[1].toString();

            data.add(d);
        }
        return data;
    }
}
