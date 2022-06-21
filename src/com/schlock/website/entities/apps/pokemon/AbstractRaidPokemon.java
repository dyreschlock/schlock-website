package com.schlock.website.entities.apps.pokemon;

import java.util.Set;

public abstract class AbstractRaidPokemon
{
    private final static Integer DEFAULT_LEVEL = 40;

    private final RaidPokemonData data;

    protected AbstractRaidPokemon(RaidPokemonData data)
    {
        this.data = data;
    }

    protected RaidPokemonData getData()
    {
        return data;
    }

    public String getName()
    {
        return data.getName();
    }

    public int getLevel()
    {
        return DEFAULT_LEVEL;
    }

    public boolean isShadow()
    {
        return data.isShadow();
    }

    public boolean isMega()
    {
        return data.isMega();
    }

    public String getType1()
    {
        return data.getType1();
    }

    public String getType2()
    {
        return data.getType2();
    }

    public int getBaseAttack()
    {
        return data.getBaseAttack();
    }

    public int getBaseDefense()
    {
        return data.getBaseDefense();
    }

    public int getBaseStamina()
    {
        return data.getBaseStamina();
    }

    public Set<RaidMove> getStandardFastMoves()
    {
        return data.getStandardFastMoves();
    }

    public Set<RaidMove> getStandardChargeMoves()
    {
        return data.getStandardChargeMoves();
    }
}
