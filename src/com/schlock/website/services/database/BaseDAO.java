package com.schlock.website.services.database;

import java.util.List;

public interface BaseDAO<T>
{
    public List<T> getAll();

    public T getById(Long id);

    public void save(T obj);
}
