package com.schlock.website.services.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.RaidCounterPokemon;
import com.schlock.website.entities.apps.pokemon.LegendaryPokemon;

import java.util.List;

public interface PokemonRaidCounterService
{
    public List<LegendaryPokemon> getLegendaryPokemon();

    public List<RaidCounterPokemon> getCounterPokemon(LegendaryPokemon legendary);
}
