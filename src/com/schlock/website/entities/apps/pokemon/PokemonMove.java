package com.schlock.website.entities.apps.pokemon;

import com.schlock.website.entities.Persisted;
import com.schlock.website.services.apps.pokemon.impl.PokemonDataGameMasterServiceImpl;
import org.apache.tapestry5.json.JSONObject;

import java.text.DecimalFormat;

public class PokemonMove extends Persisted
{
    private static final String FAST_MOVE = "Fast Move";
    private static final String CHARGE_MOVE = "Charge Move";

    private String name;
    private String nameId;
    private int uuid;
    private String type;
    private String category;

    private boolean ignore;

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

    public void setName(String name)
    {
        this.name = name;
    }

    public String getNameId()
    {
        return nameId;
    }

    public void setNameId(String nameId)
    {
        this.nameId = nameId;
    }

    public int getUuid()
    {
        return uuid;
    }

    public void setUuid(int uuid)
    {
        this.uuid = uuid;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public boolean isIgnore()
    {
        return ignore;
    }

    public void setIgnore(boolean ignore)
    {
        this.ignore = ignore;
    }

    public int getPower()
    {
        return power;
    }

    public void setPower(int power)
    {
        this.power = power;
    }

    public double getCooldown()
    {
        return cooldown;
    }

    public void setCooldown(double cooldown)
    {
        this.cooldown = cooldown;
    }

    public int getEnergyGain()
    {
        return energyGain;
    }

    public void setEnergyGain(int energyGain)
    {
        this.energyGain = energyGain;
    }

    public int getEnergyCost()
    {
        return energyCost;
    }

    public void setEnergyCost(int energyCost)
    {
        this.energyCost = energyCost;
    }

    public String getDodgeWindow()
    {
        return dodgeWindow;
    }

    public void setDodgeWindow(String dodgeWindow)
    {
        this.dodgeWindow = dodgeWindow;
    }

    public String getDamageWindow()
    {
        return damageWindow;
    }

    public void setDamageWindow(String damageWindow)
    {
        this.damageWindow = damageWindow;
    }

    public int getPvpFastPower()
    {
        return pvpFastPower;
    }

    public void setPvpFastPower(int pvpFastPower)
    {
        this.pvpFastPower = pvpFastPower;
    }

    public int getPvpFastEnergy()
    {
        return pvpFastEnergy;
    }

    public void setPvpFastEnergy(int pvpFastEnergy)
    {
        this.pvpFastEnergy = pvpFastEnergy;
    }

    public double getPvpFastDuration()
    {
        return pvpFastDuration;
    }

    public void setPvpFastDuration(double pvpFastDuration)
    {
        this.pvpFastDuration = pvpFastDuration;
    }

    public int getPvpChargeDamage()
    {
        return pvpChargeDamage;
    }

    public void setPvpChargeDamage(int pvpChargeDamage)
    {
        this.pvpChargeDamage = pvpChargeDamage;
    }

    public int getPvpChargeEnergy()
    {
        return pvpChargeEnergy;
    }

    public void setPvpChargeEnergy(int pvpChargeEnergy)
    {
        this.pvpChargeEnergy = pvpChargeEnergy;
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



    private static final String NEGATIVE = "-";

    private static int getInt(JSONObject object, String key)
    {
        if (!object.has(key))
        {
            return 0;
        }
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
        if (!object.has(key))
        {
            return 0.0;
        }
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



    public void updateFromGameMasterStandardJSON(PokemonMove updates)
    {
        this.power = updates.power;
        this.cooldown = updates.cooldown;

        this.energyGain = updates.energyGain;
        this.energyCost = updates.energyCost;

        this.dodgeWindow = updates.dodgeWindow;
        this.damageWindow = updates.damageWindow;
    }

    public void updateFromGameMasterCombatJSON(PokemonMove updates)
    {
        this.pvpChargeEnergy = updates.pvpChargeEnergy;
        this.pvpChargeDamage = updates.pvpChargeDamage;

        this.pvpFastEnergy = updates.pvpFastEnergy;
        this.pvpFastPower = updates.pvpFastPower;
        this.pvpFastDuration = updates.pvpFastDuration;
    }


    private static final String GM_TEMPLATE_ID = PokemonDataGameMasterServiceImpl.GM_TEMPLATE_ID;

    public static final String GM_V_PRE = "V";
    public static final String GM_MOVE_TAG = "_MOVE_";


    private static String getMoveNameId(JSONObject json)
    {
        String templateId = json.getString(GM_TEMPLATE_ID);

        int index = templateId.indexOf(GM_MOVE_TAG) + GM_MOVE_TAG.length();

        return templateId.substring(index);
    }

    private static int getMoveUuid(JSONObject json)
    {
        String templateId = json.getString(GM_TEMPLATE_ID);

        // V0016_MOVE_DARK_PULSE
        // COMBAT_V0016_MOVE_DARK_PULSE

        int start = templateId.indexOf(GM_V_PRE) +1;
        int end = templateId.indexOf(GM_MOVE_TAG);

        String uuid = templateId.substring(start, end);

        return Integer.parseInt(uuid);
    }


    private static final String DATA_TAG = "data";
    private static final String SETTINGS_TAG = "moveSettings";
    private static final String COMBAT_SETTINGS_TAG = "combatMove";

    private static final String POKEMON_TYPE_TAG = "pokemonType";
    private static final String TYPE_TAG = "type";

    private static final String ENERGY_DELTA_TAG = "energyDelta";
    private static final String POWER_TAG = "power";

    private static final String DAMAGE_WINDOW_START_TAG = "damageWindowStartMs";
    private static final String DAMAGE_WINDOW_END_TAG = "damageWindowEndMs";
    private static final String DURATION_TAG = "durationMs";

    private static final String DURATION_TURNS_TAG = "durationTurns";

    /**
     *     {
     *         "templateId": "V0016_MOVE_DARK_PULSE",
     *         "data": {
     *             "templateId": "V0016_MOVE_DARK_PULSE",
     *             "moveSettings": {
     *                 "movementId": "DARK_PULSE",
     *                 "animationId": 5,
     *                 "pokemonType": "POKEMON_TYPE_DARK",
     *                 "power": 80.0,
     *                 "accuracyChance": 1.0,
     *                 "criticalChance": 0.05,
     *                 "staminaLossScalar": 0.08,
     *                 "trainerLevelMin": 1,
     *                 "trainerLevelMax": 100,
     *                 "vfxName": "dark_pulse",
     *                 "durationMs": 3000,
     *                 "damageWindowStartMs": 1400,
     *                 "damageWindowEndMs": 2300,
     *                 "energyDelta": -50
     *             }
     *         }
     *     },
     *
     *
     *     {
     *         "templateId": "V0264_MOVE_HEX_FAST",
     *         "data": {
     *             "templateId": "V0264_MOVE_HEX_FAST",
     *             "moveSettings": {
     *                 "movementId": "HEX_FAST",
     *                 "animationId": 4,
     *                 "pokemonType": "POKEMON_TYPE_GHOST",
     *                 "power": 10.0,
     *                 "accuracyChance": 1.0,
     *                 "staminaLossScalar": 0.01,
     *                 "trainerLevelMin": 1,
     *                 "trainerLevelMax": 100,
     *                 "vfxName": "hex_fast",
     *                 "durationMs": 1200,
     *                 "damageWindowStartMs": 1000,
     *                 "damageWindowEndMs": 1200,
     *                 "energyDelta": 16
     *             }
     *         }
     *     },
     */
    public static PokemonMove createFromGameMasterStandardJSON(JSONObject json)
    {
        PokemonMove move = new PokemonMove();

        move.nameId = getMoveNameId(json);
        move.uuid = getMoveUuid(json);

        JSONObject data = json.getJSONObject(DATA_TAG);
        JSONObject settings = data.getJSONObject(SETTINGS_TAG);

        move.type = getType(settings, POKEMON_TYPE_TAG);

        int energyDelta = getInt(settings, ENERGY_DELTA_TAG);
        move.power = (int) getDoubleIfExists(settings, POWER_TAG);

        double damageWindowStart = getDoubleIfExists(settings, DAMAGE_WINDOW_START_TAG);
        double damageWindowEnd = getDoubleIfExists(settings, DAMAGE_WINDOW_END_TAG);
        double duration = getDoubleIfExists(settings, DURATION_TAG);

        double damageWindow = damageWindowStart / 1000;
        double dodgeWindow = (damageWindowEnd - damageWindowStart) / 1000;
        double cooldown = duration / 1000;

        move.dodgeWindow = new DecimalFormat("0.00").format(dodgeWindow) + " seconds";
        move.damageWindow = new DecimalFormat("0.00").format(damageWindow) + " seconds";
        move.cooldown = cooldown;

        if (energyDelta >= 0)
        {
            //fast move
            move.category = FAST_MOVE;

            move.energyGain = energyDelta;
            move.energyCost = 0;
        }
        else
        {
            //charge move
            move.category = CHARGE_MOVE;

            move.energyCost = energyDelta;
            move.energyGain = 0;
        }
        return move;
    }

    /**
     *     {
     *         "templateId": "COMBAT_V0016_MOVE_DARK_PULSE",
     *         "data": {
     *             "templateId": "COMBAT_V0016_MOVE_DARK_PULSE",
     *             "combatMove": {
     *                 "uniqueId": "DARK_PULSE",
     *                 "type": "POKEMON_TYPE_DARK",
     *                 "power": 80.0,
     *                 "vfxName": "dark_pulse",
     *                 "energyDelta": -50
     *             }
     *         }
     *     },
     *
     *     {
     *         "templateId": "COMBAT_V0264_MOVE_HEX_FAST",
     *         "data": {
     *             "templateId": "COMBAT_V0264_MOVE_HEX_FAST",
     *             "combatMove": {
     *                 "uniqueId": "HEX_FAST",
     *                 "type": "POKEMON_TYPE_GHOST",
     *                 "power": 6.0,
     *                 "vfxName": "hex_fast",
     *                 "durationTurns": 2,
     *                 "energyDelta": 12
     *             }
     *         }
     *     },
     */
    public static PokemonMove createFormGameMasterCombatJSON(JSONObject json)
    {
        PokemonMove move = new PokemonMove();

        move.nameId = getMoveNameId(json);
        move.uuid = getMoveUuid(json);

        JSONObject data = json.getJSONObject(DATA_TAG);
        JSONObject settings = data.getJSONObject(COMBAT_SETTINGS_TAG);

        move.type = getType(settings, TYPE_TAG);

        int energyDelta = getInt(settings, ENERGY_DELTA_TAG);
        double power = getDoubleIfExists(settings, POWER_TAG);
        if (energyDelta >= 0)
        {
            //fast move
            move.category = FAST_MOVE;

            move.pvpFastEnergy = energyDelta;
            move.pvpFastPower = (int) power;
            move.pvpFastDuration = getDoubleIfExists(settings, DURATION_TURNS_TAG);

            move.pvpChargeEnergy = 0;
            move.pvpChargeDamage = 0;
        }
        else
        {
            //charge move
            move.category = CHARGE_MOVE;

            move.pvpChargeEnergy = energyDelta;
            move.pvpChargeDamage = (int) power;

            move.pvpFastEnergy = 0;
            move.pvpFastPower = 0;
            move.pvpFastDuration = 0.0;
        }
        return move;
    }


    private static String getType(JSONObject settingsJson, String tag)
    {
        if(settingsJson.has(tag))
        {
            String type = settingsJson.getString(tag);
            return PokemonType.getTextByGamemasterTag(type);
        }
        return null;
    }
}
