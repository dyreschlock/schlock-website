package com.schlock.website.entities.apps.pokemon;

import java.util.Set;

public class RaidCounter extends AbstractRaidPokemon
{
    private RaidPokemonData data;

    private RaidCounter(RaidPokemonData data)
    {
        this.data = data;
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

    public static RaidCounter createFromData(RaidPokemonData data)
    {
        return new RaidCounter(data);
    }
}
