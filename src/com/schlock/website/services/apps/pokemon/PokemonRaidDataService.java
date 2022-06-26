package com.schlock.website.services.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.*;

import java.util.Collection;
import java.util.List;

public interface PokemonRaidDataService
{
    public List<RaidBoss> getRaidBosses();

    public Collection<RaidCounter> getSuitableCounterPokemon(RaidCounterType type);

    public Double getCpmFromLevel(Integer level);

    public RaidPokemonData getDataByName(String name);

    public RaidMove getMoveByName(String name);
}
