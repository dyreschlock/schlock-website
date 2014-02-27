package com.schlock.website.services.database;

public interface BaseDAO<T>
{
    public T getById(Long id);

    public void save(T obj);
}
