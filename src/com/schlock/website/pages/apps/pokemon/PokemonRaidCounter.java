package com.schlock.website.pages.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.RaidCounterInstance;
import com.schlock.website.entities.apps.pokemon.RaidBoss;
import com.schlock.website.entities.apps.pokemon.RaidCounterType;
import com.schlock.website.services.apps.pokemon.PokemonRaidCounterService;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class PokemonRaidCounter
{
    @Inject
    private PokemonRaidCounterService counterService;

    @Property
    private RaidBoss currentRaidBoss;

    @Property
    private RaidCounterInstance currentCounterPokemon;

    @Property
    private Integer currentIndex;

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


    public List<RaidBoss> getLegendaryPokemon()
    {
        return counterService.getRaidBosses();
    }

    public List<RaidCounterInstance> getCounterPokemon()
    {
        return counterService.getCounterPokemon(currentRaidBoss, counterType);
    }

    public List<RaidCounterInstance> getTopMegaPokemon()
    {
        return counterService.getTopMegaCounterPokemon(counterType);
    }

    public List<RaidCounterInstance> getTopShadowPokemon()
    {
        return counterService.getTopShadowCounterPokemon(counterType);
    }

    public List<RaidCounterInstance> getTopRegularPokemon()
    {
        return counterService.getTopRegularCounterPokemon(counterType);
    }

    public String getColumnIndex()
    {
        return "column" + (currentIndex +1);
    }
}
