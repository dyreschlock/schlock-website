package com.schlock.website.services.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.RaidCounter;

import java.util.Set;

public interface PokemonRaidCustomCounterService
{
    public Set<RaidCounter> getCustomPokemon();
}
