package com.schlock.website.entities.apps.pokemon;

import org.apache.tapestry5.json.JSONObject;

public class PokemonMove
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

    private int pvpChargeEnergy;
    private int pvpChargeDamage;

    private int pvpFastEnergy;
    private int pvpFastPower;
    private double pvpFastDuration;

    private PokemonMove()
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

    public String getCategory()
    {
        return category;
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

    protected double getCooldown()
    {
        return cooldown;
    }

    protected int getPvpFastPower()
    {
        return pvpFastPower;
    }

    protected int getPvpFastEnergy()
    {
        return pvpFastEnergy;
    }

    protected double getPvpFastDuration()
    {
        return pvpFastDuration;
    }

    protected int getPvpChargeDamage()
    {
        return pvpChargeDamage;
    }

    protected int getPvpChargeEnergy()
    {
        return pvpChargeEnergy;
    }

    public boolean isChargeMove()
    {
        return CHARGE_MOVE.equalsIgnoreCase(category);
    }

    public boolean isFastMove()
    {
        return FAST_MOVE.equalsIgnoreCase(category);
    }

    public int getPower(BattleMode battleMode)
    {
        int power = 0;
        if(battleMode.isRaid())
        {
            power = this.power;
        }
        if(battleMode.isRocket())
        {
            if (isFastMove())
            {
                power = this.pvpFastPower;
            }
            if (isChargeMove())
            {
                power = this.pvpChargeDamage;
            }
        }
        return power;
    }

    public double getEnergyDelta(BattleMode battleMode)
    {
        if (battleMode.isRaid())
        {
            if (isFastMove())
            {
                return getEnergyGain();
            }
            if (isChargeMove())
            {
                return getEnergyCost();
            }
        }
        if (battleMode.isRocket())
        {
            if (isFastMove())
            {
                return getPvpFastEnergy();
            }
            if (isChargeMove())
            {
                return getPvpChargeEnergy();
            }
        }
        return 0.0;
    }

    public double getDuration(BattleMode battleMode)
    {
        double duration = 0.0;
        if(battleMode.isRaid())
        {
            duration = getCooldown() * 1000;
        }
        if(battleMode.isRocket())
        {
            if (isFastMove())
            {
                duration = getPvpFastDuration() + 1.0;
            }
            if (isChargeMove())
            {
                duration = 0.0;
            }
        }
        return duration;
    }

    private static final String DAMAGE_WINDOW_DELIM = " ";

    public double getDamageWindow(BattleMode battleMode)
    {
        double damageWindow = 0.0;
        if(battleMode.isRaid())
        {
            String dw = this.damageWindow.split(DAMAGE_WINDOW_DELIM)[0];

            damageWindow = Double.parseDouble(dw) * 1000;
        }
        return damageWindow;
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

    public static final String PVP_FAST_POWER = "pvp_fast_power";
    public static final String PVP_FAST_ENERGY = "pvp_fast_energy";
    public static final String PVP_FAST_DURATION = "pvp_fast_duration";

    public static final String PVP_CHARGE_DAMAGE = "pvp_charge_damage";
    public static final String PVP_CHARGE_ENERGY = "pvp_charge_energy";


    public static PokemonMove createFromJSON(JSONObject object)
    {
        PokemonMove move = new PokemonMove();

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

        move.pvpFastPower = getInt(object, PVP_FAST_POWER);
        move.pvpFastEnergy = getInt(object, PVP_FAST_ENERGY);
        move.pvpFastDuration = getDoubleIfExists(object, PVP_FAST_DURATION);

        move.pvpChargeDamage = getInt(object, PVP_CHARGE_DAMAGE);
        move.pvpChargeEnergy = getInt(object, PVP_CHARGE_ENERGY);
        if (move.pvpChargeEnergy > 0)
        {
            move.pvpChargeEnergy = -move.pvpChargeEnergy;
        }

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

    private static double getDoubleIfExists(JSONObject object, String key)
    {
        String value = object.getString(key);
        if (value.isEmpty())
        {
            return 0.0;
        }
        return object.getDouble(key);
    }

    public static PokemonMove copyMove(PokemonMove oldMove, String newName, String newType)
    {
        PokemonMove move = new PokemonMove();

        move.name = newName;
        move.type = newType;

        move.category = oldMove.category;
        move.power = oldMove.power;
        move.cooldown = oldMove.cooldown;
        move.energyGain = oldMove.energyGain;
        move.energyCost = oldMove.energyCost;
        move.dodgeWindow = oldMove.dodgeWindow;
        move.damageWindow = oldMove.damageWindow;

        return move;
    }
}
