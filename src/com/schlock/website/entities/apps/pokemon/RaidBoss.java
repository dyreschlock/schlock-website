package com.schlock.website.entities.apps.pokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RaidBoss extends AbstractRaidPokemon
{
    private final RaidPokemonData data;

    private List<RaidCounterPokemon> megaCounters = new ArrayList<RaidCounterPokemon>();
    private List<RaidCounterPokemon> shadowCounters = new ArrayList<RaidCounterPokemon>();
    private List<RaidCounterPokemon> regularCounters = new ArrayList<RaidCounterPokemon>();

    private RaidBoss(RaidPokemonData data)
    {
        this.data = data;
    }


    public boolean isCountersGenerated()
    {
        return !megaCounters.isEmpty() && !shadowCounters.isEmpty() && !regularCounters.isEmpty();
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

    public List<RaidCounterPokemon> getMegaCounters()
    {
        return megaCounters;
    }

    public void setMegaCounters(List<RaidCounterPokemon> megaCounters)
    {
        this.megaCounters = megaCounters;
    }

    public List<RaidCounterPokemon> getShadowCounters()
    {
        return shadowCounters;
    }

    public void setShadowCounters(List<RaidCounterPokemon> shadowCounters)
    {
        this.shadowCounters = shadowCounters;
    }

    public List<RaidCounterPokemon> getRegularCounters()
    {
        return regularCounters;
    }

    public void setRegularCounters(List<RaidCounterPokemon> regularCounters)
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

    private static final String TITLE = "title_plain";
    private static final String TYPE = "type";

    private static final String ATTACK = "atk";
    private static final String DEFENSE = "def";
    private static final String STAMINA = "sta";
    private static final String CP = "cp";
    private static final String WEATHER_MAX = "weather_max";
    private static final String WEATHER_MIN = "weather_min";

    private static final String TYPE_DELIM = ",";

    public static RaidBoss createFromData(RaidPokemonData data)
    {
        return new RaidBoss(data);
    }
}
