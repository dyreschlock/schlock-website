package com.schlock.website.components.apps.pokemon.unown;

import com.schlock.website.entities.apps.pokemon.UnownPokemon;
import com.schlock.website.services.apps.pokemon.PokemonUnownService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class PokemonUnownRarityList
{
    @Inject
    private PokemonUnownService unownService;


    @Property
    private UnownPokemon currentUnown;

    @Property
    private String currentYear;

    public List<UnownPokemon> getUnown()
    {
        return unownService.getListOfUnownByRarity();
    }

    public List<String> getYears()
    {
        return unownService.getEventYears();
    }

    public String getEventsByCurrentYear()
    {
        return unownService.getEventNamesForUnownByYear(currentUnown, currentYear);
    }
}
