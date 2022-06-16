package com.schlock.website.entities.apps.pokemon;

import org.apache.tapestry5.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class RaidPokemonData
{
    private String name;
    private String type;

    private boolean shadow;
    private boolean mega;

    private int attack;
    private int defense;
    private int stamina;
    private int cp;

    private Set<String> fastMoveNames;
    private Set<String> chargeMoveNames;

    private int lvl20;
    private int lvl30;
    private int lvl35;
    private int lvl40;

    private RaidPokemonData()
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

    public boolean isShadow()
    {
        return shadow;
    }

    public boolean isMega()
    {
        return mega;
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

    public Set<String> getFastMoveNames()
    {
        return fastMoveNames;
    }

    public Set<String> getChargeMoveNames()
    {
        return chargeMoveNames;
    }

    public int getLvl20()
    {
        return lvl20;
    }

    public int getLvl30()
    {
        return lvl30;
    }

    public int getLvl35()
    {
        return lvl35;
    }

    public int getLvl40()
    {
        return lvl40;
    }

    private static final String TITLE = "title_1";
    private static final String TYPE = "field_pokemon_type";

    private static final String SHADOW = "field_shadow_pokemon_";
    private static final String MEGA = "is_mega";

    private static final String ATTACK = "atk";
    private static final String DEFENSE = "def";
    private static final String STAMINA = "sta";
    private static final String CP = "cp";

    private static final String FAST_MOVES = "field_primary_moves";
    private static final String FAST_ELITE_MOVES = "elite_fast_moves";
    private static final String FAST_PURIFY_MOVES = "purified_fast_moves";
    private static final String FAST_LEGACY_MOVES = "field_legacy_quick_moves";
    private static final String FAST_EX_MOVES = "quick_exclusive_moves";

    private static final String CHARGE_MOVES = "field_secondary_moves";
    private static final String CHARGE_ELITE_MOVES = "elite_charge_moves";
    private static final String CHARGE_PURIFY_MOVES = "purified_charge_moves";
    private static final String CHARGE_LEGACY_MOVES = "field_legacy_charge_moves";
    private static final String CHARGE_EX_MOVES = "charge_exclusive_moves";

    private static final String LEVEL_20 = "lvl20";
    private static final String LEVEL_30 = "lvl30";
    private static final String LEVEL_35 = "lvl35";
    private static final String LEVEL_40 = "lvl40";

    public static RaidPokemonData createFromJSON(JSONObject object)
    {
        RaidPokemonData pokemon = new RaidPokemonData();

        pokemon.name = object.getString(TITLE);
        pokemon.type = object.getString(TYPE);

        pokemon.shadow = isOn(object, SHADOW);
        pokemon.mega = isOn(object, MEGA);

        pokemon.attack = object.getInt(ATTACK);
        pokemon.defense = object.getInt(DEFENSE);
        pokemon.stamina = object.getInt(STAMINA);
        pokemon.cp = object.getInt(CP);

        pokemon.fastMoveNames = getSetStrings(object, FAST_MOVES, FAST_ELITE_MOVES, FAST_PURIFY_MOVES, FAST_LEGACY_MOVES, FAST_EX_MOVES);
        pokemon.chargeMoveNames = getSetStrings(object, CHARGE_MOVES, CHARGE_ELITE_MOVES, CHARGE_PURIFY_MOVES, CHARGE_LEGACY_MOVES, CHARGE_EX_MOVES);

        pokemon.lvl20 = object.getInt(LEVEL_20);
        pokemon.lvl30 = object.getInt(LEVEL_30);
        pokemon.lvl35 = object.getInt(LEVEL_35);
        pokemon.lvl40 = object.getInt(LEVEL_40);

        return pokemon;
    }


    private static final String ON = "On";
    private static final String OFF = "Off";

    private static boolean isOn(JSONObject object, String fieldKey)
    {
        String value = object.getString(fieldKey);

        return ON.equalsIgnoreCase(value);
    }

    private static final String DELIM = ",";

    private static Set<String> getSetStrings(JSONObject object, String... fieldKeys)
    {
        Set<String> set = new HashSet<String>();

        for (String key : fieldKeys)
        {
            String values = object.getString(key);
            for (String val : values.split(DELIM))
            {
                if (!val.isEmpty())
                {
                    set.add(val);
                }
            }
        }
        return set;
    }
}
