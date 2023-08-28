package com.schlock.website.pages.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.CounterType;
import com.schlock.website.services.blog.CssCache;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PokemonRaidCounter
{
    private static final String POST_UUID = "pokemon-go-raid-counters";

    @Inject
    private CssCache cssCache;

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
        else if (CounterType.TOP.toString().equalsIgnoreCase(parameter))
        {
            this.counterType = CounterType.TOP;
        }
        else
        {
            this.counterType = CounterType.defaultType();
        }
        return true;
    }

    public String getPageTitle()
    {
        String type = messages.get(counterType.name().toLowerCase());
        return messages.format("page-title", type);
    }

    public String getPostUuid()
    {
        return POST_UUID;
    }

    public String getCss()
    {
        return cssCache.getCssForPokemon();
    }

    public CounterType getCounterType()
    {
        return counterType;
    }
}
