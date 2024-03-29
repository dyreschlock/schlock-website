package com.schlock.website.services.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.UnownEvent;
import com.schlock.website.entities.apps.pokemon.UnownPokemon;

import java.util.List;

public interface PokemonUnownService
{
    public List<String> getEventYears();

    public List<UnownEvent> getListOfEvents();
    public List<UnownPokemon> getListOfUnownByLetter();
    public List<UnownPokemon> getListOfUnownByRarity();

    public List<UnownPokemon> getListOfUnownByShinyRarity();

    public String getEventNamesForUnown(UnownPokemon pokemon);
    public String getEventNamesForUnownByYear(UnownPokemon pokemon, String year);
    public String getShinyEventNamesForUnownByYear(UnownPokemon pokemon, String year);
}
