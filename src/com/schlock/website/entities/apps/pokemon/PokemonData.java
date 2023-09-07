package com.schlock.website.entities.apps.pokemon;

import com.schlock.website.entities.Persisted;
import com.schlock.website.services.apps.pokemon.impl.PokemonDataGameMasterServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class PokemonData extends Persisted
{
    private String name;
    private String nameId;
    private String number;
    private String type1;
    private String type2;

    private boolean shadow;
    private boolean mega;

    private int baseAttack;
    private int baseDefense;
    private int baseStamina;

    private boolean hasEvolution;
    private boolean ignore;
    private boolean legendary;
    private boolean raidBoss;

    private String allMoveNames;
    private String standardMoveNames;

    private Set<PokemonMove> allMoves;
    private Set<PokemonMove> standardMoves;


    private PokemonData()
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

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
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

    public void setType1(String type1)
    {
        this.type1 = type1;
    }

    public String getType2()
    {
        return type2;
    }

    public void setType2(String type2)
    {
        this.type2 = type2;
    }

    public boolean isShadow()
    {
        return shadow;
    }

    public void setShadow(boolean shadow)
    {
        this.shadow = shadow;
    }

    public boolean isMega()
    {
        return mega;
    }

    public void setMega(boolean mega)
    {
        this.mega = mega;
    }

    public int getBaseAttack()
    {
        return baseAttack;
    }

    public void setBaseAttack(int baseAttack)
    {
        this.baseAttack = baseAttack;
    }

    public int getBaseDefense()
    {
        return baseDefense;
    }

    public void setBaseDefense(int baseDefense)
    {
        this.baseDefense = baseDefense;
    }

    public int getBaseStamina()
    {
        return baseStamina;
    }

    public void setBaseStamina(int baseStamina)
    {
        this.baseStamina = baseStamina;
    }

    public boolean isHasEvolution()
    {
        return hasEvolution;
    }

    public void setHasEvolution(boolean hasEvolution)
    {
        this.hasEvolution = hasEvolution;
    }

    public boolean isIgnore()
    {
        return ignore;
    }

    public void setIgnore(boolean ignore)
    {
        this.ignore = ignore;
    }

    public boolean isLegendary()
    {
        return legendary;
    }

    public void setLegendary(boolean legendary)
    {
        this.legendary = legendary;
    }

    public boolean isRaidBoss()
    {
        return raidBoss;
    }

    public void setRaidBoss(boolean raidBoss)
    {
        this.raidBoss = raidBoss;
    }

    public String getAllMoveNames()
    {
        return allMoveNames;
    }

    public void setAllMoveNames(String allMoveNames)
    {
        this.allMoveNames = allMoveNames;
    }

    public String getStandardMoveNames()
    {
        return standardMoveNames;
    }

    public void setStandardMoveNames(String standardMoveNames)
    {
        this.standardMoveNames = standardMoveNames;
    }

    public Set<PokemonMove> getAllMoves()
    {
        return allMoves;
    }

    public void setAllMoves(Set<PokemonMove> allMoves)
    {
        this.allMoves = allMoves;
    }

    public Set<PokemonMove> getStandardMoves()
    {
        return standardMoves;
    }

    public void setStandardMoves(Set<PokemonMove> standardMoves)
    {
        this.standardMoves = standardMoves;
    }


    public Set<PokemonMove> getAllChargeMoves()
    {
        Set<PokemonMove> chargeMoves = new HashSet<PokemonMove>();
        for(PokemonMove move : allMoves)
        {
            if (move.isChargeMove() && !move.isIgnore())
            {
                chargeMoves.add(move);
            }
        }
        return chargeMoves;
    }

    public Set<PokemonMove> getAllFastMoves()
    {
        Set<PokemonMove> fastMoves = new HashSet<PokemonMove>();
        for(PokemonMove move : allMoves)
        {
            if (move.isFastMove() && !move.isIgnore())
            {
                fastMoves.add(move);
            }
        }
        return fastMoves;
    }

    public Set<PokemonMove> getStandardChargeMoves()
    {
        Set<PokemonMove> chargeMoves = new HashSet<PokemonMove>();
        for(PokemonMove move : standardMoves)
        {
            if (move.isChargeMove() && !move.isIgnore())
            {
                chargeMoves.add(move);
            }
        }
        return chargeMoves;
    }

    public Set<PokemonMove> getStandardFastMoves()
    {
        Set<PokemonMove> fastMoves = new HashSet<PokemonMove>();
        for(PokemonMove move : standardMoves)
        {
            if (move.isFastMove() && !move.isIgnore())
            {
                fastMoves.add(move);
            }
        }
        return fastMoves;
    }


    private static final String TITLE = "title_1";
    private static final String NUMBER = "number";
    private static final String TYPE = "field_pokemon_type";

    private static final String SHADOW = "field_shadow_pokemon_";
    private static final String MEGA = "is_mega";

    private static final String ATTACK = "atk";
    private static final String DEFENSE = "def";
    private static final String STAMINA = "sta";

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

    private static final String FIELD_EVOLUTION = "field_evolutions";

    public static final String MOVE_DELIM = ",";

    public static PokemonData createFromGamepressJSON(JSONObject object)
    {
        PokemonData pokemon = new PokemonData();

        pokemon.name = object.getString(TITLE);
        pokemon.number = getConvertedPokemonNumber(pokemon.name, object);

        String[] types = object.getString(TYPE).split(MOVE_DELIM);
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

        pokemon.allMoveNames = getSetStrings(object,
                                                FAST_MOVES, FAST_ELITE_MOVES, FAST_PURIFY_MOVES, FAST_LEGACY_MOVES, FAST_EX_MOVES,
                                                CHARGE_MOVES, CHARGE_ELITE_MOVES, CHARGE_PURIFY_MOVES, CHARGE_LEGACY_MOVES, CHARGE_EX_MOVES);

        pokemon.standardMoveNames = getSetStrings(object, FAST_MOVES, CHARGE_MOVES, CHARGE_PURIFY_MOVES);

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

    private static String getSetStrings(JSONObject object, String... fieldKeys)
    {
        String strings = "";

        for (String key : fieldKeys)
        {
            String values = object.getString(key);
            if (StringUtils.isNotEmpty(values))
            {
                if (!strings.isEmpty())
                {
                    strings += MOVE_DELIM + values;
                }
                else
                {
                    strings = values;
                }
            }
        }
        return strings.replaceAll(", ", ",");
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

        newData.allMoveNames = oldData.allMoveNames;
        newData.allMoves = oldData.allMoves;

        return newData;
    }


    private static final String GM_TEMPLATE_ID = PokemonDataGameMasterServiceImpl.GM_TEMPLATE_ID;

    public static final String GM_POKEMON_TAG = "_POKEMON_";


    private static String getPokemonNameId(JSONObject json)
    {
        String templateId = json.getString(GM_TEMPLATE_ID);

        int index = templateId.indexOf(GM_POKEMON_TAG) + GM_POKEMON_TAG.length();

        return templateId.substring(index);
    }


    private static final String DATA_TAG = "data";
    private static final String SETTINGS_TAG = "pokemonSettings";

    private static final String TYPE_1_TAG = "type";
    private static final String TYPE_2_TAG = "type2";

    private static final String STATS_TAG = "stats";
    private static final String STAMINA_TAG = "baseStamina";
    private static final String ATTACK_TAG = "baseAttack";
    private static final String DEFENSE_TAG = "baseDefense";

    private static final String EVOLUTION_TAG = "evolutionBranch";

    /**
     *     {
     *         "templateId": "V0260_POKEMON_SWAMPERT",
     *         "data": {
     *             "templateId": "V0260_POKEMON_SWAMPERT",
     *             "pokemonSettings": {
     *                 "pokemonId": "SWAMPERT",
     *                 "type": "POKEMON_TYPE_WATER",
     *                 "type2": "POKEMON_TYPE_GROUND",
     *                 "stats": {
     *                     "baseStamina": 225,
     *                     "baseAttack": 208,
     *                     "baseDefense": 175
     *                 },
     *                 "quickMoves": [
     *                     "MUD_SHOT_FAST",
     *                     "WATER_GUN_FAST"
     *                 ],
     *                 "cinematicMoves": [
     *                     "EARTHQUAKE",
     *                     "SLUDGE_WAVE",
     *                     "SURF",
     *                     "MUDDY_WATER"
     *                 ],
     *                 "evolutionBranch": [
     *                     {
     *                         "temporaryEvolution": "TEMP_EVOLUTION_MEGA",
     *                         "temporaryEvolutionEnergyCost": 200,
     *                         "temporaryEvolutionEnergyCostSubsequent": 40
     *                     }
     *                 ],
     *                 "shadow": {
     *                     "purificationStardustNeeded": 3000,
     *                     "purificationCandyNeeded": 3,
     *                     "purifiedChargeMove": "RETURN",
     *                     "shadowChargeMove": "FRUSTRATION"
     *                 },
     *                 "buddyGroupNumber": 3,
     *                 "eliteCinematicMove": [
     *                     "HYDRO_CANNON"
     *                 ],
     *                 "tempEvoOverrides": [
     *                     {
     *                         "tempEvoId": "TEMP_EVOLUTION_MEGA",
     *                         "stats": {
     *                             "baseStamina": 225,
     *                             "baseAttack": 283,
     *                             "baseDefense": 218
     *                         },
     *                         "averageHeightM": 1.9,
     *                         "averageWeightKg": 102.0,
     *                         "typeOverride1": "POKEMON_TYPE_WATER",
     *                         "typeOverride2": "POKEMON_TYPE_GROUND",
     *                         "camera": {
     *                             "cylinderRadiusM": 1.15,
     *                             "cylinderHeightM": 2.0
     *                         },
     *                         "raidBossDistanceOffset": 3.0
     *                     }
     *                 ],
     *                 "buddyWalkedMegaEnergyAward": 15
     *             }
     *         }
     *     },
     */
    public static PokemonData createFromGameMasterJSON(JSONObject json)
    {
        PokemonData pokemon = new PokemonData();

        pokemon.nameId = getPokemonNameId(json);

        JSONObject data = json.getJSONObject(DATA_TAG);
        JSONObject settings = data.getJSONObject(SETTINGS_TAG);

        pokemon.type1 = getType(settings, TYPE_1_TAG);
        pokemon.type2 = getType(settings, TYPE_2_TAG);

        JSONObject stats = settings.getJSONObject(STATS_TAG);
        pokemon.baseStamina = getInt(stats, STAMINA_TAG);
        pokemon.baseAttack = getInt(stats, ATTACK_TAG);
        pokemon.baseDefense = getInt(stats, DEFENSE_TAG);



        pokemon.hasEvolution = settings.has(EVOLUTION_TAG);

        pokemon.shadow = false;
        pokemon.mega = false;

        pokemon.ignore = false;
        pokemon.legendary = false;
        pokemon.raidBoss = false;

        return pokemon;
    }

    /**
     * private boolean shadow;
     * private boolean mega;
     * <p>
     * private String allMoveNames;
     * private String standardMoveNames;
     * <p>
     * private Set<PokemonMove> allMoves;
     * private Set<PokemonMove> standardMoves;
     */

    private static String getType(JSONObject settingsJson, String tag)
    {
        if(settingsJson.has(tag))
        {
            String type = settingsJson.getString(tag);
            return PokemonType.getTextByGamemasterTag(type);
        }
        return null;
    }

    private static int getInt(JSONObject json, String tag)
    {
        if (json.has(tag))
        {
            return json.getInt(tag);
        }
        return 0;
    }
}
