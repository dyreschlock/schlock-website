package com.schlock.website.components.apps.pokemon.raid;

import com.schlock.website.entities.apps.pokemon.CounterType;
import com.schlock.website.entities.apps.pokemon.RaidBossPokemon;
import com.schlock.website.entities.apps.pokemon.RaidCounterInstance;
import com.schlock.website.services.apps.pokemon.PokemonRaidCounterService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class RaidCountersGraphical
{
    @Inject
    private PokemonRaidCounterService counterService;

    @Property
    private RaidBossPokemon currentRaidBoss;

    @Property
    private RaidCounterInstance currentCounterPokemon;

    @Property
    private Integer currentIndex;



    public List<RaidBossPokemon> getLegendaryPokemon()
    {
        return counterService.getRaidBosses();
    }

    public List<RaidCounterInstance> getCounterPokemon()
    {
        return counterService.getCounterPokemon(currentRaidBoss, CounterType.GENERAL);
    }

    public String getColumnIndex()
    {
        return "column" + (currentIndex +1);
    }
}
