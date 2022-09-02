package com.schlock.website.entities.apps.pokemon;

import java.util.ArrayList;
import java.util.List;

public class RocketLeader
{
    private RocketLeaderName name;

    List<RocketBossPokemon> pokemon = new ArrayList<RocketBossPokemon>();

    public RocketLeader(RocketLeaderName name)
    {
        this.name = name;
    }

    public void addPokemon(RocketBossPokemon pokemon)
    {
        this.pokemon.add(pokemon);
    }


    public String getName()
    {
        return name.name();
    }

    public List<RocketBossPokemon> getPokemon()
    {
        return pokemon;
    }

    public void setPokemon(List<RocketBossPokemon> pokemon)
    {
        this.pokemon = pokemon;
    }
}
