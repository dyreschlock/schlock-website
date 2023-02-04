package com.schlock.website.entities;

public enum Icon
{
    RSS_DARK,
    TWITTER_DARK,
    YOUTUBE_DARK,
    STACK_DARK,
    GITHUB_DARK,
    PSN_DARK,
    XBOX_DARK,
    STEAM_DARK,
    EBAY_DARK,
    DISCORD_DARK,
    TWITCH_DARK,

    RSS_LIGHT,
    TWITTER_LIGHT,
    YOUTUBE_LIGHT,
    GITHUB_LIGHT,
    EBAY_LIGHT,
    DISCORD_LIGHT,
    TWITCH_LIGHT;


    public String getIconPath()
    {
        String path = "icons/" + this.name().toLowerCase();
        path += ".png";

        return path;
    }
}
