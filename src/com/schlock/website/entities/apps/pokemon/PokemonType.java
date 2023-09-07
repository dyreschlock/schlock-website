package com.schlock.website.entities.apps.pokemon;

import java.util.Arrays;
import java.util.List;

public enum PokemonType
{
    BUG,
    DARK,
    DRAGON,
    ELECTRIC,
    FAIRY,
    FIGHTING,
    FIRE,
    FLYING,
    GHOST,
    GRASS,
    GROUND,
    ICE,
    NORMAL,
    POISON,
    PSYCHIC,
    ROCK,
    STEEL,
    WATER;

    private final String text;
    private final String gamemasterTag;

    PokemonType()
    {
        String thisname = this.name();

        this.text = thisname.substring(0, 1) + thisname.substring(1).toLowerCase();
        this.gamemasterTag = "POKEMON_TYPE_" + thisname;
    }

    public String text()
    {
        return this.text;
    }

    public String gamemasterTag()
    {
        return this.gamemasterTag;
    }

    public static String getTextByGamemasterTag(String gmTag)
    {
        for(PokemonType type : values())
        {
            if (type.gamemasterTag.equals(gmTag))
            {
                return type.text;
            }
        }
        return null;
    }

    public static List<PokemonType> preferredTypeOrder()
    {
        return Arrays.asList(
                FIRE,
                WATER,
                ELECTRIC,
                GRASS,
                ICE,
                FIGHTING,
                POISON,
                GROUND,
                FLYING,
                PSYCHIC,
                BUG,
                ROCK,
                GHOST,
                DRAGON,
                DARK,
                STEEL,
                FAIRY,
                NORMAL
        );
    }
}
