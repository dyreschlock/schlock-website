package com.schlock.website.services.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.*;

import java.util.List;

public interface PokemonCounterCalculationService
{
    RaidCounterInstance generateRaidCounter(RaidBossPokemon raidBoss, CounterPokemon counterPokemon, PokemonMove fastMove, PokemonMove chargeMove);

    RocketCounterInstance generateRocketCounter(RocketBossPokemon rocketBoss, CounterPokemon counterPokemon, PokemonMove fastMove, PokemonMove chargeMove);

    Integer getTotalDamageForParty(List<RaidCounterInstance> party);

    Integer getTotalTimeForParty(List<RaidCounterInstance> party);
}
