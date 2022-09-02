package com.schlock.website.services.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.RaidCounterInstance;
import com.schlock.website.entities.apps.pokemon.RaidBossPokemon;
import com.schlock.website.entities.apps.pokemon.CounterType;

import java.util.List;

public interface PokemonRaidCounterService
{
    public List<RaidBossPokemon> getRaidBosses();

    public List<RaidCounterInstance> getCounterPokemon(RaidBossPokemon legendary, CounterType type);

    public List<RaidCounterInstance> getTopMegaCounterPokemon(CounterType type);
    public List<RaidCounterInstance> getTopShadowCounterPokemon(CounterType type);
    public List<RaidCounterInstance> getTopRegularCounterPokemon(CounterType type);
}
