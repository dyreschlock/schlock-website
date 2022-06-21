package com.schlock.website.entities.apps.pokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RaidBoss extends AbstractRaidPokemon
{
    private RaidCounterType counterType;

    private List<RaidCounterInstance> megaCounters = new ArrayList<RaidCounterInstance>();
    private List<RaidCounterInstance> shadowCounters = new ArrayList<RaidCounterInstance>();
    private List<RaidCounterInstance> regularCounters = new ArrayList<RaidCounterInstance>();

    private RaidBoss(RaidPokemonData data)
    {
        super(data);
    }

    public boolean isShadow()
    {
        return false;
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

    public static RaidBoss createFromData(RaidPokemonData data)
    {
        return new RaidBoss(data);
    }
}
