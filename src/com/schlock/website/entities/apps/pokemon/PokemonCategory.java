package com.schlock.website.entities.apps.pokemon;

import com.schlock.website.entities.Persisted;

public class PokemonCategory extends Persisted
{
    private String name;
    private String nameId;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getNameId()
    {
        return nameId;
    }

    public void setNameId(String nameId)
    {
        this.nameId = nameId;
    }
}
