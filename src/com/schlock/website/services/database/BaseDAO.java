package com.schlock.website.services.database;

public interface BaseDAO<T>
{
    public T getById(Long id);

    public T getByUuid(String uuid);
}
