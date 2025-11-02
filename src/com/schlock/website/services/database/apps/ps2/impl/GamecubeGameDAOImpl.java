package com.schlock.website.services.database.apps.ps2.impl;

import com.schlock.website.entities.apps.ps2.GamecubeGame;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.apps.ps2.GamecubeGameDAO;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class GamecubeGameDAOImpl extends BaseDAOImpl<GamecubeGame> implements GamecubeGameDAO
{
    public GamecubeGameDAOImpl(Session session)
    {
        super(GamecubeGame.class, session);
    }

    public List<GamecubeGame> getAllAvailable()
    {
        String text = " select g " +
                        " from GamecubeGame g " +
                        " where g.available is true ";

        Query query = session.createQuery(text);
        return query.list();
    }

    public List<GamecubeGame> getAllWithSave()
    {
        String text = "select g " +
                        " from GamecubeGame g " +
                        " where g.haveSave is true ";

        Query query = session.createQuery(text);
        return query.list();
    }
    public List<GamecubeGame> getAllWithNoArt()
    {
        String text = " select g " +
                        " from GamecubeGame g " +
                        " where g.available is true " +
                        " and g.serialNumber is not null " +
                        " and g.haveArt is false ";

        Query query = session.createQuery(text);
        return query.list();
    }

    public GamecubeGame getByGameName(String gameName)
    {
        String text = " select g " +
                        " from GamecubeGame g " +
                        " where g.gameName = :gameName ";

        Query query = session.createQuery(text);
        query.setParameter("gameName", gameName);

        return (GamecubeGame) singleResult(query);
    }

    public GamecubeGame getBySerialNumber(String serialNumber)
    {
        String text = " select g " +
                        " from GamecubeGame g " +
                        " where g.serialNumber = :serialNumber ";

        Query query = session.createQuery(text);
        query.setParameter("serialNumber", serialNumber);

        return (GamecubeGame) singleResult(query);
    }
}
