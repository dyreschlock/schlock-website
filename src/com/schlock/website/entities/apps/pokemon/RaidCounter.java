package com.schlock.website.entities.apps.pokemon;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RaidCounter extends AbstractRaidPokemon
{
    private int level;

    private Integer attackIv;
    private Integer defenseIv;
    private Integer staminaIv;

    private Set<RaidMove> fastMoves = new HashSet<RaidMove>();
    private Set<RaidMove> chargeMoves = new HashSet<RaidMove>();

    private RaidCounter(RaidPokemonData data)
    {
        super(data);
    }

    public int getLevel()
    {
        return level;
    }

    public int getAttackIV()
    {
        if (attackIv == null)
        {
            return super.getAttackIV();
        }
        return attackIv;
    }

    public int getDefenseIV()
    {
        if (defenseIv == null)
        {
            return super.getDefenseIV();
        }
        return defenseIv;
    }

    public int getStaminaIV()
    {
        if (staminaIv == null)
        {
            return super.getStaminaIV();
        }
        return staminaIv;
    }

    public Set<RaidMove> getAllFastMoves()
    {
        if (fastMoves.isEmpty())
        {
            return getData().getAllFastMoves();
        }
        return fastMoves;
    }

    public Set<RaidMove> getAllChargeMoves()
    {
        if (chargeMoves.isEmpty())
        {
            return getData().getAllChargeMoves();
        }
        return chargeMoves;
    }

    public Set<RaidMove> getStandardFastMoves()
    {
        if (fastMoves.isEmpty())
        {
            return super.getStandardFastMoves();
        }
        return fastMoves;
    }

    public Set<RaidMove> getStandardChargeMoves()
    {
        if (chargeMoves.isEmpty())
        {
            return super.getStandardChargeMoves();
        }
        return chargeMoves;
    }

    public static RaidCounter createFromData(RaidPokemonData data, int level)
    {
        RaidCounter counter = new RaidCounter(data);
        counter.level = level;

        return counter;
    }

    public static RaidCounter createCustom(RaidPokemonData data,
                                           int level,
                                           int attackIV,
                                           int defenseIV,
                                           int staminaIV)
    {
        RaidCounter counter = createFromData(data, level);

        counter.attackIv = attackIV;
        counter.defenseIv = defenseIV;
        counter.staminaIv = staminaIV;

        return counter;
    }


    public static RaidCounter createCustom(RaidPokemonData data,
                                           int level,
                                           int attackIV,
                                           int defenseIV,
                                           int staminaIV,
                                           String fastMoveNames,
                                           String chargeMoveNames)
    {
        RaidCounter counter = createCustom(data, level, attackIV, defenseIV, staminaIV);

        if (fastMoveNames != null && !fastMoveNames.isEmpty())
        {
            Set<RaidMove> fastMoves = getMoves(counter.getAllFastMoves(), fastMoveNames);
            counter.fastMoves.addAll(fastMoves);
        }

        if (chargeMoveNames != null && !chargeMoveNames.isEmpty())
        {
            Set<RaidMove> chargeMoves = getMoves(counter.getAllChargeMoves(), chargeMoveNames);
            counter.chargeMoves.addAll(chargeMoves);
        }

        return counter;
    }

    private static final String DELIM = ",";

    private static Set<RaidMove> getMoves(Set<RaidMove> allMoves, String moveNames)
    {
        Set<RaidMove> moves = new HashSet<RaidMove>();

        String[] names = moveNames.split(DELIM);
        for (String moveName : names)
        {
            RaidMove move = getMove(allMoves, moveName.trim());
            moves.add(move);
        }
        return moves;
    }

    private static RaidMove getMove(Set<RaidMove> allMoves, String moveName)
    {
        for (RaidMove move : allMoves)
        {
            if (move.getName().equalsIgnoreCase(moveName))
            {
                return move;
            }
        }
        throw new RuntimeException("Move not found: " + moveName);
    }
}
