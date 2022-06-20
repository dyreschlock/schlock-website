package com.schlock.website.entities.apps.pokemon;

public enum RaidCounterType
{
    GENERAL, PERSONAL;

    public static RaidCounterType defaultType()
    {
        return RaidCounterType.GENERAL;
    }
}
