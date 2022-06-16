package com.schlock.website.services.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.RaidCounterPokemon;
import com.schlock.website.entities.apps.pokemon.RaidBoss;

import java.util.List;

public interface PokemonRaidCounterService
{
    public List<RaidBoss> getLegendaryPokemon();

    public List<RaidCounterPokemon> getCounterPokemon(RaidBoss legendary);

    public List<RaidCounterPokemon> getTopMegaCounterPokemon();
    public List<RaidCounterPokemon> getTopShadowCounterPokemon();
    public List<RaidCounterPokemon> getTopRegularCounterPokemon();
}
