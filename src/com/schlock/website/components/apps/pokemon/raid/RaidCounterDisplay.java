package com.schlock.website.components.apps.pokemon.raid;

import com.schlock.website.entities.apps.pokemon.RaidCounterInstance;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class RaidCounterDisplay
{
    @Inject
    private Messages messages;

    @Parameter(required = true)
    @Property
    private RaidCounterInstance pokemon;

    @Parameter
    @Property
    private Boolean displayIVs = false;

    @Parameter
    @Property
    private Boolean displayCount = false;

    @Parameter
    @Property
    private Boolean displayDpsDetails = false;

    @Parameter
    @Property
    private String extraClass;

    public String getPokemonLevel()
    {
        Integer level = pokemon.getLevel();
        if (displayIVs)
        {
            Integer attackIV = pokemon.getAttackIV();
            Integer defenseIV = pokemon.getDefenseIV();
            Integer staminaIV = pokemon.getStaminaIV();

            String msg = messages.format("custom-level", level, attackIV, defenseIV, staminaIV);
            return msg;
        }
        return level.toString();
    }
}
