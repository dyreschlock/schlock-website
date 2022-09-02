package com.schlock.website.services.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.RaidCounterInstance;
import com.schlock.website.entities.apps.pokemon.RaidBoss;
import com.schlock.website.entities.apps.pokemon.CounterType;

import java.util.List;

public interface PokemonRaidCounterService
{
    public List<RaidBoss> getRaidBosses();

    public List<RaidCounterInstance> getCounterPokemon(RaidBoss legendary, CounterType type);

    public List<RaidCounterInstance> getTopMegaCounterPokemon(CounterType type);
    public List<RaidCounterInstance> getTopShadowCounterPokemon(CounterType type);
    public List<RaidCounterInstance> getTopRegularCounterPokemon(CounterType type);
}
