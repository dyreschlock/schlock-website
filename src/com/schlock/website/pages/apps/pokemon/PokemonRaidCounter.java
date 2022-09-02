package com.schlock.website.pages.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.CounterType;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PokemonRaidCounter
{
    @Inject
    private Messages messages;

    @Persist
    private CounterType counterType;


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

    public CounterType getCounterType()
    {
        return counterType;
    }
}
