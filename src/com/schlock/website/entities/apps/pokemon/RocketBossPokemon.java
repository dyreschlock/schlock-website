package com.schlock.website.entities.apps.pokemon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RocketBossPokemon extends AbstractPokemon
{
    private Map<CounterType, List<RocketCounterInstance>> counters = new HashMap<CounterType, List<RocketCounterInstance>>();

    private RocketBossPokemon(PokemonData data)
    {
        super(data);
    }


    public String getName()
    {
        return getData().getName();
    }

    public List<RocketCounterInstance> getCounters(CounterType type)
    {
        return counters.get(type);
    }

    public void setCounters(CounterType type, List<RocketCounterInstance> counters)
    {
        this.counters.put(type, counters);
    }


    public static RocketBossPokemon createFromData(PokemonData data)
    {
        return new RocketBossPokemon(data);
    }
}
