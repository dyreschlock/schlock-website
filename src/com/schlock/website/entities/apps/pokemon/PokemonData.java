package com.schlock.website.entities.apps.pokemon;

import com.schlock.website.entities.Persisted;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;

import java.util.HashSet;
import java.util.Iterator;
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
    private boolean dynamax;

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

    private Set<PokemonCategory> categories;

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

    private static final String IMAGE_LINK = "img/pokemon/%s.png";

    public String getImageLinkPath()
    {
        return String.format(IMAGE_LINK, number);
    }

    public int getSanitizedNumber()
    {
        String san = getNumber();
        if (StringUtils.isNumeric(san))
        {
            return Integer.parseInt(san);
        }

        int region = san.indexOf("_");
        if (region > 0)
        {
            san = san.split("_")[1];

            if (StringUtils.isNumeric(san))
            {
                return Integer.parseInt(san);
            }
        }

        san = san.substring(0, san.length() - 1);
        if (StringUtils.isNumeric(san))
        {
            return Integer.parseInt(san);
        }
        san = san.substring(0, san.length() - 1);
        return Integer.parseInt(san);
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

    public boolean isDynamax()
    {
        return dynamax;
    }

    public void setDynamax(boolean dynamax)
    {
        this.dynamax = dynamax;
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

    public Set<PokemonCategory> getCategories()
    {
        return categories;
    }

    public void setCategories(Set<PokemonCategory> categories)
    {
        this.categories = categories;
    }

    public void addCategory(PokemonCategory category)
    {
        if (!categories.contains(category))
        {
            categories.add(category);
        }
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


    private static final String DATA_TAG = "data";
    private static final String SETTINGS_TAG = "pokemonSettings";

    private static final String POKEMON_ID_TAG = "pokemonId";
    private static final String FORM_TAG = "form";

    private static final String TYPE_1_TAG = "type";
    private static final String TYPE_2_TAG = "type2";

    private static final String STATS_TAG = "stats";
    private static final String STAMINA_TAG = "baseStamina";
    private static final String ATTACK_TAG = "baseAttack";
    private static final String DEFENSE_TAG = "baseDefense";

    private static final String EVOLUTION_TAG = "evolutionBranch";

    private static final String FAST_MOVES_TAG = "quickMoves";
    private static final String CHARGE_MOVES_TAG = "cinematicMoves";

    private static final String ELITE_FAST_MOVE_TAG = "eliteQuickMove";
    private static final String ELITE_CHARGE_MOVE_TAG = "eliteCinematicMove";

    private static final String SHADOW_TAG = "shadow";
    private static final String PURIFIED_MOVE_TAG = "purifiedChargeMove";
    private static final String SHADOW_MOVE_TAG = "shadowChargeMove";

    private static final String TEMP_EVO_OVERRIDES_TAG = "tempEvoOverrides";
    private static final String TEMP_EVO_ID = "tempEvoId";

    private static final String TYPE_1_OVERRIDE_TAG = "typeOverride1";
    private static final String TYPE_2_OVERRIDE_TAG = "typeOverride2";

    private static final String MEGA_X_ID_SUFFIX = "_MEGA_X";
    private static final String MEGA_Y_ID_SUFFIX = "_MEGA_Y";
    private static final String MEGA_ID_SUFFIX = "_MEGA";
    private static final String PRIMAL_ID_SUFFIX = "_PRIMAL";
    private static final String SHADOW_SUFFIX = "_S";

    private static final String RETURN_MOVE_ID = "RETURN";
    private static final String FRUSTRATION_MOVE_ID = "FRUSTRATION";

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

        JSONObject data = json.getJSONObject(DATA_TAG);
        JSONObject settings = data.getJSONObject(SETTINGS_TAG);

        pokemon.nameId = settings.getString(POKEMON_ID_TAG);
        if (settings.has(FORM_TAG))
        {
            String formId = settings.getString(FORM_TAG);
            if(!StringUtils.isNumeric(formId))
            {
                pokemon.nameId = formId;
            }
        }

        pokemon.type1 = getType(settings, TYPE_1_TAG);
        pokemon.type2 = getType(settings, TYPE_2_TAG);

        JSONObject stats = settings.getJSONObject(STATS_TAG);
        pokemon.baseStamina = getInt(stats, STAMINA_TAG);
        pokemon.baseAttack = getInt(stats, ATTACK_TAG);
        pokemon.baseDefense = getInt(stats, DEFENSE_TAG);

        pokemon.shadow = StringUtils.endsWith(pokemon.nameId, SHADOW_SUFFIX);
        pokemon.mega = false;

        StringBuilder moveNames = new StringBuilder();

        appendStringsFromArray(moveNames, settings, FAST_MOVES_TAG);
        appendStringsFromArray(moveNames, settings, CHARGE_MOVES_TAG);

        pokemon.standardMoveNames = moveNames.toString();

        appendStringsFromArray(moveNames, settings, ELITE_FAST_MOVE_TAG);
        appendStringsFromArray(moveNames, settings, ELITE_CHARGE_MOVE_TAG);
        appendShadowPurifiedMoves(moveNames, settings, pokemon.shadow);

        pokemon.allMoveNames = moveNames.toString();


        pokemon.hasEvolution = settings.has(EVOLUTION_TAG);

        pokemon.ignore = false;
        pokemon.legendary = false;
        pokemon.raidBoss = false;

        return pokemon;
    }

    public static PokemonData createShadowFromGameMasterJSON(JSONObject json, PokemonData base)
    {
        if (base.shadow)
        {
            return null;
        }

        JSONObject data = json.getJSONObject(DATA_TAG);
        JSONObject settings = data.getJSONObject(SETTINGS_TAG);

        if (!settings.has(SHADOW_TAG))
        {
            return null;
        }

        PokemonData pokemon = new PokemonData();

        pokemon.nameId = base.nameId + SHADOW_SUFFIX;

        pokemon.type1 = base.type1;
        pokemon.type2 = base.type2;

        pokemon.baseStamina = base.baseStamina;
        pokemon.baseAttack = base.baseAttack;
        pokemon.baseDefense = base.baseDefense;

        pokemon.shadow = true;
        pokemon.mega = false;

        pokemon.standardMoveNames = base.standardMoveNames;
        pokemon.allMoveNames = base.allMoveNames.replaceAll(RETURN_MOVE_ID, FRUSTRATION_MOVE_ID);

        pokemon.hasEvolution = base.hasEvolution;

        pokemon.ignore = true;
        pokemon.legendary = false;
        pokemon.raidBoss = false;

        return pokemon;
    }

    public static Set<PokemonData> createMegaFromGameMasterJSON(JSONObject json, PokemonData base)
    {
        Set<PokemonData> megas = new HashSet<PokemonData>();

        if (base.shadow)
        {
            return megas;
        }

        JSONObject data = json.getJSONObject(DATA_TAG);
        JSONObject settings = data.getJSONObject(SETTINGS_TAG);

        if (!settings.has(TEMP_EVO_OVERRIDES_TAG))
        {
            return megas;
        }

        JSONArray evolutions = settings.getJSONArray(TEMP_EVO_OVERRIDES_TAG);
        Iterator iter = evolutions.iterator();
        while(iter.hasNext())
        {
            JSONObject evolution = (JSONObject) iter.next();
            if (evolution.has(TEMP_EVO_ID))
            {
                PokemonData pokemon = new PokemonData();

                pokemon.nameId = base.nameId;

                String evoId = evolution.getString(TEMP_EVO_ID);
                if (StringUtils.endsWith(evoId, PRIMAL_ID_SUFFIX))
                {
                    pokemon.nameId += PRIMAL_ID_SUFFIX;
                }
                else if(StringUtils.endsWith(evoId, MEGA_X_ID_SUFFIX))
                {
                    pokemon.nameId += MEGA_X_ID_SUFFIX;
                }
                else if(StringUtils.endsWith(evoId, MEGA_Y_ID_SUFFIX))
                {
                    pokemon.nameId += MEGA_Y_ID_SUFFIX;
                }
                else
                {
                    pokemon.nameId += MEGA_ID_SUFFIX;
                }

                pokemon.type1 = getType(evolution, TYPE_1_OVERRIDE_TAG);
                pokemon.type2 = getType(evolution, TYPE_2_OVERRIDE_TAG);

                JSONObject stats = evolution.getJSONObject(STATS_TAG);

                pokemon.baseStamina = stats.getInt(STAMINA_TAG);
                pokemon.baseAttack = stats.getInt(ATTACK_TAG);
                pokemon.baseDefense = stats.getInt(DEFENSE_TAG);

                pokemon.standardMoveNames = base.standardMoveNames;
                pokemon.allMoveNames = base.allMoveNames;

                pokemon.hasEvolution = base.hasEvolution;

                pokemon.shadow = false;
                pokemon.mega = true;

                pokemon.ignore = false;
                pokemon.legendary = false;
                pokemon.raidBoss = false;

                megas.add(pokemon);
            }
        }
        return megas;
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

    private static int getInt(JSONObject json, String tag)
    {
        if (json.has(tag))
        {
            return json.getInt(tag);
        }
        return 0;
    }

    private static StringBuilder appendStringsFromArray(StringBuilder sb, final JSONObject base, final String tag)
    {
        if (!base.has(tag))
        {
            return sb;
        }

        JSONArray jsonArray = base.getJSONArray(tag);

        Iterator iter = jsonArray.iterator();
        while(iter.hasNext())
        {
            Object name = iter.next();
            if (name.getClass().isAssignableFrom(String.class))
            {
                String str = (String) name;
                if (sb.length() > 0)
                {
                    sb.append(",");
                }
                sb.append(str);
            }
            if (name.getClass().isAssignableFrom(Integer.class))
            {
                Integer str = (Integer) name;
                if (sb.length() > 0)
                {
                    sb.append(",");
                }
                sb.append(Integer.toString(str));
            }
        }
        return sb;
    }

    private static StringBuilder appendShadowPurifiedMoves(StringBuilder sb, final JSONObject base, boolean isShadow)
    {
        if (base.has(SHADOW_TAG))
        {
            JSONObject shadow = base.getJSONObject(SHADOW_TAG);
            if (!isShadow && shadow.has(PURIFIED_MOVE_TAG))
            {
                String nameId = shadow.getString(PURIFIED_MOVE_TAG);

                sb.append(",");
                sb.append(nameId);
            }
            if (isShadow && shadow.has(SHADOW_MOVE_TAG))
            {
                String nameId = shadow.getString(SHADOW_MOVE_TAG);

                sb.append(",");
                sb.append(nameId);
            }
        }
        return sb;
    }
}
