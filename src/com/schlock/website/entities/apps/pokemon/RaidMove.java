package com.schlock.website.entities.apps.pokemon;

import org.apache.tapestry5.json.JSONObject;

public class RaidMove
{
    private String name;
    private String type;

    private int power;
    private double cooldown;

    private int energyGain;
    private int energyCost;

    private String dodgeWindow;
    private String damageWindow;

    private RaidMove()
    {
    }

    public String getName()
    {
        return name;
    }

    public String getType()
    {
        return type;
    }

    public int getPower()
    {
        return power;
    }

    public double getCooldown()
    {
        return cooldown;
    }

    public int getEnergyCost()
    {
        return energyCost;
    }

    public int getEnergyGain()
    {
        return energyGain;
    }

    public String getDamageWindow()
    {
        return damageWindow;
    }

    public String getDodgeWindow()
    {
        return dodgeWindow;
    }



    private static final String TITLE = "title";
    private static final String TYPE = "move_type";

    private static final String POWER = "power";
    private static final String COOLDOWN = "cooldown";

    private static final String ENERGY_GAIN = "energy_gain";
    private static final String ENERGY_COST = "energy_cost";

    private static final String DODGE_WINDOW = "dodge_window";
    private static final String DAMAGE_WINDOW = "damage_window";

    public static RaidMove createFromJSON(JSONObject object)
    {
        RaidMove move = new RaidMove();

        move.name = object.getString(TITLE);
        move.type = object.getString(TYPE);

        move.power = object.getInt(POWER);
        move.cooldown = object.getDouble(COOLDOWN);
        move.energyGain = getInt(object, ENERGY_GAIN);
        move.energyCost = getInt(object, ENERGY_COST);

        move.dodgeWindow = object.getString(DODGE_WINDOW);
        move.damageWindow = object.getString(DAMAGE_WINDOW);

        return move;
    }

    private static final String NEGATIVE = "-";

    private static int getInt(JSONObject object, String key)
    {
        String value = object.getString(key);

        if (value.isEmpty())
        {
            return 0;
        }

        if(value.startsWith(NEGATIVE))
        {
            return 0 - Integer.parseInt(value.substring(1));
        }
        return Integer.parseInt(value);
    }
}
