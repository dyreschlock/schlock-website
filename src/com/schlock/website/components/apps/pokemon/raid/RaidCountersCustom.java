package com.schlock.website.components.apps.pokemon.raid;

import com.schlock.website.entities.apps.pokemon.RaidBoss;
import com.schlock.website.entities.apps.pokemon.RaidCounterInstance;
import com.schlock.website.entities.apps.pokemon.RaidCounterType;
import com.schlock.website.services.apps.pokemon.PokemonRaidCounterService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class RaidCountersCustom
{
    @Inject
    private PokemonRaidCounterService counterService;

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
        return counterService.getCounterPokemon(currentRaidBoss, RaidCounterType.CUSTOM);
    }

    public String getColumnIndex()
    {
        return "column" + (currentIndex +1);
    }
}
