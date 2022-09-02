package com.schlock.website.pages.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.CounterType;
import com.schlock.website.entities.apps.pokemon.RocketCounterInstance;
import com.schlock.website.entities.apps.pokemon.RocketLeader;
import com.schlock.website.entities.apps.pokemon.RocketBossPokemon;
import com.schlock.website.services.apps.pokemon.PokemonRocketCounterService;
import org.apache.tapestry5.annotations.Persist;
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

    @Persist
    private CounterType counterType;


    @Property
    private String currentRocketLeaderGroup;

    @Property
    private RocketLeader currentRocketLeader;

    @Property
    private RocketBossPokemon currentRocketBossPokemon;

    @Property
    private RocketCounterInstance currentCounterPokemon;

    @Property
    private Integer currentIndex;


    Object onActivate()
    {
        if (counterType == null)
        {
            this.counterType = CounterType.defaultType();
        }
        return true;
    }

    Object onActivate(String parameter)
    {
        if(CounterType.CUSTOM.toString().equalsIgnoreCase(parameter))
        {
            this.counterType = CounterType.CUSTOM;
        }
        else if(CounterType.GENERAL.toString().equalsIgnoreCase(parameter))
        {
            this.counterType = CounterType.GENERAL;
        }
        else
        {
            this.counterType = CounterType.defaultType();
        }
        return true;
    }

    public String getPageTitle()
    {
        return messages.format("page-title", counterType.name().toLowerCase());
    }


    public String getCurrentRocketLeaderName()
    {
        return messages.get(currentRocketLeader.getName());
    }

    public List<RocketLeader> getRocketLeaders()
    {
        return counterService.getRocketLeaders();
    }

    public List<RocketCounterInstance> getCounterPokemon()
    {
        return counterService.getCounterPokemon(counterType, currentRocketBossPokemon);
    }

    public String getCurrentIndexPlusOne()
    {
        return Integer.toString(currentIndex + 1);
    }
}
