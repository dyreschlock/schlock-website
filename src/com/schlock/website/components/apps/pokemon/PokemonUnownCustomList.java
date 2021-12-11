package com.schlock.website.components.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.UnownPokemon;
import com.schlock.website.services.apps.pokemon.PokemonUnownService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.Arrays;
import java.util.List;

public class PokemonUnownCustomList
{
    private static final List<String> YEARS = Arrays.asList("2022", "2021", "2020", "2019");

    @Inject
    private PokemonUnownService unownService;

    @Inject
    private Messages messages;


    @Property
    private UnownPokemon currentUnown;

    @Property
    private String currentYear;


    public String getTitle()
    {
        int count = getUnown().size();
        return messages.format("title", count);
    }


    public List<UnownPokemon> getUnown()
    {
        return unownService.getListOfUnownByRarityAndNotOwned();
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
