package com.schlock.website.services.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.RaidBoss;
import com.schlock.website.entities.apps.pokemon.RaidCounterInstance;
import com.schlock.website.entities.apps.pokemon.RaidMove;
import com.schlock.website.entities.apps.pokemon.RaidPokemonData;

public interface PokemonRaidCounterCalculationService
{
    RaidCounterInstance generateRaidCounter(RaidBoss raidBoss, RaidPokemonData pokemonData, RaidMove fastMove, RaidMove chargeMove, int level);
}
