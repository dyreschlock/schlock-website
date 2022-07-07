package com.schlock.website.components.apps.pokemon.unown;

import com.schlock.website.entities.apps.pokemon.UnownPokemon;
import com.schlock.website.services.apps.pokemon.PokemonUnownService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class PokemonUnownByLetterList
{
    @Inject
    private PokemonUnownService unownService;

    @Inject
    private Messages messages;


    @Property
    private UnownPokemon currentUnown;

    public List<UnownPokemon> getUnown()
    {
        return unownService.getListOfUnownByLetter();
    }

    public String getEventNamesForCurrentUnown()
    {
        return unownService.getEventNamesForUnown(currentUnown);
    }
}
