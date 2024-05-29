package com.schlock.website.services.database.apps.ps2.impl;

import com.schlock.website.entities.apps.ps2.PlaystationGame;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.apps.ps2.PlaystationGameDAO;
import org.hibernate.Query;
import org.hibernate.Session;

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
}
