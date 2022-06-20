package com.schlock.website.services.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.RaidCounterType;
import com.schlock.website.entities.apps.pokemon.RaidPokemonData;

import java.util.Collection;
import java.util.List;

public interface PokemonRaidDataService
{
    public RaidPokemonData getPokemonDataByName(String name);

    public Collection<RaidPokemonData> getAllPokemon();

    public Collection<RaidPokemonData> getSuitableCounterPokemon(RaidCounterType type);

    public List<String> getRaidBossNames();
}
