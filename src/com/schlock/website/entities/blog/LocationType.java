package com.schlock.website.entities.blog;

public enum LocationType
{
    URBAN,
    TOWER,
    MUSEUM,
    EVENT,
    HISTORY,
    RUINS,
    NATURE;

    public String getIconPath()
    {
        String path = "/assets/marker/%s-icon.png";
        path = String.format(path, this.name().toLowerCase());

        return path;
    }

    public static String getShadowIconPath()
    {
        return "/assets/marker/marker-shadow.png";
    }
}
