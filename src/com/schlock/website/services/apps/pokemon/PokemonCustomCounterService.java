package com.schlock.website.services.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.BattleMode;
import com.schlock.website.entities.apps.pokemon.CounterPokemon;

import java.util.Set;

public interface PokemonCustomCounterService
{
    Set<CounterPokemon> getCounterPokemon(BattleMode battleMode);
}
