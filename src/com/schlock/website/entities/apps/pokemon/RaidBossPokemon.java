package com.schlock.website.entities.apps.pokemon;

import java.util.*;

public class RaidBossPokemon extends AbstractPokemon
{
    private Map<CounterType, List<RaidCounterInstance>> megaCounters = new HashMap<CounterType, List<RaidCounterInstance>>();
    private Map<CounterType, List<RaidCounterInstance>> shadowCounters = new HashMap<CounterType, List<RaidCounterInstance>>();
    private Map<CounterType, List<RaidCounterInstance>> regularCounters = new HashMap<CounterType, List<RaidCounterInstance>>();

    protected RaidBossPokemon(PokemonData data)
    {
        super(data);
    }

    public boolean isShadow()
    {
        return false;
    }

    public boolean isCountersGenerated(CounterType counterType)
    {
        return megaCounters.get(counterType) != null &&
                shadowCounters.get(counterType) != null &&
                regularCounters.get(counterType) != null;
    }

    public List<RaidCounterInstance> getMegaCounters(CounterType type)
    {
        return megaCounters.get(type);
    }

    public List<RaidCounterInstance> getShadowCounters(CounterType type)
    {
        return shadowCounters.get(type);
    }

    public List<RaidCounterInstance> getRegularCounters(CounterType type)
    {
        return regularCounters.get(type);
    }

    public void setMegaCounters(CounterType type, List<RaidCounterInstance> counters)
    {
        this.megaCounters.put(type, counters);
    }

    public void setShadowCounters(CounterType type, List<RaidCounterInstance> counters)
    {
        this.shadowCounters.put(type, counters);
    }

    public void setRegularCounters(CounterType type, List<RaidCounterInstance> counters)
    {
        this.regularCounters.put(type, counters);
    }

    public static RaidBossPokemon createFromData(PokemonData data)
    {
        return new RaidBossPokemon(data);
    }
}
