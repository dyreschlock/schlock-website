package com.schlock.website.entities.apps.games;

public enum Condition
{
    SEALED, COMPLETE, LOOSE, DIGITAL, ROM;

    public String key()
    {
        return this.name().toLowerCase();
    }

    public static Condition parse(String value)
    {
        for(Condition condition : Condition.values())
        {
            if (condition.key().equals(value))
            {
                return condition;
            }
        }
        return null;
    }
}
