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

    public List<PlaystationGame> getAvailableGames()
    {
        String text = " select g " +
                        " from PlaystationGame g " +
                        " where g.drive != null ";

        Query query = session.createQuery(text);

        return query.list();
    }

    public List<String> getAllGenres()
    {
        String text = " select g.genre " +
                        " from PlaystationGame g " +
                        " where g.drive != null " +
                        " and g.genre != null " +
                        " group by g.genre " +
                        " order by g.genre asc ";

        Query query = session.createQuery(text);

        return query.list();
    }
}
