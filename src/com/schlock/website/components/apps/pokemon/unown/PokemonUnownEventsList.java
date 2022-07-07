package com.schlock.website.components.apps.pokemon.unown;

import com.schlock.website.entities.apps.pokemon.UnownEvent;
import com.schlock.website.services.apps.pokemon.PokemonUnownService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;

import javax.inject.Inject;
import java.util.List;

public class PokemonUnownEventsList
{
    @Inject
    private PokemonUnownService unownService;

    @Inject
    private Messages messaages;


    @Property
    private UnownEvent currentEvent;

    public List<UnownEvent> getUnownEvents()
    {
        return unownService.getListOfEvents();
    }

    public String getEventCSS()
    {
        if (currentEvent.isAttended())
        {
            return "attended";
        }
        return "";
    }
}
