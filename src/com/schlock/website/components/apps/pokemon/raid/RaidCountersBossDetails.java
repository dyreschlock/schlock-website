package com.schlock.website.components.apps.pokemon.raid;

import com.schlock.website.entities.apps.pokemon.RaidBossPokemon;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class RaidCountersBossDetails
{
    @Parameter(required = true)
    @Property
    private RaidBossPokemon selectedBoss;
}
