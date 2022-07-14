package com.schlock.website.entities.apps.pokemon;

public enum RaidCounterType
{
    GENERAL, CUSTOM, CUSTOM1, CUSTOM2;

    public boolean isGeneral()
    {
        return GENERAL.equals(this);
    }

    public boolean isCustom()
    {
        return CUSTOM.equals(this) || CUSTOM1.equals(this) || CUSTOM2.equals(this);
    }

    public static RaidCounterType defaultType()
    {
        return RaidCounterType.GENERAL;
    }
}
