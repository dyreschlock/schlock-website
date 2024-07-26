package com.schlock.website.entities.apps.pokemon;

import com.schlock.website.entities.Persisted;

public class PokemonCustomCounter extends Persisted
{
    private PokemonCustomCounterAccount account;

    private String name;

    private int level;
    private int attackIV;
    private int defenseIV;
    private int staminaIV;

    private String fastMoves;
    private String chargeMoves;

    public PokemonCustomCounterAccount getAccount()
    {
        return account;
    }

    public void setAccount(PokemonCustomCounterAccount account)
    {
        this.account = account;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public int getAttackIV()
    {
        return attackIV;
    }

    public void setAttackIV(int attackIV)
    {
        this.attackIV = attackIV;
    }

    public int getDefenseIV()
    {
        return defenseIV;
    }

    public void setDefenseIV(int defenseIV)
    {
        this.defenseIV = defenseIV;
    }

    public int getStaminaIV()
    {
        return staminaIV;
    }

    public void setStaminaIV(int staminaIV)
    {
        this.staminaIV = staminaIV;
    }

    public String getFastMoves()
    {
        return fastMoves;
    }

    public void setFastMoves(String fastMoves)
    {
        this.fastMoves = fastMoves;
    }

    public String getChargeMoves()
    {
        return chargeMoves;
    }

    public void setChargeMoves(String chargeMoves)
    {
        this.chargeMoves = chargeMoves;
    }
}
