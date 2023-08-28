package com.schlock.website.entities.apps.pokemon;

public abstract class AbstractCounterInstance implements Comparable<AbstractCounterInstance>
{
    private String name;
    private String number;
    private String imageLink;
    private String mainType;
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
        this.number = counter.getNumber();
        this.imageLink = counter.getImageLink();
        this.mainType = counter.getMainType();
        this.level = counter.getLevel();
        this.attackIV = counter.getAttackIV();
        this.defenseIV = counter.getDefenseIV();
        this.staminaIV = counter.getStaminaIV();

        this.fastMove = fastMove;
        this.chargeMove = chargeMove;
    }

    public String getName() { return name; }

    public String getNumber()
    {
        return number;
    }

    public String getImageLink()
    {
        return imageLink;
    }

    public String getMainType()
    {
        return mainType;
    }

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
