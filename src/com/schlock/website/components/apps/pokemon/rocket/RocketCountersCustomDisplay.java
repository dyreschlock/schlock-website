package com.schlock.website.components.apps.pokemon.rocket;

import com.schlock.website.entities.apps.pokemon.CounterType;
import com.schlock.website.entities.apps.pokemon.RocketBossPokemon;
import com.schlock.website.entities.apps.pokemon.RocketCounterInstance;
import com.schlock.website.services.apps.pokemon.PokemonRocketCounterService;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class RocketCountersCustomDisplay
{
    @Inject
    private PokemonRocketCounterService counterService;

    @Parameter(required = true)
    @Property
    private RocketBossPokemon rocketBossPokemon;

    @Property
    private Integer currentIndex;

    @Property
    private RocketCounterInstance currentCounterPokemon;


    public List<RocketCounterInstance> getPrimeCounterPokemon()
    {
        return counterService.getCounterPokemon(CounterType.CUSTOM1, rocketBossPokemon);
    }

    public List<RocketCounterInstance> getSecondCounterPokemon()
    {
        return counterService.getCounterPokemon(CounterType.CUSTOM2, rocketBossPokemon);
    }

    public String getCurrentIndexPlusOne()
    {
        return Integer.toString(currentIndex + 1);
    }
}
