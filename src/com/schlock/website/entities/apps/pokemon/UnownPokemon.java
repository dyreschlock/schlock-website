package com.schlock.website.entities.apps.pokemon;

import java.util.ArrayList;
import java.util.List;

public class UnownPokemon
{
    private final String letter;

    private final List<UnownEvent> events = new ArrayList<UnownEvent>();

    public UnownPokemon(String letter)
    {
        this.letter = letter;
    }

    public String getLetter()
    {
        return letter;
    }

    public List<UnownEvent> getEvents()
    {
        return events;
    }
}
