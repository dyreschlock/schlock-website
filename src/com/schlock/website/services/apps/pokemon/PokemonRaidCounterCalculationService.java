package com.schlock.website.services.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.*;

import java.util.List;

public interface PokemonRaidCounterCalculationService
{
    RaidCounterInstance generateRaidCounter(RaidBoss raidBoss, CounterPokemon counterPokemon, PokemonMove fastMove, PokemonMove chargeMove);

    Integer getTotalDamageForParty(List<RaidCounterInstance> party);

    Integer getTotalTimeForParty(List<RaidCounterInstance> party);
}
