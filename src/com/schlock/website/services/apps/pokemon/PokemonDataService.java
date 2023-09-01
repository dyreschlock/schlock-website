package com.schlock.website.services.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.*;

import java.util.Collection;
import java.util.List;

public interface PokemonDataService
{
    List<RaidBossPokemon> getRaidBosses();

    List<RaidBossWithAttackingType> getRaidBossForEachAttackingType();

    List<RocketLeader> getRocketLeaders();

    Collection<CounterPokemon> getCounterPokemon(CounterType type, BattleMode battleMode);

    Double getCpmFromLevel(Integer level);

    PokemonData getDataByName(String name);

    PokemonMove getMoveByName(String name);
}
