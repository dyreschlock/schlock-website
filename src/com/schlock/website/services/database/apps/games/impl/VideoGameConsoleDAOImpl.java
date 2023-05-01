package com.schlock.website.services.database.apps.games.impl;

import com.schlock.website.entities.apps.games.VideoGameConsole;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.apps.games.VideoGameConsoleDAO;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class VideoGameConsoleDAOImpl extends BaseDAOImpl<VideoGameConsole> implements VideoGameConsoleDAO
{
    public VideoGameConsoleDAOImpl(Session session)
    {
        super(VideoGameConsole.class, session);
    }

    public List<VideoGameConsole> getByCompany(String company)
    {
        String text = " from VideoGameConsole c " +
                "where c.company = :company ";

        Query query = session.createQuery(text);
        query.setParameter("company", company);

        return query.list();
    }
}
