package com.schlock.website.entities.blog;

import com.schlock.website.entities.Persisted;

public class Keyword extends Persisted
{
    public static final String DELIMITER = ",";

    private String name;

    protected Keyword()
    {
    }

    public Keyword(String name)
    {
        this.name = name;
    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
