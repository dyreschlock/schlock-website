package com.schlock.website.entities.apps.pocket;

public enum Device
{
    MISTER,
    POCKET;

    public static Device value(String value)
    {
        try
        {
            return valueOf(value.toUpperCase());
        }
        catch(Exception e)
        {
            return null;
        }
    }
}
