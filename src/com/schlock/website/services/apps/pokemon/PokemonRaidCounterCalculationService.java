package com.schlock.website.services.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.*;

public interface PokemonRaidCounterCalculationService
{
    RaidCounterInstance generateRaidCounter(RaidBoss raidBoss, RaidCounter raidCounter, RaidMove fastMove, RaidMove chargeMove);
}
