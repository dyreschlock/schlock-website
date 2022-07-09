package com.schlock.website.entities.apps.pokemon;

public enum RaidCounterType
{
    GENERAL, CUSTOM;

    public boolean isGeneral()
    {
        return GENERAL.equals(this);
    }

    public boolean isCustom()
    {
        return CUSTOM.equals(this);
    }

    public static RaidCounterType defaultType()
    {
        return RaidCounterType.GENERAL;
    }
}
