package com.schlock.website.entities;

import org.apache.tapestry5.services.AssetSource;

public enum Icon
{
    TWITTER,
    FACEBOOK,
    YOUTUBE,
    STACK, //stack overflow
    GITHUB,
    PSN,
    XBOX,
    STEAM,
    EBAY;



    public String getLightIconPath(AssetSource assetSource)
    {
        String path = "context:" + iconPath(false, true);
        return assetSource.getAsset(null, path, null).toClientURL();
    }

    public String getDarkIconPath(AssetSource assetSource)
    {
        String path = "context:" + iconPath(true, false);
        return assetSource.getAsset(null, path, null).toClientURL();
    }

    private String iconPath(boolean dark, boolean light)
    {
        String path = "icons/" + this.name().toLowerCase();

        if (dark)
        {
            path += "_dark";
        }
        if (light)
        {
            path += "_light";
        }
        path += ".png";

        return path;
    }
}
