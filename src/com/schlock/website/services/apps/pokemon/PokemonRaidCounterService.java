package com.schlock.website.services.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.RaidCounterPokemon;
import com.schlock.website.entities.apps.pokemon.RaidBoss;
import com.schlock.website.entities.apps.pokemon.RaidCounterType;

import java.util.List;

public interface PokemonRaidCounterService
{
    public List<RaidBoss> getRaidBosses();

    public List<RaidCounterPokemon> getCounterPokemon(RaidBoss legendary, RaidCounterType type);

    public List<RaidCounterPokemon> getTopMegaCounterPokemon(RaidCounterType type);
    public List<RaidCounterPokemon> getTopShadowCounterPokemon(RaidCounterType type);
    public List<RaidCounterPokemon> getTopRegularCounterPokemon(RaidCounterType type);
}
