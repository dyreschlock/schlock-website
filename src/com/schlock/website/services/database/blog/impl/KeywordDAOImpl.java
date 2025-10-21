package com.schlock.website.services.database.blog.impl;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Keyword;
import com.schlock.website.entities.blog.KeywordType;
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

    public List<Object[]> getAllAvailable()
    {
        String text = "select k.name, count(p.id), max(p.created) " +
                        " from AbstractPost p " +
                        " join p.keywords k " +
                        " where p.publishedLevel >= " + AbstractPost.LEVEL_PUBLISHED + " " +
                        " group by k.id " +
                        " having (count(p.id) > 1) " +
                        " order by k.name asc ";

        Query query = session.createQuery(text);

        return query.list();
    }

    public List<Keyword> getTopInOrder()
    {
        KeywordType type = KeywordType.VISIBLE;

        String text = " select k " +
                " from Keyword k " +
                " where k.parent is null " +
                " and k.type = :type " +
                " order by k.ordering asc ";

        Query query = session.createQuery(text);
        query.setParameter("type", type);

        return query.list();
    }

    public List<Keyword> getSubInOrder(Keyword parent)
    {
        String text = "select child " +
                " from Keyword child " +
                " join child.parent par " +
                " where par.id = :parentId " +
                " and child.type = :type " +
                " order by child.ordering asc ";

        Query query = session.createQuery(text);
        query.setLong("parentId", parent.getId());
        query.setParameter("type", parent.getType());

        return query.list();
    }
}
