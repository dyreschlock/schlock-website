package com.schlock.website.entities.apps.pokemon;

import java.util.*;

public class RaidBoss extends AbstractRaidPokemon
{
    private Map<RaidCounterType, List<RaidCounterInstance>> megaCounters = new HashMap<RaidCounterType, List<RaidCounterInstance>>();
    private Map<RaidCounterType, List<RaidCounterInstance>> shadowCounters = new HashMap<RaidCounterType, List<RaidCounterInstance>>();
    private Map<RaidCounterType, List<RaidCounterInstance>> regularCounters = new HashMap<RaidCounterType, List<RaidCounterInstance>>();

    private RaidBoss(RaidPokemonData data)
    {
        super(data);
    }

    public boolean isShadow()
    {
        return false;
    }

    public boolean isCountersGenerated(RaidCounterType counterType)
    {
        return megaCounters.get(counterType) != null &&
                shadowCounters.get(counterType) != null &&
                regularCounters.get(counterType) != null;
    }

    public List<RaidCounterInstance> getMegaCounters(RaidCounterType type)
    {
        return megaCounters.get(type);
    }

    public List<RaidCounterInstance> getShadowCounters(RaidCounterType type)
    {
        return shadowCounters.get(type);
    }

    public List<RaidCounterInstance> getRegularCounters(RaidCounterType type)
    {
        return regularCounters.get(type);
    }

    public void setMegaCounters(RaidCounterType type, List<RaidCounterInstance> counters)
    {
        this.megaCounters.put(type, counters);
    }

    public void setShadowCounters(RaidCounterType type, List<RaidCounterInstance> counters)
    {
        this.shadowCounters.put(type, counters);
    }

    public void setRegularCounters(RaidCounterType type, List<RaidCounterInstance> counters)
    {
        this.regularCounters.put(type, counters);
    }

    public static RaidBoss createFromData(RaidPokemonData data)
    {
        return new RaidBoss(data);
    }
}
