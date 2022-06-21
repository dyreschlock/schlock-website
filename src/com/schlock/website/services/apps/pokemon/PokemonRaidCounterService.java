package com.schlock.website.services.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.RaidCounterInstance;
import com.schlock.website.entities.apps.pokemon.RaidBoss;
import com.schlock.website.entities.apps.pokemon.RaidCounterType;

import java.util.List;

public interface PokemonRaidCounterService
{
    public List<RaidBoss> getRaidBosses();

    public List<RaidCounterInstance> getCounterPokemon(RaidBoss legendary, RaidCounterType type);

    public List<RaidCounterInstance> getTopMegaCounterPokemon(RaidCounterType type);
    public List<RaidCounterInstance> getTopShadowCounterPokemon(RaidCounterType type);
    public List<RaidCounterInstance> getTopRegularCounterPokemon(RaidCounterType type);
}
