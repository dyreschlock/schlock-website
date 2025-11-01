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
}
