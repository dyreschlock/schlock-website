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

    public String getCategory()
    {
        return category;
    }

    public boolean isChargeMove()
    {
        return CHARGE_MOVE.equalsIgnoreCase(category);
    }

    public boolean isFastMove()
    {
        return FAST_MOVE.equalsIgnoreCase(category);
    }

    public double getEnergyDelta()
    {
        if (isFastMove())
        {
            return getEnergyGain();
        }
        if (isChargeMove())
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



    public static final String TITLE = "title";
    public static final String TYPE = "move_type";
    public static final String CATEGORY = "move_category";

    public static final String POWER = "power"; // Power is the same as Damage
    public static final String COOLDOWN = "cooldown"; // Cooldown is the same as Duration

    public static final String ENERGY_GAIN = "energy_gain";
    public static final String ENERGY_COST = "energy_cost";

    public static final String DODGE_WINDOW = "dodge_window"; // this is Damage End - Damage Start
    public static final String DAMAGE_WINDOW = "damage_window"; // this is the same as Damage Start

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
