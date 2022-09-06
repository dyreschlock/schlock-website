package com.schlock.website.components.apps.pokemon.rocket;

import com.schlock.website.entities.apps.pokemon.RaidCounterInstance;
import com.schlock.website.entities.apps.pokemon.RocketCounterInstance;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class RocketCounterDisplay
{
    @Parameter(required = true)
    @Property
    private RocketCounterInstance counterPokemon;

    @Parameter(required = true)
    @Property
    private Integer number;
}
