package com.schlock.website.components.apps.pokemon.raid;

import com.schlock.website.entities.apps.pokemon.CounterType;
import com.schlock.website.entities.apps.pokemon.RaidBossPokemon;
import com.schlock.website.entities.apps.pokemon.RaidCounterInstance;
import com.schlock.website.pages.apps.pokemon.PokemonRaidCounter;
import com.schlock.website.services.apps.pokemon.PokemonRaidCounterService;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.List;

public class RaidCountersBossDetails
{
    private static final CounterType COUNTER_TYPE = CounterType.GENERAL;

    private static final int COUNTERS_PER_ROW = 9;
    private static final int GENERAL_COUNTER_NUM_ROWS = 3;

    @Parameter(required = true)
    @Property
    private RaidBossPokemon selectedBoss;

    @Inject
    private PokemonRaidCounterService counterService;


    @Property
    private RaidCounterInstance currentCounterPokemon;

    @Property
    private Integer currentRow;

    @Property
    private Integer currentIndex;


    public Integer getNumber()
    {
        int number = currentIndex +1;

        if (currentRow != null)
        {
            number += ((currentRow -1) * COUNTERS_PER_ROW);
        }
        return number;
    }

    public List<RaidCounterInstance> getMegaCounters()
    {
        counterService.generateRaidCounters(selectedBoss, COUNTER_TYPE);

        return selectedBoss.getMegaCounters(COUNTER_TYPE).subList(0, COUNTERS_PER_ROW);
    }

    public List<RaidCounterInstance> getShadowCounters()
    {
        counterService.generateRaidCounters(selectedBoss, COUNTER_TYPE);

        return selectedBoss.getShadowCounters(COUNTER_TYPE).subList(0, COUNTERS_PER_ROW);
    }

    public List<Integer> getGeneralRowNumbers()
    {
        List<Integer> numbers = new ArrayList<Integer>();

        for(int i = 0; i < GENERAL_COUNTER_NUM_ROWS; i++)
        {
            numbers.add(i + 1);
        }
        return numbers;
    }

    public List<RaidCounterInstance> getGeneralCounters()
    {
        int end = COUNTERS_PER_ROW * currentRow;
        int start = end - COUNTERS_PER_ROW;

        counterService.generateRaidCounters(selectedBoss, COUNTER_TYPE);

        return selectedBoss.getRegularCounters(COUNTER_TYPE).subList(start, end);
    }

    public int getCountersPerRow()
    {
        return COUNTERS_PER_ROW;
    }

    public String getReturnLink()
    {
        return PokemonRaidCounter.getPageLink(null);
    }
}
