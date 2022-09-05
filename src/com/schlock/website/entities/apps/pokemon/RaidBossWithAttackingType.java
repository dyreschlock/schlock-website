package com.schlock.website.entities.apps.pokemon;

import java.util.Collections;
import java.util.Set;

public class RaidBossWithAttackingType extends RaidBossPokemon
{
    private String attackingType;
    private String type;

    private RaidBossWithAttackingType(PokemonData data)
    {
        super(data);
    }

    public String getAttackingType()
    {
        return attackingType;
    }

    public String getType1()
    {
        return type;
    }

    public String getType2()
    {
        return null;
    }

    public Set<PokemonMove> getStandardFastMoves()
    {
        return Collections.EMPTY_SET;
    }

    public Set<PokemonMove> getStandardChargeMoves()
    {
        return Collections.EMPTY_SET;
    }

    public static RaidBossWithAttackingType createFromData(PokemonData data, String arceusType, String attackingType)
    {
        RaidBossWithAttackingType boss = new RaidBossWithAttackingType(data);
        boss.attackingType = attackingType;
        boss.type = arceusType;

        return boss;
    }
}
