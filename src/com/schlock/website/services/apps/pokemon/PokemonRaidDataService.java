package com.schlock.website.services.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.RaidBoss;
import com.schlock.website.entities.apps.pokemon.RaidCounter;
import com.schlock.website.entities.apps.pokemon.RaidCounterType;
import com.schlock.website.entities.apps.pokemon.RaidPokemonData;

import java.util.Collection;
import java.util.List;

public interface PokemonRaidDataService
{
    public RaidPokemonData getPokemonDataByName(String name);

    public List<RaidBoss> getRaidBosses();

    public Collection<RaidPokemonData> getSuitableCounterPokemon(RaidCounterType type);
}