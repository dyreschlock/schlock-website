package com.schlock.website.entities.apps.pokemon;

public enum BattleMode
{
    RAID, ROCKET;

    public boolean isRaid()
    {
        return RAID.equals(this);
    }

    public boolean isRocket()
    {
        return ROCKET.equals(this);
    }
}
