package com.schlock.website.entities.apps.pokemon;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UnownPokemon implements Comparable<UnownPokemon>
{
    private static final String EXCLAMATION_POINT = "!";
    private static final String QUESTION_MARK = "?";

    private final String letter;

    private final List<UnownEvent> events = new ArrayList<UnownEvent>();

    public UnownPokemon(String letter)
    {
        this.letter = letter;
    }

    public int getEventCount()
    {
        return events.size();
    }

    public String getEventNames()
    {
        List<String> names = new ArrayList<String>();
        for(UnownEvent event : events)
        {
            names.add(event.getEventName());
        }

        return StringUtils.join(names, ", ");
    }


    public String getLetter()
    {
        return letter;
    }

    public List<UnownEvent> getEvents()
    {
        return events;
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
