package com.schlock.website.services.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.*;
import com.schlock.website.services.apps.pokemon.PokemonCustomCounterService;
import com.schlock.website.services.apps.pokemon.PokemonDataService;
import com.schlock.website.services.database.apps.pokemon.PokemonCustomCounterDAO;
import com.schlock.website.services.database.apps.pokemon.PokemonDataDAO;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractCustomCounterServiceImpl implements PokemonCustomCounterService
{
    private final PokemonDataService dataService;

    private final PokemonCustomCounterDAO counterDAO;
    private final PokemonDataDAO pokemonDAO;

    private Set<CounterPokemon> customCounters = new HashSet<CounterPokemon>();

    public AbstractCustomCounterServiceImpl(PokemonDataService dataService,
                                            PokemonCustomCounterDAO counterDAO,
                                            PokemonDataDAO pokemonDAO)
    {
        this.dataService = dataService;
        this.counterDAO = counterDAO;
        this.pokemonDAO = pokemonDAO;
    }

    private void createCustomCounters(PokemonCustomCounterAccount account)
    {
        for (PokemonCustomCounter custom : counterDAO.getByAccount(account))
        {
            String name = custom.getName();
            int level = custom.getLevel();
            int attackIV = custom.getAttackIV();
            int defenseIV = custom.getDefenseIV();
            int staminaIV = custom.getStaminaIV();

            String fastMoves = custom.getFastMoves();
            String chargeMoves = custom.getChargeMoves();

            PokemonData data = pokemonDAO.getByName(name);
            double cpm = dataService.getCpmFromLevel(level);

            CounterPokemon counter = CounterPokemon.createCustom(data, level, cpm, attackIV, defenseIV, staminaIV, fastMoves, chargeMoves);
            customCounters.add(counter);
        }
    }

    public Set<CounterPokemon> getCounterPokemon(PokemonCustomCounterAccount account, BattleMode battleMode)
    {
        if (customCounters.isEmpty())
        {
            createCustomCounters(account);
        }

        if(battleMode.isRocket())
        {
            Set<CounterPokemon> counters = new HashSet<CounterPokemon>();
            for (CounterPokemon counter : customCounters)
            {
                if (!counter.isMega())
                {
                    counters.add(counter);
                }
            }
            return counters;
        }
        return customCounters;
    }
}
