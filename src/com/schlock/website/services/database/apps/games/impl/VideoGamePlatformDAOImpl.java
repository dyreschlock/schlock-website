package com.schlock.website.services.database.apps.games.impl;

import com.schlock.website.entities.apps.games.VideoGamePlatform;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.apps.games.VideoGamePlatformDAO;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class VideoGamePlatformDAOImpl extends BaseDAOImpl<VideoGamePlatform> implements VideoGamePlatformDAO
{
    public VideoGamePlatformDAOImpl(Session session)
    {
        super(VideoGamePlatform.class, session);
    }

    public VideoGamePlatform getByCode(String code)
    {
        String text = " from VideoGamePlatform p " +
                " where p.code = :code ";

        Query query = session.createQuery(text);
        query.setParameter("code", code);

        return (VideoGamePlatform) singleResult(query);
    }

    public List<VideoGamePlatform> getByCompany(String company)
    {
        String text = " from VideoGamePlatform p " +
                "where p.company = :company ";

        Query query = session.createQuery(text);
        query.setParameter("company", company);

        return query.list();
    }
}
