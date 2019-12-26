package com.schlock.website.services.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.RocketCounterPokemon;
import com.schlock.website.entities.apps.pokemon.RocketLeader;

import java.util.List;

public interface PokemonRocketCounterService
{
    public List<RocketLeader> getRocketLeaders();

    public List<RocketCounterPokemon> getCounterPokemon(String rocketPokemonName);
}
