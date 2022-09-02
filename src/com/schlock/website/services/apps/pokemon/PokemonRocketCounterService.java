package com.schlock.website.services.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.CounterType;
import com.schlock.website.entities.apps.pokemon.RocketBossPokemon;
import com.schlock.website.entities.apps.pokemon.RocketCounterInstance;
import com.schlock.website.entities.apps.pokemon.RocketLeader;

import java.util.List;

public interface PokemonRocketCounterService
{
    public List<RocketLeader> getRocketLeaders();

    public List<RocketCounterInstance> getCounterPokemon(CounterType counterType, RocketBossPokemon pokemon);
}
