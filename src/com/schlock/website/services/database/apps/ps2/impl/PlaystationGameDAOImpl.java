package com.schlock.website.services.database.apps.ps2.impl;

import com.schlock.website.entities.apps.ps2.PlaystationGame;
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
}
