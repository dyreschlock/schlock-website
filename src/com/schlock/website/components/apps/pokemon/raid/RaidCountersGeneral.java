package com.schlock.website.components.apps.pokemon.raid;

import com.schlock.website.entities.apps.pokemon.CounterType;
import com.schlock.website.entities.apps.pokemon.RaidBossPokemon;
import com.schlock.website.entities.apps.pokemon.RaidCounterInstance;
import com.schlock.website.services.apps.pokemon.PokemonRaidCounterService;
import com.schlock.website.services.apps.pokemon.impl.PokemonRaidCounterServiceImpl;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class RaidCountersGeneral
{
    @Inject
    private PokemonRaidCounterService counterService;

    @Property
    private RaidBossPokemon currentRaidBoss;

    @Property
    private RaidCounterInstance currentCounterPokemon;

    @Property
    private Integer currentIndex;



    public List<RaidBossPokemon> getRaidBosses()
    {
        return counterService.getRaidBosses();
    }

    public List<RaidCounterInstance> getCounterPokemon()
    {
        return counterService.getCounterPokemon(currentRaidBoss, CounterType.GENERAL);
    }

    public int getMegaCounterNumber()
    {
        return PokemonRaidCounterServiceImpl.NUMBER_OF_MEGA_COUNTERS_PER_POKEMON;
    }

    public int getShadowCounterNumber()
    {
        return PokemonRaidCounterServiceImpl.NUMBER_OF_SHADOW_COUNTERS_PER_POKEMON;
    }

    public int getCounterNumber()
    {
        return PokemonRaidCounterServiceImpl.NUMBER_OF_REGULAR_COUNTERS_PER_POKEMON;
    }

    public Integer getNumber()
    {
        int number = currentIndex +1;

        if (number > PokemonRaidCounterServiceImpl.NUMBER_OF_MEGA_COUNTERS_PER_POKEMON)
        {
            number -= PokemonRaidCounterServiceImpl.NUMBER_OF_MEGA_COUNTERS_PER_POKEMON;
        }
        if (number > PokemonRaidCounterServiceImpl.NUMBER_OF_SHADOW_COUNTERS_PER_POKEMON)
        {
            number -= PokemonRaidCounterServiceImpl.NUMBER_OF_SHADOW_COUNTERS_PER_POKEMON;
        }
        return number;
    }

    public String getColumnIndex()
    {
        return "column" + (currentIndex +1);
    }
}
