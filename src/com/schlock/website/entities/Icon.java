package com.schlock.website.entities;

public enum Icon
{
    SEED,
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
    TWITCH;


    public String getIconPath()
    {
        String path = "icons/" + this.name().toLowerCase();
        path += ".png";

        return path;
    }
}
