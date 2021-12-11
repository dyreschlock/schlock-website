package com.schlock.website.components.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.UnownPokemon;
import com.schlock.website.services.apps.pokemon.PokemonUnownService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.Arrays;
import java.util.List;

public class PokemonUnownRarityList
{
    private static final List<String> YEARS = Arrays.asList("2022", "2021", "2020", "2019", "2018", "2017");

    @Inject
    private PokemonUnownService unownService;

    @Inject
    private Messages messages;


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
        return YEARS;
    }

    public String getEventsByCurrentYear()
    {
        return unownService.getEventNamesForUnownByYear(currentUnown, currentYear);
    }
}
