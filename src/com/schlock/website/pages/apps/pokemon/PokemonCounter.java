package com.schlock.website.pages.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.CounterPokemon;
import com.schlock.website.entities.apps.pokemon.LegendaryPokemon;
import com.schlock.website.services.apps.pokemon.PokemonCounterService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class PokemonCounter
{
    @Inject
    private PokemonCounterService counterService;

    @Property
    private LegendaryPokemon currentLegendaryPokemon;

    @Property
    private CounterPokemon currentCounterPokemon;


    public List<LegendaryPokemon> getLegendaryPokemon()
    {
        return counterService.getLegendaryPokemon();
    }

    public List<CounterPokemon> getCounterPokemon()
    {
        return counterService.getCounterPokemon(currentLegendaryPokemon);
    }
}
