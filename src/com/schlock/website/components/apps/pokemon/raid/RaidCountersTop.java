package com.schlock.website.components.apps.pokemon.raid;

import com.schlock.website.entities.apps.pokemon.CounterType;
import com.schlock.website.entities.apps.pokemon.RaidCounterInstance;
import com.schlock.website.entities.apps.pokemon.RaidBossWithAttackingType;
import com.schlock.website.services.apps.pokemon.PokemonRaidCounterService;
import org.apache.tapestry5.annotations.Property;

import javax.inject.Inject;
import java.util.List;

public class RaidCountersTop
{
    @Inject
    private PokemonRaidCounterService counterService;


    @Property
    private RaidBossWithAttackingType currentBoss;

    @Property
    private RaidCounterInstance currentCounterPokemon;

    @Property
    private Integer currentBossIndex;

    @Property
    private Integer currentIndex;


    public List<RaidBossWithAttackingType> getRaidBossForEachAttackingType()
    {
        return counterService.getRaidBossForEachAttackingType();
    }

    public List<RaidCounterInstance> getCounterPokemon()
    {
        return counterService.getCounterPokemonByAttackingType(currentBoss, CounterType.TOP);
    }

    public List<RaidCounterInstance> getOverallTopCounterPokemon()
    {
        return counterService.getTopCounterPokemonByAttackingType(CounterType.TOP);
    }

    public String getColumnIndex()
    {
        return "column0";
//        return "column" + (currentIndex +1);
    }

    public boolean isCloseRow()
    {
        return currentBossIndex % 2 == 1;
    }

    public String getCloseRowHTML()
    {
        return "</tr><tr>";
    }
}
