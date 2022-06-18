package com.schlock.website.entities.apps.pokemon;

import java.util.Set;

public abstract class AbstractRaidPokemon
{
    public abstract boolean isShadow();

    public abstract boolean isMega();

    public abstract String getType1();

    public abstract String getType2();

    public abstract int getBaseAttack();

    public abstract int getBaseDefense();

    public abstract int getBaseStamina();

    public abstract Set<RaidMove> getStandardFastMoves();

    public abstract Set<RaidMove> getStandardChargeMoves();
}
