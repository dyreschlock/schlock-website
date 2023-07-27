package com.schlock.website.entities.apps.pokemon;

import java.util.ArrayList;
import java.util.List;

public class UnownPokemon implements Comparable<UnownPokemon>
{
    private static final String EXCLAMATION_POINT = "!";
    private static final String QUESTION_MARK = "?";

    private final String letter;

    private final List<UnownEvent> events = new ArrayList<UnownEvent>();
    private final List<UnownEvent> shinyEvents = new ArrayList<UnownEvent>();

    public UnownPokemon(String letter)
    {
        this.letter = letter;
    }

    public int getEventCount()
    {
        return events.size();
    }

    public int getShinyCount()
    {
        return shinyEvents.size();
    }

    public String getLetter()
    {
        return letter;
    }

    public List<UnownEvent> getEvents()
    {
        return events;
    }

    public List<UnownEvent> getShinyEvents()
    {
        return shinyEvents;
    }

    public boolean isExclamationPoint()
    {
        return this.letter.equals(EXCLAMATION_POINT);
    }

    public boolean isQuestionMark()
    {
        return this.letter.equals(QUESTION_MARK);
    }

    public int compareTo(UnownPokemon o)
    {
        if (this.isExclamationPoint())
        {
            return 1;
        }
        if (o.isExclamationPoint())
        {
            return -1;
        }
        if (this.isQuestionMark())
        {
            return 1;
        }
        if (o.isQuestionMark())
        {
            return -1;
        }
        return this.getLetter().compareTo(o.getLetter());
    }
}
