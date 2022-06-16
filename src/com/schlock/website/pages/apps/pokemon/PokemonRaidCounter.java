package com.schlock.website.pages.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.RaidCounterPokemon;
import com.schlock.website.entities.apps.pokemon.RaidBoss;
import com.schlock.website.services.apps.pokemon.PokemonRaidCounterService;
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


    public List<RaidBoss> getLegendaryPokemon()
    {
        return counterService.getLegendaryPokemon();
    }

    public List<RaidCounterPokemon> getCounterPokemon()
    {
        return counterService.getCounterPokemon(currentRaidBoss);
    }

    public List<RaidCounterPokemon> getTopMegaPokemon()
    {
        return counterService.getTopMegaCounterPokemon();
    }

    public List<RaidCounterPokemon> getTopShadowPokemon()
    {
        return counterService.getTopShadowCounterPokemon();
    }

    public List<RaidCounterPokemon> getTopRegularPokemon()
    {
        return counterService.getTopRegularCounterPokemon();
    }

    public String getColumnIndex()
    {
        return "column" + (currentIndex +1);
    }
}
