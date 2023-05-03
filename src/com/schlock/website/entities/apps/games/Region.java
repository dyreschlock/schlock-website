package com.schlock.website.entities.apps.games;

public enum Region
{
    USA, JAPAN, PAL;

    public String key()
    {
        return this.name().toLowerCase();
    }

    public static Region parse(String value)
    {
        for(Region region : Region.values())
        {
            if(region.key().equals(value))
            {
                return region;
            }
        }
        return null;
    }
}
