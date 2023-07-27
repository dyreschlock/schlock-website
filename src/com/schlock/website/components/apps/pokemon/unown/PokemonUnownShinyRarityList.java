package com.schlock.website.components.apps.pokemon.unown;

import com.schlock.website.entities.apps.pokemon.UnownPokemon;
import com.schlock.website.services.apps.pokemon.PokemonUnownService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class PokemonUnownShinyRarityList
{
    @Inject
    private PokemonUnownService unownService;


    @Property
    private UnownPokemon currentUnown;

    @Property
    private String currentYear;

    public List<UnownPokemon> getUnown()
    {
        return unownService.getListOfUnownByShinyRarity();
    }

    public List<String> getYears()
    {
        return unownService.getShinyEventYears();
    }

    public String getEventsByCurrentYear()
    {
        return unownService.getShinyEventNamesForUnownByYear(currentUnown, currentYear);
    }
}
