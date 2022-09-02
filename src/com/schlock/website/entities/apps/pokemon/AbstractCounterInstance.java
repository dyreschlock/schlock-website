package com.schlock.website.entities.apps.pokemon;

public abstract class AbstractCounterInstance
{
    private String name;
    protected String fastMove;
    protected String chargeMove;

    private int level;

    private int attackIV;
    private int defenseIV;
    private int staminaIV;

    protected AbstractCounterInstance()
    {
    }

    protected AbstractCounterInstance(CounterPokemon counter, String fastMove, String chargeMove)
    {
        this.name = counter.getName();
        this.level = counter.getLevel();
        this.attackIV = counter.getAttackIV();
        this.defenseIV = counter.getDefenseIV();
        this.staminaIV = counter.getStaminaIV();

        this.fastMove = fastMove;
        this.chargeMove = chargeMove;
    }

    public String getName() { return name; }

    public String getFastMove() { return fastMove; }

    public String getChargeMove() { return chargeMove; }

    public int getLevel()
    {
        return level;
    }

    public int getAttackIV()
    {
        return attackIV;
    }

    public int getDefenseIV()
    {
        return defenseIV;
    }

    public int getStaminaIV()
    {
        return staminaIV;
    }
}
