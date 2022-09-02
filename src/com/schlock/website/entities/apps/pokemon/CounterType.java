package com.schlock.website.entities.apps.pokemon;

public enum CounterType
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

    public static CounterType defaultType()
    {
        return CounterType.GENERAL;
    }
}
