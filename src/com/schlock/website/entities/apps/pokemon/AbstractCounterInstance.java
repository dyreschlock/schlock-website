package com.schlock.website.entities.apps.pokemon;

public abstract class AbstractCounterInstance implements Comparable<AbstractCounterInstance>
{
    private static final String ELITE_MOVE = "**";

    private String name;
    private String number;
    private String imageLink;
    private String mainType;
    protected String fastMove;
    protected String chargeMove;

    private boolean isEliteFastMove = false;
    private boolean isEliteChargeMove = false;

    private int level;

    private int attackIV;
    private int defenseIV;
    private int staminaIV;

    protected AbstractCounterInstance()
    {
    }

    protected AbstractCounterInstance(CounterPokemon counter, PokemonMove fastMove, PokemonMove chargeMove)
    {
        this.name = counter.getName();
        this.number = counter.getNumber();
        this.imageLink = counter.getImageLink();
        this.mainType = counter.getMainType(fastMove, chargeMove);
        this.level = counter.getLevel();
        this.attackIV = counter.getAttackIV();
        this.defenseIV = counter.getDefenseIV();
        this.staminaIV = counter.getStaminaIV();

        this.fastMove = fastMove.getName();
        this.isEliteFastMove = !counter.getStandardFastMoves().contains(fastMove);

        this.chargeMove = chargeMove.getName();
        this.isEliteChargeMove = !counter.getStandardChargeMoves().contains(chargeMove);
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

    public String getFastMoveName()
    {
        if (isEliteFastMove)
        {
            return this.fastMove + ELITE_MOVE;
        }
        return this.fastMove;
    }

    public String getChargeMoveName()
    {
        if (isEliteChargeMove)
        {
            return this.chargeMove + ELITE_MOVE;
        }
        return this.chargeMove;
    }

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
