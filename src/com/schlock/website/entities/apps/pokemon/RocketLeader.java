package com.schlock.website.entities.apps.pokemon;

import java.util.ArrayList;
import java.util.List;

public class RocketLeader
{
    private String name;

    List<RocketPokemon> pokemon;


    public RocketLeader(String name)
    {
        this.name = name;
        this.pokemon = new ArrayList<RocketPokemon>();
    }

    public void addPokemon(RocketPokemon pokemon)
    {
        this.pokemon.add(pokemon);
    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<RocketPokemon> getPokemon()
    {
        return pokemon;
    }

    public void setPokemon(List<RocketPokemon> pokemon)
    {
        this.pokemon = pokemon;
    }
}
