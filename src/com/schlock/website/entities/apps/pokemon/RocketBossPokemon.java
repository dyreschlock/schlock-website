package com.schlock.website.entities.apps.pokemon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RocketBossPokemon extends AbstractPokemon
{
    private final static Integer DEFAULT_DEFENSE_IV = 15;
    private final static Integer DEFAULT_STAMINA_IV = 9;

    private final static Double ROCKET_LEADER_CPM = 1.0 // for level 50
                                                    + 1.05; // for leader multiplier

    private final static Double ROCKET_ATTACK_MULTIPLIER = 2.0;
    private final static Double ROCKET_DEFENSE_MULTIPLIER = 0.8;
    private final static Double ROCKET_STAMINA_MULTIPLIER = 1.1;

    private Map<CounterType, List<RocketCounterInstance>> counters = new HashMap<CounterType, List<RocketCounterInstance>>();

    private RocketBossPokemon(PokemonData data)
    {
        super(data);
    }

    public int getAttackIV()
    {
        Double iv = Math.floor(2.0 / 3.0 * getBaseAttack() + 25);
        return iv.intValue();
    }

    public int getDefenseIV()
    {
        return DEFAULT_DEFENSE_IV;
    }

    public int getStaminaIV()
    {
        return DEFAULT_STAMINA_IV;
    }

    public double getAttack()
    {
        return ROCKET_ATTACK_MULTIPLIER * super.getAttack();
    }

    public double getDefense()
    {
        return ROCKET_DEFENSE_MULTIPLIER * super.getDefense();
    }

    public double getStamina()
    {
        return ROCKET_STAMINA_MULTIPLIER * super.getStamina();
    }

    public double getCPM()
    {
        return ROCKET_LEADER_CPM;
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
