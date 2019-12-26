package com.schlock.website.pages.apps.pokemon;

import com.oracle.webservices.internal.api.message.PropertySet;
import com.schlock.website.entities.apps.pokemon.RocketCounterPokemon;
import com.schlock.website.entities.apps.pokemon.RocketLeader;
import com.schlock.website.entities.apps.pokemon.RocketPokemon;
import com.schlock.website.services.apps.pokemon.PokemonRocketCounterService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class PokemonRocketCounter
{
    @Inject
    private PokemonRocketCounterService counterService;

    @Property
    private RocketLeader currentRocketLeader;

    @Property
    private RocketPokemon currentRocketPokemon;

    @Property
    private RocketCounterPokemon currentCounterPokemon;

    @Property
    private Integer currentIndex;



    public List<RocketLeader> getRocketLeaders() { return counterService.getRocketLeaders(); }

    public List<RocketCounterPokemon> getCounterPokemon()
    {
        String rocketPokemonName = currentRocketPokemon.getName();
        return counterService.getCounterPokemon(rocketPokemonName);
    }

    public String getCurrentIndexPlusOne()
    {
        return Integer.toString(currentIndex + 1);
    }
}
