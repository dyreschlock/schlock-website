package com.schlock.website.pages.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.RaidCounterPokemon;
import com.schlock.website.entities.apps.pokemon.LegendaryPokemon;
import com.schlock.website.services.apps.pokemon.PokemonRaidCounterService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class PokemonRaidCounter
{
    @Inject
    private PokemonRaidCounterService counterService;

    @Property
    private LegendaryPokemon currentLegendaryPokemon;

    @Property
    private RaidCounterPokemon currentCounterPokemon;


    public List<LegendaryPokemon> getLegendaryPokemon()
    {
        return counterService.getLegendaryPokemon();
    }

    public List<RaidCounterPokemon> getCounterPokemon()
    {
        return counterService.getCounterPokemon(currentLegendaryPokemon);
    }
}
