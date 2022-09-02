package com.schlock.website.components.apps.pokemon.raid;

import com.schlock.website.entities.apps.pokemon.RaidBoss;
import com.schlock.website.entities.apps.pokemon.RaidCounterInstance;
import com.schlock.website.entities.apps.pokemon.CounterType;
import com.schlock.website.services.apps.pokemon.PokemonRaidCounterService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class RaidCountersGeneral
{
    @Inject
    private PokemonRaidCounterService counterService;

    @Inject
    private Messages messages;

    @Property
    private RaidBoss currentRaidBoss;

    @Property
    private RaidCounterInstance currentCounterPokemon;

    @Property
    private Integer currentIndex;




    public List<RaidBoss> getLegendaryPokemon()
    {
        return counterService.getRaidBosses();
    }

    public List<RaidCounterInstance> getCounterPokemon()
    {
        return counterService.getCounterPokemon(currentRaidBoss, CounterType.GENERAL);
    }

    public List<RaidCounterInstance> getTopMegaPokemon()
    {
        return counterService.getTopMegaCounterPokemon(CounterType.GENERAL);
    }

    public List<RaidCounterInstance> getTopShadowPokemon()
    {
        return counterService.getTopShadowCounterPokemon(CounterType.GENERAL);
    }

    public List<RaidCounterInstance> getTopRegularPokemon()
    {
        return counterService.getTopRegularCounterPokemon(CounterType.GENERAL);
    }

    public String getColumnIndex()
    {
        return "column" + (currentIndex +1);
    }
}
