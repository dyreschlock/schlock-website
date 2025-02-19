package com.schlock.website.services.database.blog.impl;

import com.schlock.website.entities.blog.Keyword;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.blog.KeywordDAO;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

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

    public List<Keyword> getAllAvailable()
    {
        String text = "select k " +
                        " from AbstractPost p " +
                        " join p.keywords k " +
                        " group by k.id " +
                        " having (count(p.id) > 1) " +
                        " order by k.name asc ";

        Query query = session.createQuery(text);

        return query.list();
    }
}
