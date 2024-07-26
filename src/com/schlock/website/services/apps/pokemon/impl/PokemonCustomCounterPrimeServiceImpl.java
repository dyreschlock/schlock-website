package com.schlock.website.services.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.BattleMode;
import com.schlock.website.entities.apps.pokemon.CounterPokemon;
import com.schlock.website.entities.apps.pokemon.PokemonCustomCounterAccount;
import com.schlock.website.services.apps.pokemon.PokemonCustomCounterPrimeService;
import com.schlock.website.services.apps.pokemon.PokemonDataService;
import com.schlock.website.services.database.apps.pokemon.PokemonCustomCounterDAO;
import com.schlock.website.services.database.apps.pokemon.PokemonDataDAO;

import java.util.Set;

public class PokemonCustomCounterPrimeServiceImpl extends AbstractCustomCounterServiceImpl implements PokemonCustomCounterPrimeService
{
    public PokemonCustomCounterPrimeServiceImpl(PokemonDataService dataService, PokemonCustomCounterDAO counterDAO, PokemonDataDAO pokemonDAO)
    {
        super(dataService, counterDAO, pokemonDAO);
    }

    public Set<CounterPokemon> getCounterPokemon(BattleMode battleMode)
    {
        return getCounterPokemon(PokemonCustomCounterAccount.PRIME, battleMode);
    }
}
