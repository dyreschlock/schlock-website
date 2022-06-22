package com.schlock.website.entities.apps.pokemon;

public enum RaidCounterType
{
    GENERAL, CUSTOM;

    public static RaidCounterType defaultType()
    {
        return RaidCounterType.GENERAL;
    }
}
