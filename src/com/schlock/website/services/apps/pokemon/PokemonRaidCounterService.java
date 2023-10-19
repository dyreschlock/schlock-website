package com.schlock.website.services.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.RaidCounterInstance;
import com.schlock.website.entities.apps.pokemon.RaidBossPokemon;
import com.schlock.website.entities.apps.pokemon.CounterType;
import com.schlock.website.entities.apps.pokemon.RaidBossWithAttackingType;

import java.util.List;

public interface PokemonRaidCounterService
{
    public List<RaidBossPokemon> getRaidBosses();
    public RaidBossPokemon getRaidBossByNameId(String nameId);

    public List<RaidBossWithAttackingType> getRaidBossForEachAttackingType();

    public List<RaidCounterInstance> getCounterPokemon(RaidBossPokemon legendary, CounterType type);
    public List<RaidCounterInstance> getTopMegaCounterPokemon(CounterType type);
    public List<RaidCounterInstance> getTopShadowCounterPokemon(CounterType type);
    public List<RaidCounterInstance> getTopRegularCounterPokemon(CounterType type);

    public List<RaidCounterInstance> getCounterPokemonByAttackingType(RaidBossWithAttackingType legendary, CounterType type);
    public List<RaidCounterInstance> getTopCounterPokemonByAttackingType(CounterType type);
}
