package com.schlock.website.entities.old;

public enum SiteVersion
{
    V1,
    V2,
    V3,
    V4,
    V5,
    V6,
    V7;

    public static SiteVersion getVersion(String param)
    {
        for(SiteVersion v : SiteVersion.values())
        {
            if (v.name().endsWith(param))
            {
                return v;
            }
        }
        return null;
    }
}
