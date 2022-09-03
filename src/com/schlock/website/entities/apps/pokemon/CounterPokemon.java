package com.schlock.website.entities.apps.pokemon;

import java.util.HashSet;
import java.util.Set;

public class CounterPokemon extends AbstractPokemon
{
    private int level;
    private double cpm;

    private Integer attackIv;
    private Integer defenseIv;
    private Integer staminaIv;

    private Set<PokemonMove> fastMoves = new HashSet<PokemonMove>();
    private Set<PokemonMove> chargeMoves = new HashSet<PokemonMove>();

    private CounterPokemon(PokemonData data)
    {
        super(data);
    }

    public int getLevel()
    {
        return level;
    }

    public double getCPM()
    {
        return cpm;
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

    private static final double MEGA_POKEMON_STAT_MULTIPLIER = 1.1;
    private static final double SHADOW_DEFENSE_MULTIPLIER = 0.8333333;

    public double getDefense()
    {
        double defense = super.getDefense();
        if (isMega())
        {
            return defense * MEGA_POKEMON_STAT_MULTIPLIER;
        }
        if (isShadow())
        {
            return defense * SHADOW_DEFENSE_MULTIPLIER;
        }
        return defense;
    }

    public Set<PokemonMove> getAllFastMoves()
    {
        if (fastMoves.isEmpty())
        {
            return getData().getAllFastMoves();
        }
        return fastMoves;
    }

    public Set<PokemonMove> getAllChargeMoves()
    {
        if (chargeMoves.isEmpty())
        {
            return getData().getAllChargeMoves();
        }
        return chargeMoves;
    }

    public Set<PokemonMove> getStandardFastMoves()
    {
        if (fastMoves.isEmpty())
        {
            return super.getStandardFastMoves();
        }
        return fastMoves;
    }

    public Set<PokemonMove> getStandardChargeMoves()
    {
        if (chargeMoves.isEmpty())
        {
            return super.getStandardChargeMoves();
        }
        return chargeMoves;
    }

    public static CounterPokemon createFromData(PokemonData data, int level, double cpm)
    {
        CounterPokemon counter = new CounterPokemon(data);
        counter.level = level;
        counter.cpm = cpm;

        return counter;
    }

    public static CounterPokemon createCustom(PokemonData data,
                                              int level,
                                              double cpm,
                                              int attackIV,
                                              int defenseIV,
                                              int staminaIV)
    {
        CounterPokemon counter = createFromData(data, level, cpm);

        counter.attackIv = attackIV;
        counter.defenseIv = defenseIV;
        counter.staminaIv = staminaIV;

        return counter;
    }


    public static CounterPokemon createCustom(PokemonData data,
                                              int level,
                                              double cpm,
                                              int attackIV,
                                              int defenseIV,
                                              int staminaIV,
                                              String fastMoveNames,
                                              String chargeMoveNames)
    {
        CounterPokemon counter = createCustom(data, level, cpm, attackIV, defenseIV, staminaIV);

        if (fastMoveNames != null && !fastMoveNames.isEmpty())
        {
            Set<PokemonMove> fastMoves = getMoves(counter.getAllFastMoves(), fastMoveNames);
            counter.fastMoves.addAll(fastMoves);
        }

        if (chargeMoveNames != null && !chargeMoveNames.isEmpty())
        {
            Set<PokemonMove> chargeMoves = getMoves(counter.getAllChargeMoves(), chargeMoveNames);
            counter.chargeMoves.addAll(chargeMoves);
        }

        return counter;
    }

    private static final String DELIM = ",";

    private static Set<PokemonMove> getMoves(Set<PokemonMove> allMoves, String moveNames)
    {
        Set<PokemonMove> moves = new HashSet<PokemonMove>();

        String[] names = moveNames.split(DELIM);
        for (String moveName : names)
        {
            PokemonMove move = getMove(allMoves, moveName.trim());
            moves.add(move);
        }
        return moves;
    }

    private static PokemonMove getMove(Set<PokemonMove> allMoves, String moveName)
    {
        for (PokemonMove move : allMoves)
        {
            if (move.getName().equalsIgnoreCase(moveName))
            {
                return move;
            }
        }
        throw new RuntimeException("Move not found: " + moveName);
    }
}
