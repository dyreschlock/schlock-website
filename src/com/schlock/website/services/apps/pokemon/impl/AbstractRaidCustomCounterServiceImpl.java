package com.schlock.website.services.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.RaidCounter;
import com.schlock.website.entities.apps.pokemon.RaidPokemonData;
import com.schlock.website.services.apps.pokemon.PokemonRaidCustomCounterService;
import com.schlock.website.services.apps.pokemon.PokemonRaidDataService;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractRaidCustomCounterServiceImpl implements PokemonRaidCustomCounterService
{
    private final PokemonRaidDataService dataService;

    private Set<RaidCounter> customCounters = new HashSet<RaidCounter>();

    public AbstractRaidCustomCounterServiceImpl(PokemonRaidDataService dataService)
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
        RaidPokemonData data = dataService.getDataByName(name);
        RaidCounter counter = RaidCounter.createCustom(data, level, attackIV, defenseIV, staminaIV, fastMoves, chargeMoves);
        customCounters.add(counter);
    }

    public Set<RaidCounter> getCustomPokemon()
    {
        if (customCounters.isEmpty())
        {
            createCustomCounters();
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
}
