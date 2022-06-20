package com.schlock.website.pages.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.RaidCounterPokemon;
import com.schlock.website.entities.apps.pokemon.RaidBoss;
import com.schlock.website.entities.apps.pokemon.RaidCounterType;
import com.schlock.website.services.apps.pokemon.PokemonRaidCounterService;
import com.schlock.website.services.apps.pokemon.PokemonRaidDataService;
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
    private RaidCounterPokemon currentCounterPokemon;

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
        if(RaidCounterType.PERSONAL.toString().equalsIgnoreCase(parameter))
        {
            this.counterType = RaidCounterType.PERSONAL;
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

    public List<RaidCounterPokemon> getCounterPokemon()
    {
        return counterService.getCounterPokemon(currentRaidBoss, counterType);
    }

    public List<RaidCounterPokemon> getTopMegaPokemon()
    {
        return counterService.getTopMegaCounterPokemon(counterType);
    }

    public List<RaidCounterPokemon> getTopShadowPokemon()
    {
        return counterService.getTopShadowCounterPokemon(counterType);
    }

    public List<RaidCounterPokemon> getTopRegularPokemon()
    {
        return counterService.getTopRegularCounterPokemon(counterType);
    }

    public String getColumnIndex()
    {
        return "column" + (currentIndex +1);
    }
}
