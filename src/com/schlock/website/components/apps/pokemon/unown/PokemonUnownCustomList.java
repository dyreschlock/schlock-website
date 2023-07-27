package com.schlock.website.components.apps.pokemon.unown;

import com.schlock.website.entities.apps.pokemon.UnownPokemon;
import com.schlock.website.services.apps.pokemon.PokemonUnownService;
import com.schlock.website.services.apps.pokemon.impl.PokemonUnownServiceImpl;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class PokemonUnownCustomList
{
    private static final String UNOWN_TAG = "unown";

    private static final String QUESTION = "?";
    private static final String EXCLAMATION = "!";

    private static final String UNOWN_QUESTION = "Qu";
    private static final String UNOWN_EXCLAMATION = "Ex";

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

    public String getAllUnown()
    {
        String allUnown = "";
        for(UnownPokemon pokemon : unownService.getListOfUnownByLetter())
        {
            allUnown += pokemon.getLetter();
        }
        return allUnown;
    }

    public String getUnownAsString()
    {
        return PokemonUnownServiceImpl.CURRENT_UNOWN_POKEDEX;
    }

    public String getCurrentMissing()
    {
        String allUnown = getAllUnown();
        String currentUnown = getUnownAsString();

        Integer missing = allUnown.length() - currentUnown.length();

        return missing.toString();
    }

    public List<UnownPokemon> getUnown()
    {
        return unownService.getListOfUnownByRarity();
    }

    public String getCurrentUnownId()
    {
        String letter = currentUnown.getLetter();
        if (QUESTION.equals(letter))
        {
            letter = UNOWN_QUESTION;
        }
        if (EXCLAMATION.equals(letter))
        {
            letter = UNOWN_EXCLAMATION;
        }
        return UNOWN_TAG + letter;
    }

    public String getCurrentUnownCSS()
    {
        if(getUnownAsString().contains(currentUnown.getLetter()))
        {
            return "visibility: collapse";
        }
        return "visibility: visible";
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
