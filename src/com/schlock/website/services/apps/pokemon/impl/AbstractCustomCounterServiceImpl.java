package com.schlock.website.services.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.BattleMode;
import com.schlock.website.entities.apps.pokemon.CounterPokemon;
import com.schlock.website.entities.apps.pokemon.CounterType;
import com.schlock.website.entities.apps.pokemon.PokemonData;
import com.schlock.website.services.apps.pokemon.PokemonCustomCounterService;
import com.schlock.website.services.apps.pokemon.PokemonDataService;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractCustomCounterServiceImpl implements PokemonCustomCounterService
{
    private final PokemonDataService dataService;

    private Set<CounterPokemon> customCounters = new HashSet<CounterPokemon>();

    public AbstractCustomCounterServiceImpl(PokemonDataService dataService)
    {
        this.dataService = dataService;

        createCustomCounters();
    }


    private void createCustomCounters()
    {
        megas();
        shadows();
        rocketCounters();

        fireSquad();
        waterSquad();
        grassSquad();
        electricSquad();

        iceSquad();
        rockSquad();
        groundSquad();

        dragonSquad();

        psychicSquad();
        ghostDarkSquad();
        fightSquad();

        metalPoisonSquad();
    }

    protected void addCustom(String name, int level, int attackIV, int defenseIV, int staminaIV)
    {
        addCustom(name, level, attackIV , defenseIV, staminaIV, null, null);
    }

    protected void addCustom(String name, int level, int attackIV, int defenseIV, int staminaIV, String fastMoves, String chargeMoves)
    {
        PokemonData data = dataService.getDataByName(name);
        double cpm = dataService.getCpmFromLevel(level);

        CounterPokemon counter = CounterPokemon.createCustom(data, level, cpm, attackIV, defenseIV, staminaIV, fastMoves, chargeMoves);
        customCounters.add(counter);
    }

    public Set<CounterPokemon> getCounterPokemon(BattleMode battleMode)
    {
        if (customCounters.isEmpty())
        {
            createCustomCounters();
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

    abstract protected void megas();
    abstract protected void shadows();
    abstract protected void rocketCounters();

    abstract protected void fireSquad();
    abstract protected void waterSquad();
    abstract protected void grassSquad();
    abstract protected void electricSquad();

    abstract protected void iceSquad();
    abstract protected void rockSquad();
    abstract protected void groundSquad();

    abstract protected void dragonSquad();

    abstract protected void psychicSquad();
    abstract protected void ghostDarkSquad();
    abstract protected void fightSquad();

    abstract protected void metalPoisonSquad();
    abstract protected void flightSquad();
}
