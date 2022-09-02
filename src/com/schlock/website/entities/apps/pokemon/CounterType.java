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

    public int counterListSize()
    {
        if (GENERAL.equals(this))
        {
            return 30;
        }
        return 6;
    }

    public static CounterType defaultType()
    {
        return CounterType.GENERAL;
    }
}
