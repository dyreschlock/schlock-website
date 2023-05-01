package com.schlock.website.services.database.apps.games.impl;

import com.schlock.website.entities.apps.games.Condition;
import com.schlock.website.entities.apps.games.Region;
import com.schlock.website.entities.apps.games.VideoGame;
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

    public List<VideoGame> getByCondition(Condition condition)
    {
        String text = " from VideoGame g " +
                " where g.condition = :cond ";

        Query query = session.createQuery(text);
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
}
