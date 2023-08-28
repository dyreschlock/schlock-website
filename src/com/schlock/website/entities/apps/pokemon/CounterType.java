package com.schlock.website.entities.apps.pokemon;

public enum CounterType
{
    GRAPHICAL, GENERAL, CUSTOM, CUSTOM1, CUSTOM2, TOP;

    public boolean isGraphical()
    {
        return GRAPHICAL.equals(this);
    }

    public boolean isGeneral()
    {
        return GENERAL.equals(this);
    }

    public boolean isCustom()
    {
        return CUSTOM.equals(this) || CUSTOM1.equals(this) || CUSTOM2.equals(this);
    }

    public boolean isTop()
    {
        return TOP.equals(this);
    }

    public int counterListSize()
    {
        if (isGeneral() || isGraphical())
        {
            return 30;
        }
        return 6;
    }

    public static CounterType defaultType()
    {
        return CounterType.GRAPHICAL;
    }
}
