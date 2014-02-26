package com.schlock.website.services.database;

import org.hibernate.Session;

public abstract class BaseDAOImpl<T> implements BaseDAO<T>
{
    protected final Session session;

    private final Class entityClass;

    public BaseDAOImpl(Class<T> entityClass, Session session)
    {
        this.session = session;
        this.entityClass = entityClass;
    }

    public T getById(Long id)
    {
        return (T) session.load(entityClass, id);
    }
}
