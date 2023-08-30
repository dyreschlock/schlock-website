package com.schlock.website.entities.apps.pokemon;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class PokemonData
{
    private String name;
    private String number;
    private String type1;
    private String type2;

    private boolean shadow;
    private boolean mega;

    private int baseAttack;
    private int baseDefense;
    private int baseStamina;

    private boolean hasEvolution;

    private Set<String> allFastMoveNames;
    private Set<String> allChargeMoveNames;

    private Set<PokemonMove> allFastMoves;
    private Set<PokemonMove> allChargeMoves;

    private Set<String> standardFastMoveNames;
    private Set<String> standardChargeMoveNames;

    private Set<PokemonMove> standardFastMoves;
    private Set<PokemonMove> standardChargeMoves;


    private PokemonData()
    {
    }

    public String getName()
    {
        return name;
    }

    public String getNumber()
    {
        return number;
    }

    private static final String IMAGE_LINK = "https://raw.githubusercontent.com/dyreschlock/dyreschlock.github.io/main/img/pokemon/%s.png";

    public String getImageLink()
    {
        return String.format(IMAGE_LINK, number);
    }

    public String getMainType(PokemonMove fastMove, PokemonMove chargeMove)
    {
        String type1 = getType1().toLowerCase();
        String type2 = getType2();
        if (type2 != null)
        {
            type2 = type2.toLowerCase();
        }

        String fastType = fastMove.getType().toLowerCase();
        String chargeType = chargeMove.getType().toLowerCase();

        if (StringUtils.equals(fastType, chargeType))
        {
            if (StringUtils.equals(type1, fastType))
            {
                return type1;
            }
            if (StringUtils.equals(type2, fastType))
            {
                return type2;
            }
        }
//        return chargeType;

        if (StringUtils.equals(type2, chargeType) || StringUtils.equals(type2, fastType))
        {
            return type2;
        }

        return type1;
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

    public Set<PokemonMove> getAllChargeMoves()
    {
        return allChargeMoves;
    }

    public Set<PokemonMove> getAllFastMoves()
    {
        return allFastMoves;
    }

    public void setAllFastMoves(Set<PokemonMove> allFastMoves)
    {
        this.allFastMoves = allFastMoves;
    }

    public void setAllChargeMoves(Set<PokemonMove> allChargeMoves)
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

    public Set<PokemonMove> getStandardFastMoves()
    {
        return standardFastMoves;
    }

    public Set<PokemonMove> getStandardChargeMoves()
    {
        return standardChargeMoves;
    }

    public void setStandardFastMoves(Set<PokemonMove> standardFastMoves)
    {
        this.standardFastMoves = standardFastMoves;
    }

    public void setStandardChargeMoves(Set<PokemonMove> standardChargeMoves)
    {
        this.standardChargeMoves = standardChargeMoves;
    }


    private static final String TITLE = "title_1";
    private static final String NUMBER = "number";
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

    public static PokemonData createFromJSON(JSONObject object)
    {
        PokemonData pokemon = new PokemonData();

        pokemon.name = object.getString(TITLE);
        pokemon.number = getConvertedPokemonNumber(pokemon.name, object);

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

        pokemon.allFastMoveNames = getSetStrings(object, FAST_MOVES, FAST_ELITE_MOVES, FAST_PURIFY_MOVES, FAST_LEGACY_MOVES, FAST_EX_MOVES);
        pokemon.allChargeMoveNames = getSetStrings(object, CHARGE_MOVES, CHARGE_ELITE_MOVES, CHARGE_PURIFY_MOVES, CHARGE_LEGACY_MOVES, CHARGE_EX_MOVES);

        pokemon.standardFastMoveNames = getSetStrings(object, FAST_MOVES);
        pokemon.standardChargeMoveNames = getSetStrings(object, CHARGE_MOVES, CHARGE_PURIFY_MOVES);

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


    private static final String MEGA_NUMBER_PREFIX = "m_";
    private static final String MEGA_X_NUMBER_PREMIX = "mx_";
    private static final String ALOLA_NUMBER_PREFIX = "a_";
    private static final String GALAR_NUMBER_PREFIX = "g_";
    private static final String HISUI_NUMBER_PREFIX = "h_";
    private static final String PALDEA_NUMBER_PREFIX = "p_";

    private static final String MEGA_NAME_PREFIX = "Mega ";
    private static final String PRIMAL_NAME_PREFIX = "Primal ";
    private static final String MEGA_X_NAME_SUFFIX = " X";
    private static final String ALOLA_NAME_PREFIX = "Alolan ";
    private static final String GALAR_NAME_PREFIX = "Galarian ";
    private static final String HISUI_NAME_PREFIX = "Hisuian ";
    private static final String PALDEA_NAME_PREFIX = "Paldean ";


    private static String getConvertedPokemonNumber(String name, JSONObject object)
    {
        String number = object.getString(NUMBER);

        try
        {
            number = sanitizeNumberString(number);
            number = addLeadingZeroes(number);
            number = addVariantPrefixes(name, number);
        } catch (Exception e)
        {
            String message = "Pokemon (%s) has problems with its number";
            message = String.format(message, name);

            throw new RuntimeException(message, e);
        }

        return number;
    }

    private static String sanitizeNumberString(String original) throws Exception
    {
        String number = original;

        while (!isNumber(number))
        {
            number = number.substring(0, number.length() - 1);
        }
        return number;
    }

    private static boolean isNumber(String str)
    {
        try
        {
            int test = Integer.parseInt(str);
        } catch (Exception e)
        {
            return false;
        }
        return true;
    }

    private static String addLeadingZeroes(String number)
    {
        if (number.length() == 1)
        {
            return "00" + number;
        }
        if (number.length() == 2)
        {
            return "0" + number;
        }
        return number;
    }

    private static String addVariantPrefixes(String name, String original)
    {
        String number = original;

        if (name.startsWith(MEGA_NAME_PREFIX) || name.startsWith(PRIMAL_NAME_PREFIX))
        {
            if (name.endsWith(MEGA_X_NAME_SUFFIX))
            {
                number = MEGA_X_NUMBER_PREMIX + number;
            } else
            {
                number = MEGA_NUMBER_PREFIX + number;
            }
        } else if (name.startsWith(ALOLA_NAME_PREFIX))
        {
            number = ALOLA_NUMBER_PREFIX + number;
        } else if (name.startsWith(GALAR_NAME_PREFIX))
        {
            number = GALAR_NUMBER_PREFIX + number;
        } else if (name.startsWith(HISUI_NAME_PREFIX))
        {
            number = HISUI_NUMBER_PREFIX + number;
        } else if (name.startsWith(PALDEA_NAME_PREFIX))
        {
            number = PALDEA_NUMBER_PREFIX + number;
        }
        return number;
    }


    private static final String SHADOW_PRETEXT = "Shadow ";

    public static PokemonData createShadowPokemonFromData(PokemonData oldData)
    {
        PokemonData newData = new PokemonData();

        newData.name = SHADOW_PRETEXT + oldData.name;
        newData.type1 = oldData.type1;
        newData.type2 = oldData.type2;

        newData.mega = false;
        newData.shadow = true;

        newData.baseAttack = oldData.baseAttack;
        newData.baseDefense = oldData.baseDefense;
        newData.baseStamina = oldData.baseStamina;

        newData.allChargeMoveNames = oldData.allChargeMoveNames;
        newData.allFastMoveNames = oldData.allFastMoveNames;

        newData.allChargeMoves = oldData.allChargeMoves;
        newData.allFastMoves = oldData.allFastMoves;

        newData.standardChargeMoveNames = oldData.standardChargeMoveNames;
        newData.standardFastMoveNames = oldData.standardFastMoveNames;

        newData.standardChargeMoves = oldData.standardChargeMoves;
        newData.standardFastMoves = oldData.standardFastMoves;

        return newData;
    }
}
