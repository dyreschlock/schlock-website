package com.schlock.website.services.database.blog.impl;

import com.schlock.website.entities.blog.Keyword;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.blog.KeywordDAO;
import org.hibernate.Query;
import org.hibernate.Session;

public class KeywordDAOImpl extends BaseDAOImpl<Keyword> implements KeywordDAO
{
    public KeywordDAOImpl(Session session)
    {
        super(Keyword.class, session);
    }

    public Keyword getByName(String name)
    {
        String text = "from Keyword k " +
                        " where k.name = :name ";

        Query query = session.createQuery(text);
        query.setParameter("name", name);

        return (Keyword) singleResult(query);

    }
}
