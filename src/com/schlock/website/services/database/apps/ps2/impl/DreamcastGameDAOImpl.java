package com.schlock.website.services.database.apps.ps2.impl;

import com.schlock.website.entities.apps.ps2.DreamcastGame;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.apps.ps2.DreamcastGameDAO;
import org.hibernate.Query;
import org.hibernate.Session;

public class DreamcastGameDAOImpl extends BaseDAOImpl<DreamcastGame> implements DreamcastGameDAO
{
    public DreamcastGameDAOImpl(Session session)
    {
        super(DreamcastGame.class, session);
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
