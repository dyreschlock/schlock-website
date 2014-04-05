package com.schlock.website.entities;

public enum Icon
{
    RSS_DARK,
    TWITTER_DARK,
    FACEBOOK_DARK,
    GOOGLE_DARK,
    YOUTUBE_DARK,
    STACK_DARK,
    GITHUB_DARK,
    PSN_DARK,
    XBOX_DARK,
    STEAM_DARK,
    EBAY_DARK,

    RSS_LIGHT,
    TWITTER_LIGHT,
    FACEBOOK_LIGHT,
    GOOGLE_LIGHT,
    YOUTUBE_LIGHT,
    EBAY_LIGHT;


    public String getIconPath()
    {
        String path = "icons/" + this.name().toLowerCase();
        path += ".png";

        return path;
    }
}
