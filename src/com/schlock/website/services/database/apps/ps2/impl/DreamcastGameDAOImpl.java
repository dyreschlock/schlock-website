package com.schlock.website.services.database.apps.ps2.impl;

import com.schlock.website.entities.apps.ps2.DreamcastGame;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.apps.ps2.DreamcastGameDAO;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class DreamcastGameDAOImpl extends BaseDAOImpl<DreamcastGame> implements DreamcastGameDAO
{
    public DreamcastGameDAOImpl(Session session)
    {
        super(DreamcastGame.class, session);
    }


    public List<DreamcastGame> getAllAvailable()
    {
        String text = " select g " +
                        " from DreamcastGame g " +
                        " where g.available is true ";

        Query query = session.createQuery(text);
        return query.list();
    }

    public List<DreamcastGame> getAllWithSave()
    {
        String text = "select g " +
                        " from DreamcastGame g " +
                        " where g.haveSave is true ";

        Query query = session.createQuery(text);
        return query.list();
    }

    public List<DreamcastGame> getAllWithNoArt()
    {
        String text = " select g " +
                        " from DreamcastGame g " +
                        " where g.available is true " +
                        " and g.haveArt is false ";

        Query query = session.createQuery(text);
        return query.list();
    }

    public DreamcastGame getBySerialNumber(String serialNumber)
    {
        String text = " select g " +
                        " from DreamcastGame g " +
                        " where g.serialNumber = :serialNumber ";

        Query query = session.createQuery(text);
        query.setParameter("serialNumber", serialNumber);

        return (DreamcastGame) singleResult(query);
    }
}
