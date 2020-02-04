package com.schlock.website.pages.apps.pokemon;

import com.oracle.webservices.internal.api.message.PropertySet;
import com.schlock.website.entities.apps.pokemon.RocketCounterPokemon;
import com.schlock.website.entities.apps.pokemon.RocketLeader;
import com.schlock.website.entities.apps.pokemon.RocketPokemon;
import com.schlock.website.services.apps.pokemon.PokemonRocketCounterService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class PokemonRocketCounter
{
    @Inject
    private PokemonRocketCounterService counterService;

    @Inject
    private Messages messages;


    @Property
    private String currentRocketLeaderGroup;

    @Property
    private RocketLeader currentRocketLeader;

    @Property
    private RocketPokemon currentRocketPokemon;

    @Property
    private RocketCounterPokemon currentCounterPokemon;

    @Property
    private Integer currentIndex;


    public List<String> getRocketLeaderGroups()
    {
        return counterService.getRocketLeaderGroups();
    }


    public String getCurrentRocketLeaderGroupName()
    {
        return messages.get(currentRocketLeaderGroup);
    }

    public String getCurrentRocketLeaderName()
    {
        return messages.get(currentRocketLeader.getName());
    }

    public List<RocketLeader> getRocketLeaders()
    {
        return counterService.getRocketLeaders(currentRocketLeaderGroup);
    }

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
