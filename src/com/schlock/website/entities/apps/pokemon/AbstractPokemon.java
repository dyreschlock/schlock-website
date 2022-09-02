package com.schlock.website.entities.apps.pokemon;

import java.util.Set;

public abstract class AbstractPokemon
{
    private final static Integer DEFAULT_LEVEL = 40;

    private final static Integer DEFAULT_ATTACK_IV = 15;
    private final static Integer DEFAULT_DEFENSE_IV = 15;
    private final static Integer DEFAULT_STAMINA_IV = 15;

    private final PokemonData data;

    protected AbstractPokemon(PokemonData data)
    {
        this.data = data;
    }

    protected PokemonData getData()
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

    public int getAttackIV()
    {
        return DEFAULT_ATTACK_IV;
    }

    public int getDefenseIV()
    {
        return DEFAULT_DEFENSE_IV;
    }

    public int getStaminaIV()
    {
        return DEFAULT_STAMINA_IV;
    }

    public Set<PokemonMove> getStandardFastMoves()
    {
        return data.getStandardFastMoves();
    }

    public Set<PokemonMove> getStandardChargeMoves()
    {
        return data.getStandardChargeMoves();
    }
}
