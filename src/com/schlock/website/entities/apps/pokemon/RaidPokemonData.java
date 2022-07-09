package com.schlock.website.entities.apps.pokemon;

import org.apache.tapestry5.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class RaidPokemonData
{
    private String name;
    private String type1;
    private String type2;

    private boolean shadow;
    private boolean mega;

    private int baseAttack;
    private int baseDefense;
    private int baseStamina;

    private int cp;

    private boolean hasEvolution;

    private Set<String> allFastMoveNames;
    private Set<String> allChargeMoveNames;

    private Set<RaidMove> allFastMoves;
    private Set<RaidMove> allChargeMoves;

    private Set<String> standardFastMoveNames;
    private Set<String> standardChargeMoveNames;

    private Set<RaidMove> standardFastMoves;
    private Set<RaidMove> standardChargeMoves;

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

    public String getType1()
    {
        return type1;
    }

    public String getType2()
    {
        return type2;
    }

    public boolean isShadow()
    {
        return shadow;
    }

    public boolean isMega()
    {
        return mega;
    }

    public int getBaseAttack()
    {
        return baseAttack;
    }

    public int getBaseDefense()
    {
        return baseDefense;
    }

    public int getBaseStamina()
    {
        return baseStamina;
    }


    public int getCp()
    {
        return cp;
    }

    public boolean isHasEvolution()
    {
        return hasEvolution;
    }

    public Set<String> getAllFastMoveNames()
    {
        return allFastMoveNames;
    }

    public Set<String> getAllChargeMoveNames()
    {
        return allChargeMoveNames;
    }

    public Set<RaidMove> getAllChargeMoves()
    {
        return allChargeMoves;
    }

    public Set<RaidMove> getAllFastMoves()
    {
        return allFastMoves;
    }

    public void setAllFastMoves(Set<RaidMove> allFastMoves)
    {
        this.allFastMoves = allFastMoves;
    }

    public void setAllChargeMoves(Set<RaidMove> allChargeMoves)
    {
        this.allChargeMoves = allChargeMoves;
    }


    public Set<String> getStandardFastMoveNames()
    {
        return standardFastMoveNames;
    }

    public Set<String> getStandardChargeMoveNames()
    {
        return standardChargeMoveNames;
    }

    public Set<RaidMove> getStandardFastMoves()
    {
        return standardFastMoves;
    }

    public Set<RaidMove> getStandardChargeMoves()
    {
        return standardChargeMoves;
    }

    public void setStandardFastMoves(Set<RaidMove> standardFastMoves)
    {
        this.standardFastMoves = standardFastMoves;
    }

    public void setStandardChargeMoves(Set<RaidMove> standardChargeMoves)
    {
        this.standardChargeMoves = standardChargeMoves;
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

    private static final String FIELD_EVOLUTION = "field_evolutions";

    private static final String DELIM = ",";

    public static RaidPokemonData createFromJSON(JSONObject object)
    {
        RaidPokemonData pokemon = new RaidPokemonData();

        pokemon.name = object.getString(TITLE);

        String[] types = object.getString(TYPE).split(DELIM);
        pokemon.type1 = types[0].trim();
        if (types.length == 2)
        {
            pokemon.type2 = types[1].trim();
        }

        pokemon.shadow = isOn(object, SHADOW);
        pokemon.mega = isOn(object, MEGA);

        pokemon.baseAttack = object.getInt(ATTACK);
        pokemon.baseDefense = object.getInt(DEFENSE);
        pokemon.baseStamina = object.getInt(STAMINA);
        pokemon.cp = object.getInt(CP);

        pokemon.allFastMoveNames = getSetStrings(object, FAST_MOVES, FAST_ELITE_MOVES, FAST_PURIFY_MOVES, FAST_LEGACY_MOVES, FAST_EX_MOVES);
        pokemon.allChargeMoveNames = getSetStrings(object, CHARGE_MOVES, CHARGE_ELITE_MOVES, CHARGE_PURIFY_MOVES, CHARGE_LEGACY_MOVES, CHARGE_EX_MOVES);

        pokemon.standardFastMoveNames = getSetStrings(object, FAST_MOVES);
        pokemon.standardChargeMoveNames = getSetStrings(object, CHARGE_MOVES, CHARGE_PURIFY_MOVES);

        pokemon.lvl20 = object.getInt(LEVEL_20);
        pokemon.lvl30 = object.getInt(LEVEL_30);
        pokemon.lvl35 = object.getInt(LEVEL_35);
        pokemon.lvl40 = object.getInt(LEVEL_40);

        pokemon.hasEvolution = isHasEvolution(object);

        return pokemon;
    }


    private static final String ON = "On";
    private static final String OFF = "Off";

    private static boolean isOn(JSONObject object, String fieldKey)
    {
        String value = object.getString(fieldKey);

        return ON.equalsIgnoreCase(value);
    }

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
                    set.add(val.trim());
                }
            }
        }
        return set;
    }

    private static boolean isHasEvolution(JSONObject object)
    {
        String evoReqs = object.getString(FIELD_EVOLUTION);

        return evoReqs != null && !evoReqs.isEmpty();
    }

    private static final String SHADOW_PRETEXT = "Shadow ";

    public static RaidPokemonData createShadowPokemonFromData(RaidPokemonData oldData)
    {
        RaidPokemonData newData = new RaidPokemonData();

        newData.name = SHADOW_PRETEXT + oldData.name;
        newData.type1 = oldData.type1;
        newData.type2 = oldData.type2;

        newData.mega = false;
        newData.shadow = true;

        newData.baseAttack = oldData.baseAttack;
        newData.baseDefense = oldData.baseDefense;
        newData.baseStamina = oldData.baseStamina;
        newData.cp = oldData.cp;

        newData.allChargeMoveNames = oldData.allChargeMoveNames;
        newData.allFastMoveNames = oldData.allFastMoveNames;

        newData.allChargeMoves = oldData.allChargeMoves;
        newData.allFastMoves = oldData.allFastMoves;

        newData.standardChargeMoveNames = oldData.standardChargeMoveNames;
        newData.standardFastMoveNames = oldData.standardFastMoveNames;

        newData.standardChargeMoves = oldData.standardChargeMoves;
        newData.standardFastMoves = oldData.standardFastMoves;

        newData.lvl20 = oldData.lvl20;
        newData.lvl30 = oldData.lvl30;
        newData.lvl35 = oldData.lvl35;
        newData.lvl40 = oldData.lvl40;

        return newData;
    }
}
