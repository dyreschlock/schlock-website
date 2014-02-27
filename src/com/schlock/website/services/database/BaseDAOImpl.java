package com.schlock.website.services.database;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public abstract class BaseDAOImpl<T> implements BaseDAO<T>
{
    protected final Session session;

    private final Class entityClass;

    public BaseDAOImpl(Class<T> entityClass, Session session)
    {
        this.session = session;
        this.entityClass = entityClass;
    }

    public List<T> getAll()
    {
        String text = String.format("from %s order by id", entityClass.getName());
        Query query = session.createQuery(text);
        return query.list();
    }

    public T getById(Long id)
    {
        return (T) session.load(entityClass, id);
    }

    protected Object singleResult(Query query)
    {
        List list = query.list();
        if (list.isEmpty())
        {
            return null;
        }
        return list.get(0);
    }

    public void save(T obj)
    {
        session.save(obj);
    }
}
