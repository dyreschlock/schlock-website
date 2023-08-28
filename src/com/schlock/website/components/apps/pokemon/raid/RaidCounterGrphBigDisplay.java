package com.schlock.website.components.apps.pokemon.raid;

import com.schlock.website.entities.apps.pokemon.RaidCounterInstance;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class RaidCounterGrphBigDisplay
{
    @Inject
    private Messages messages;

    @Parameter(required = true)
    @Property
    private RaidCounterInstance pokemon;

    @Parameter
    @Property
    private String extraClass;

}
