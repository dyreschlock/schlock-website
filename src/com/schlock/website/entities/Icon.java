package com.schlock.website.entities;

public enum Icon
{
    SEED,
    PS_MEM,
    GC_MEM,
    DC_MEM,
    RSS,
    TWITTER,
    YOUTUBE,
    STACK,
    GITHUB,
    PSN,
    XBOX,
    STEAM,
    EBAY,
    DISCORD,
    TWITCH,
    BLUESKY;


    public String getIconPath()
    {
        String path = "assets/" + this.name().toLowerCase();
        path += ".png";

        return path;
    }


    public static Icon getGamePlatformIcon(String platformCode)
    {
        if (platformCode.length() >= 2)
        {
            String code = platformCode.substring(0, 2) + "_mem";
            for(Icon icon : values())
            {
                if (icon.name().equalsIgnoreCase(code))
                {
                    return icon;
                }
            }
        }
        return null;
    }
}
