package com.schlock.website.services.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.CounterPokemon;
import com.schlock.website.entities.apps.pokemon.LegendaryPokemon;

import java.util.List;

public interface PokemonCounterService
{
    public List<LegendaryPokemon> getLegendaryPokemon();

    public List<CounterPokemon> getCounterPokemon(LegendaryPokemon legendary);
}
