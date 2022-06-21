package com.schlock.website.entities.apps.pokemon;

import java.util.Set;

public class RaidCounter extends AbstractRaidPokemon
{
    private int level;

    private RaidCounter(RaidPokemonData data)
    {
        super(data);
    }

    public int getLevel()
    {
        return level;
    }

    public Set<RaidMove> getAllFastMoves()
    {
        return getData().getAllFastMoves();
    }

    public Set<RaidMove> getAllChargeMoves()
    {
        return getData().getAllChargeMoves();
    }

    public static RaidCounter createFromData(RaidPokemonData data, int level)
    {
        RaidCounter counter = new RaidCounter(data);
        counter.level = level;

        return counter;
    }
}
