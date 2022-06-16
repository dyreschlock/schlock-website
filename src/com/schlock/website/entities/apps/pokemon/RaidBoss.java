package com.schlock.website.entities.apps.pokemon;

import org.apache.tapestry5.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RaidBoss
{
    private String id;
    private String name;

    private String type;

    private int attack;
    private int defense;
    private int stamina;

    private int cp;

    private int weatherMax;
    private int weatherMin;


    private List<RaidCounterPokemon> megaCounters = new ArrayList<RaidCounterPokemon>();
    private List<RaidCounterPokemon> shadowCounters = new ArrayList<RaidCounterPokemon>();
    private List<RaidCounterPokemon> regularCounters = new ArrayList<RaidCounterPokemon>();

    private RaidBoss()
    {
    }

    public RaidBoss(String id)
    {
        this.id = id;
    }


    public String getId() { return id; }

    public String getName()
    {
        return name;
    }

    public String getType()
    {
        return type;
    }


    public int getAttack()
    {
        return attack;
    }

    public int getDefense()
    {
        return defense;
    }

    public int getStamina()
    {
        return stamina;
    }

    public int getCp()
    {
        return cp;
    }

    public int getWeatherMax()
    {
        return weatherMax;
    }

    public int getWeatherMin()
    {
        return weatherMin;
    }


    public List<RaidCounterPokemon> getMegaCounters()
    {
        return megaCounters;
    }

    public List<RaidCounterPokemon> getShadowCounters()
    {
        return shadowCounters;
    }

    public List<RaidCounterPokemon> getRegularCounters()
    {
        return regularCounters;
    }



    private static final String TITLE = "title_plain";
    private static final String TYPE = "type";

    private static final String ATTACK = "atk";
    private static final String DEFENSE = "def";
    private static final String STAMINA = "sta";
    private static final String CP = "cp";
    private static final String WEATHER_MAX = "weather_max";
    private static final String WEATHER_MIN = "weather_min";

    public static RaidBoss createFromJSON(JSONObject object)
    {
        RaidBoss boss = new RaidBoss();

        boss.name = object.getString(TITLE);
        boss.id = boss.name;

        boss.type = object.getString(TYPE);

        boss.attack = object.getInt(ATTACK);
        boss.defense = object.getInt(DEFENSE);
        boss.stamina = object.getInt(STAMINA);
        boss.cp = object.getInt(CP);

        boss.weatherMax = object.getInt(WEATHER_MAX);
        boss.weatherMin = object.getInt(WEATHER_MIN);

        return boss;
    }
}
