package com.schlock.website.entities.apps.pokemon;

import org.apache.tapestry5.json.JSONObject;

public class RaidMove
{
    private static final String FAST_MOVE = "Fast Move";
    private static final String CHARGE_MOVE = "Charge Move";

    private String name;
    private String type;
    private String category;

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

    public String getDodgeWindow()
    {
        return dodgeWindow;
    }

    public double getEnergyDelta()
    {
        if (FAST_MOVE.equalsIgnoreCase(category))
        {
            return getEnergyGain();
        }
        if (CHARGE_MOVE.equalsIgnoreCase(category))
        {
            return getEnergyCost();
        }
        return 0.0;
    }

    public double getDuration()
    {
        return cooldown * 1000;
    }

    private static final String DAMAGE_WINDOW_DELIM = " ";

    public double getDamageWindow()
    {
        String dw = damageWindow.split(DAMAGE_WINDOW_DELIM)[0];

        return Double.parseDouble(dw) * 1000;
    }



    private static final String TITLE = "title";
    private static final String TYPE = "move_type";
    private static final String CATEGORY = "move_category";

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
        move.category = object.getString(CATEGORY);

        move.power = object.getInt(POWER);
        move.cooldown = object.getDouble(COOLDOWN);
        move.energyGain = getInt(object, ENERGY_GAIN);
        move.energyCost = getInt(object, ENERGY_COST);
        if (move.energyCost > 0)
        {
            move.energyCost = -move.energyCost;
        }

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
