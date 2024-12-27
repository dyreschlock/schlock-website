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

    public String linkPath(String uuid)
    {
        final String PATH = "old/%s/%s";
        final String v = this.name().toLowerCase();

        return String.format(PATH, v, uuid);
    }
}
