package com.schlock.website.entities.apps.ps2;

public enum PlaystationGameAspect
{
    STAND("aspect/s","4:3"),
    WIDE("aspect/w","16:9");


    private final String aspect;
    private final String aspectText;

    PlaystationGameAspect(String aspect, String aspectText)
    {
        this.aspect = aspect;
        this.aspectText = aspectText;
    }

    public String aspect()
    {
        return this.aspect;
    }

    public String aspectText()
    {
        return this.aspectText;
    }


    public static PlaystationGameAspect getFromText(String aspect)
    {
        if (STAND.aspect.equals(aspect))
        {
            return STAND;
        }
        if (WIDE.aspect.equals(aspect))
        {
            return WIDE;
        }
        return null;
    }
}
