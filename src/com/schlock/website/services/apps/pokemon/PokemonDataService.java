package com.schlock.website.services.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.*;

import java.util.Collection;
import java.util.List;

public interface PokemonDataService
{
    public List<RaidBoss> getRaidBosses();

    public Collection<CounterPokemon> getCounterPokemon(CounterType type);

    public Double getCpmFromLevel(Integer level);

    public PokemonData getDataByName(String name);

    public PokemonMove getMoveByName(String name);
}
