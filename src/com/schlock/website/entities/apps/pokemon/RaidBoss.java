package com.schlock.website.entities.apps.pokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RaidBoss extends AbstractRaidPokemon
{
    private final RaidPokemonData data;

    private RaidCounterType counterType;

    private List<RaidCounterInstance> megaCounters = new ArrayList<RaidCounterInstance>();
    private List<RaidCounterInstance> shadowCounters = new ArrayList<RaidCounterInstance>();
    private List<RaidCounterInstance> regularCounters = new ArrayList<RaidCounterInstance>();

    private RaidBoss(RaidPokemonData data)
    {
        this.data = data;
    }


    public boolean isCountersGenerated(RaidCounterType type)
    {
        if (counterType != null && counterType.equals(type))
        {
            return !megaCounters.isEmpty() && !shadowCounters.isEmpty() && !regularCounters.isEmpty();
        }
        return false;
    }

    public void setCounterType(RaidCounterType counterType)
    {
        this.counterType = counterType;
    }

    public String getName()
    {
        return data.getName();
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

    public Set<RaidMove> getStandardFastMoves()
    {
        return data.getStandardFastMoves();
    }

    public Set<RaidMove> getStandardChargeMoves()
    {
        return data.getStandardChargeMoves();
    }

    public List<RaidCounterInstance> getMegaCounters()
    {
        return megaCounters;
    }

    public void setMegaCounters(List<RaidCounterInstance> megaCounters)
    {
        this.megaCounters = megaCounters;
    }

    public List<RaidCounterInstance> getShadowCounters()
    {
        return shadowCounters;
    }

    public void setShadowCounters(List<RaidCounterInstance> shadowCounters)
    {
        this.shadowCounters = shadowCounters;
    }

    public List<RaidCounterInstance> getRegularCounters()
    {
        return regularCounters;
    }

    public void setRegularCounters(List<RaidCounterInstance> regularCounters)
    {
        this.regularCounters = regularCounters;
    }

    public boolean isShadow()
    {
        return false;
    }

    public boolean isMega()
    {
        return data.isMega();
    }


    public static RaidBoss createFromData(RaidPokemonData data)
    {
        return new RaidBoss(data);
    }
}
