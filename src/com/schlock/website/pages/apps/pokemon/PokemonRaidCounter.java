package com.schlock.website.pages.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.RaidCounterType;
import org.apache.tapestry5.annotations.Persist;

public class PokemonRaidCounter
{
    @Persist
    private RaidCounterType counterType;


    Object onActivate()
    {
        if (counterType == null)
        {
            this.counterType = RaidCounterType.defaultType();
        }
        return true;
    }

    Object onActivate(String parameter)
    {
        if(RaidCounterType.CUSTOM.toString().equalsIgnoreCase(parameter))
        {
            this.counterType = RaidCounterType.CUSTOM;
        }
        else if(RaidCounterType.GENERAL.toString().equalsIgnoreCase(parameter))
        {
            this.counterType = RaidCounterType.GENERAL;
        }
        else
        {
            this.counterType = RaidCounterType.defaultType();
        }
        return true;
    }

    public RaidCounterType getCounterType()
    {
        return counterType;
    }
}
