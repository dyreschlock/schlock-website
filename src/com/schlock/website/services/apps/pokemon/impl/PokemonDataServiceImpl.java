package com.schlock.website.services.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.*;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.pokemon.PokemonCounterCalculationService;
import com.schlock.website.services.apps.pokemon.PokemonCustomCounterPrimeService;
import com.schlock.website.services.apps.pokemon.PokemonCustomCounterSecondService;
import com.schlock.website.services.apps.pokemon.PokemonDataService;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;

import java.io.*;
import java.util.*;

/**
 * Data Taken from : https://gamepress.gg/pokemongo/comprehensive-dps-spreadsheet
 */
public class PokemonDataServiceImpl implements PokemonDataService
{
    private static final boolean IGNORE_MOVES_POKEMON = true;
//    private static final boolean IGNORE_MOVES_POKEMON = false;

    private static final boolean USE_OVERWRITES = true;
//    private static final boolean USE_OVERWRITES = false;

    private static final String POKEMON_DIR = "pokemon/raid/";

    // https://gamepress.gg/sites/default/files/aggregatedjson/pokemon-data-full-en-PoGO.json
    private static final String POKEMON_FILE = "pokemon-data-full-en-PoGO.json";

    // https://gamepress.gg/sites/default/files/aggregatedjson/move-data-full-PoGO.json
    private static final String MOVE_FILE = "move-data-full-PoGO.json";

    // https://gamepress.gg/sites/default/files/aggregatedjson/raid-boss-list-PoGO.json

    // https://gamepress.gg/pokemongo/assets/data/cpm.json
    private static final String CPM_FILE = "cpm.json";

    private static final int LEVEL_40 = 40;
    private static final int LEVEL_45 = 45;
    private static final int LEVEL_50 = 50;

    private final static List<String> IGNORE_MOVES = Arrays.asList(
            "Hidden Power"
    );

    private final static List<String> IGNORE_POKEMON = Arrays.asList(
            "Mega Pinsir",

            "Mega Heracross",

            "Mega Glalie",
            "Mega Gardevoir",
            "Mega Gallade",
            "Mega Sableye",
            "Mega Mawile",
            "Mega Medicham",
            "Mega Shapedo",
            "Mega Camerupt",
            "Mega Audino",

            "Mega Tyranitar",
            "Mega Metagross",
            "Mega Salamence",
            "Mega Garchomp",
            "Mega Lucario",

            "Mega Mewtwo X",
            "Mega Mewtwo Y",
            "Mega Rayquaza",
            "Mega Diancie",

            "Primal Kyogre",
            "Primal Groudon",

            "White Kyurem",
            "Black Kyurem",

            "Genesect - Burn Drive",
            "Genesect - Douse Drive",
            "Genesect - Chill Drive",
            "Genesect - Shock Drive",

            "Shaymin (Sky Forme)",
            "Ash Greninja",

            "Galarian Darmanitan (Zen Mode)",

            "Zacian - Crowned Sword",
            "Zamazenta - Crowned Shield",
            "Eternamax Eternatus",
            "Urshifu (Rapid Strike Style)",
            "Urshifu (Single Strike Style)",
            "Calyrex - Shadow Rider",
            "Calyrex - Ice Rider"
    );

    private static final String SHAODW = "Shadow";
    private static final String MEGA = "Mega";

    private static final String ARCEUS = "Arceus";


    private List<RaidBossPokemon> raidBosses = new ArrayList<RaidBossPokemon>();

    private List<RocketLeader> rocketLeaders = new ArrayList<RocketLeader>();
    private Map<String, RocketBossPokemon> rocketBosses = new HashMap<String, RocketBossPokemon>();

    private List<RaidBossWithAttackingType> raidBossesWithAttackingTypes = new ArrayList<RaidBossWithAttackingType>();

    private HashMap<String, PokemonMove> moveData = new HashMap<String, PokemonMove>();
    private HashMap<String, PokemonData> pokemonData = new HashMap<String, PokemonData>();

    private HashMap<Integer, Double> cpmData = new HashMap<Integer, Double>();


    private final PokemonCustomCounterPrimeService customPrimeService;
    private final PokemonCustomCounterSecondService customSecondService;
    private final PokemonCounterCalculationService calculationService;

    private final DeploymentContext deploymentContext;

    public PokemonDataServiceImpl(PokemonCustomCounterPrimeService customPrimeService,
                                  PokemonCustomCounterSecondService customSecondService,
                                  PokemonCounterCalculationService calculationService,
                                  DeploymentContext deploymentContext)
    {
        this.customPrimeService = customPrimeService;
        this.customSecondService = customSecondService;
        this.calculationService = calculationService;

        this.deploymentContext = deploymentContext;

        loadCpmJSON();
        loadMovesJSON();

        if (USE_OVERWRITES)
        {
            addMoveOverwrites();
        }

        loadPokemonDataJSON();

        if (USE_OVERWRITES)
        {
            addPokemonOverwrites();
        }
    }

    private void addMoveOverwrites()
    {
        //move updates september 2022
//        createFairyWind();
//        createDoubleKick();
//        createShadowForce();
//        createHighHorsepower();
    }

    private void addPokemonOverwrites()
    {
        //Possible Dark Void stats
//        copyStatsCreateNewMove("Dark Void (DD)", "Dark", "Doom Desire");
//        addEliteMoveToPokemon("Darkrai", "Dark Void (DD)");
//
//        copyStatsCreateNewMove("Dark Void (Cr)", "Dark", "Crabhammer");
//        addEliteMoveToPokemon("Darkrai", "Dark Void (Cr)");
//
//        copyStatsCreateNewMove("Dark Void (MM)", "Dark", "Meteor Mash");
//        addEliteMoveToPokemon("Darkrai", "Dark Void (MM)");
//
//        copyStatsCreateNewMove("Dark Void (Psy)", "Dark", "Psystrike");
//        addEliteMoveToPokemon("Darkrai", "Dark Void (Psy)");
//
//        copyStatsCreateNewMove("Dark Void (OP)", "Dark", "Origin Pulse");
//        addEliteMoveToPokemon("Darkrai", "Dark Void (OP)");

        addEliteMoveToPokemon("Greninja", "Hydro Cannon");
        addEliteMoveToPokemon("Delphox", "Blast Burn");
        addEliteMoveToPokemon("Chesnaught", "Frenzy Plant");
//
//        addEliteMoveToPokemon("Decidueye", "Frenzy Plant");
//        addEliteMoveToPokemon("Incineroar", "Blast Burn");
//        addEliteMoveToPokemon("Primarina", "Hydro Cannon");

        //missing in json
        addEliteMoveToPokemon("Umbreon", "Psychic");
        addEliteMoveToPokemon("Rhyhorn", "Earthquake");

        addEliteMoveToPokemon("Kyogre", "Origin Pulse");

        addEliteMoveToPokemon("Rayquaza", "Breaking Swipe");
    }

    private void addStandardMoveToPokemon(String pokemonName, String... moveNames)
    {
        addMoveToPokemon(pokemonName, true, moveNames);

        String mega = MEGA_PREFIX + pokemonName;
        if (pokemonData.get(mega) != null)
        {
            addMoveToPokemon(mega, true, moveNames);
        }

        String shadow = SHADOW_PREFIX + pokemonName;
        if (pokemonData.get(shadow) != null)
        {
            addMoveToPokemon(shadow, true, moveNames);
        }
    }

    private void addEliteMoveToPokemon(String pokemonName, String... moveNames)
    {
        addMoveToPokemon(pokemonName, false, moveNames);

        String mega = MEGA_PREFIX + pokemonName;
        if (pokemonData.get(mega) != null)
        {
            addMoveToPokemon(mega, false, moveNames);
        }

        String shadow = SHADOW_PREFIX + pokemonName;
        if (pokemonData.get(shadow) != null)
        {
            addMoveToPokemon(shadow, false, moveNames);
        }
    }

    private static final String MEGA_PREFIX = "Mega ";
    private static final String SHADOW_PREFIX = "Shadow ";

    private void addMoveToPokemon(String pokemonName, boolean standard, String... moveNames)
    {
        PokemonData pokemon = pokemonData.get(pokemonName);
        if (pokemon == null)
        {
            throw new RuntimeException("Pokemon not found: " + pokemonName);
        }

        for (String moveName : moveNames)
        {
            PokemonMove move = moveData.get(moveName);
            if (move == null)
            {
                throw new RuntimeException("Move not found: " + moveName);
            }

            if (move.isFastMove())
            {
                pokemon.getAllFastMoves().add(move);
                if (standard)
                {
                    pokemon.getStandardFastMoves().add(move);
                }
            }
            if (move.isChargeMove())
            {
                pokemon.getAllChargeMoves().add(move);
                if (standard)
                {
                    pokemon.getStandardChargeMoves().add(move);
                }
            }
        }
    }

    private void copyStatsCreateNewMove(String newMoveName, String type, String oldMoveName)
    {
        PokemonMove oldMove = moveData.get(oldMoveName);
        if (oldMove == null)
        {
            throw new RuntimeException("Move not found: " + oldMoveName);
        }

        PokemonMove newMove = PokemonMove.copyMove(oldMove, newMoveName, type);
        if (moveData.get(newMove.getName()) != null)
        {
            throw new RuntimeException(newMoveName + " already exists.");
        }
        moveData.put(newMoveName, newMove);
    }

    private void createHighHorsepower()
    {
        JSONObject object = new JSONObject();
        object.put(PokemonMove.TITLE, "High Horsepower");
        object.put(PokemonMove.TYPE, "Ground");
        object.put(PokemonMove.CATEGORY, "Charge Move");
        object.put(PokemonMove.POWER, 110);
        object.put(PokemonMove.COOLDOWN, "1.6");
        object.put(PokemonMove.ENERGY_GAIN, "0");
        object.put(PokemonMove.ENERGY_COST, "-100");
        object.put(PokemonMove.DODGE_WINDOW, "1.0 seconds");
        object.put(PokemonMove.DAMAGE_WINDOW, "0.2 seconds");

        object.put(PokemonMove.PVP_FAST_POWER, "");
        object.put(PokemonMove.PVP_FAST_ENERGY, "");
        object.put(PokemonMove.PVP_FAST_DURATION, "");

        object.put(PokemonMove.PVP_CHARGE_DAMAGE, "100");
        object.put(PokemonMove.PVP_CHARGE_ENERGY, "-60");

        PokemonMove highHorsepower = PokemonMove.createFromJSON(object);

        if (moveData.get(highHorsepower.getName()) != null)
        {
            throw new RuntimeException(highHorsepower.getName() + " already exists.");
        }
        moveData.put(highHorsepower.getName(), highHorsepower);
    }

    private void createShadowForce()
    {
        JSONObject object = new JSONObject();
        object.put(PokemonMove.TITLE, "Shadow Force");
        object.put(PokemonMove.TYPE, "Ghost");
        object.put(PokemonMove.CATEGORY, "Charge Move");
        object.put(PokemonMove.POWER, 140);
        object.put(PokemonMove.COOLDOWN, "1.9");
        object.put(PokemonMove.ENERGY_GAIN, "0");
        object.put(PokemonMove.ENERGY_COST, "-100");
        object.put(PokemonMove.DODGE_WINDOW, "1.7 seconds");
        object.put(PokemonMove.DAMAGE_WINDOW, "0.2 seconds");

        object.put(PokemonMove.PVP_FAST_POWER, "");
        object.put(PokemonMove.PVP_FAST_ENERGY, "");
        object.put(PokemonMove.PVP_FAST_DURATION, "");

        object.put(PokemonMove.PVP_CHARGE_DAMAGE, "120");
        object.put(PokemonMove.PVP_CHARGE_ENERGY, "-100");

        PokemonMove shadowForce = PokemonMove.createFromJSON(object);

        if (moveData.get(shadowForce.getName()) != null)
        {
            throw new RuntimeException(shadowForce.getName() + " already exists.");
        }
        moveData.put(shadowForce.getName(), shadowForce);
    }

    private void createFairyWind()
    {
        JSONObject object = new JSONObject();
        object.put(PokemonMove.TITLE, "Fairy Wind");
        object.put(PokemonMove.TYPE, "Fairy");
        object.put(PokemonMove.CATEGORY, "Fast Move");
        object.put(PokemonMove.POWER, "9");
        object.put(PokemonMove.COOLDOWN, "0.97");
        object.put(PokemonMove.ENERGY_GAIN, "13");
        object.put(PokemonMove.ENERGY_COST, "0");
        object.put(PokemonMove.DODGE_WINDOW, "0.37 seconds");
        object.put(PokemonMove.DAMAGE_WINDOW, "0.60 seconds");

        object.put(PokemonMove.PVP_FAST_POWER, "3");
        object.put(PokemonMove.PVP_FAST_ENERGY, "9");
        object.put(PokemonMove.PVP_FAST_DURATION, "1");

        object.put(PokemonMove.PVP_CHARGE_DAMAGE, "");
        object.put(PokemonMove.PVP_CHARGE_ENERGY, "");

        PokemonMove fairyWind = PokemonMove.createFromJSON(object);

        if (moveData.get(fairyWind.getName()) != null)
        {
            throw new RuntimeException(fairyWind.getName() + " already exists.");
        }
        moveData.put(fairyWind.getName(), fairyWind);
    }

    private void createDoubleKick()
    {
        JSONObject object = new JSONObject();
        object.put(PokemonMove.TITLE, "Double Kick");
        object.put(PokemonMove.TYPE, "Fighting");
        object.put(PokemonMove.CATEGORY, "Fast Move");
        object.put(PokemonMove.POWER, "8");
        object.put(PokemonMove.COOLDOWN, "1.00");
        object.put(PokemonMove.ENERGY_GAIN, "13");
        object.put(PokemonMove.ENERGY_COST, "0");
        object.put(PokemonMove.DODGE_WINDOW, "0.50 seconds");
        object.put(PokemonMove.DAMAGE_WINDOW, "0.30 seconds");

        object.put(PokemonMove.PVP_FAST_POWER, "8");
        object.put(PokemonMove.PVP_FAST_ENERGY, "12");
        object.put(PokemonMove.PVP_FAST_DURATION, "2");

        object.put(PokemonMove.PVP_CHARGE_DAMAGE, "");
        object.put(PokemonMove.PVP_CHARGE_ENERGY, "");

        PokemonMove doubleKick = PokemonMove.createFromJSON(object);

        if (moveData.get(doubleKick.getName()) != null)
        {
            throw new RuntimeException(doubleKick.getName() + " already exists.");
        }
        moveData.put(doubleKick.getName(), doubleKick);
    }

    private void createShadowPokemon(String pokemonName)
    {
        PokemonData pokemon = pokemonData.get(pokemonName);
        if (pokemon == null)
        {
            throw new RuntimeException("Pokemon not found: " + pokemonName);
        }

        PokemonData shadow = PokemonData.createShadowPokemonFromData(pokemon);

        pokemonData.put(shadow.getName(), shadow);
    }


    private JSONArray readJSONArrayFromFile(String filename)
    {
        String content = "";
        try
        {
            String fileLocation = deploymentContext.webDirectory() + POKEMON_DIR + filename;

            InputStream in = new FileInputStream(fileLocation);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line = reader.readLine();
            while (line != null)
            {
                content += line;

                line = reader.readLine();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return new JSONArray(content);
    }


    private static final String LEVEL_FIELD = "name";
    private static final String CPM_FIELD = "field_cp_multiplier";

    private void loadCpmJSON()
    {
        if (!cpmData.isEmpty())
        {
            return;
        }

        JSONArray objects = readJSONArrayFromFile(CPM_FILE);

        Iterator iter = objects.iterator();
        while (iter.hasNext())
        {
            Object obj = iter.next();
            try
            {
                JSONObject json = (JSONObject) obj;

                Double level = json.getDouble(LEVEL_FIELD);
                Double cpm = json.getDouble(CPM_FIELD);

                if (isInteger(level))
                {
                    cpmData.put(level.intValue(), cpm);
                }
            }
            catch (ClassCastException e)
            {
            }
        }
    }

    private boolean isInteger(Double number)
    {
        Integer intValue = number.intValue();

        return number == intValue.doubleValue();
    }

    private void loadMovesJSON()
    {
        if (!moveData.isEmpty())
        {
            return;
        }

        JSONArray objects = readJSONArrayFromFile(MOVE_FILE);

        Iterator iter = objects.iterator();
        while (iter.hasNext())
        {
            Object obj = iter.next();
            try
            {
                JSONObject json = (JSONObject) obj;

                PokemonMove move = PokemonMove.createFromJSON(json);
                moveData.put(move.getName(), move);
            }
            catch (ClassCastException e)
            {
            }
        }
    }

    private void loadPokemonDataJSON()
    {
        if (!pokemonData.isEmpty())
        {
            return;
        }
        if (moveData.isEmpty())
        {
            loadMovesJSON();
        }

        JSONArray objects = readJSONArrayFromFile(POKEMON_FILE);

        Iterator iter = objects.iterator();
        while (iter.hasNext())
        {
            Object obj = iter.next();
            try
            {
                JSONObject json = (JSONObject) obj;

                PokemonData pokemon = PokemonData.createFromJSON(json);
                pokemonData.put(pokemon.getName(), pokemon);

                Set<PokemonMove> allMoves = new HashSet<PokemonMove>();
                allMoves.addAll(getMovesFromNames(pokemon.getAllFastMoveNames()));
                allMoves.addAll(getMovesFromNames(pokemon.getAllChargeMoveNames()));

                pokemon.setAllChargeMoves(new HashSet<PokemonMove>());
                pokemon.setAllFastMoves(new HashSet<PokemonMove>());

                for (PokemonMove move : allMoves)
                {
                    if (move.isFastMove())
                    {
                        pokemon.getAllFastMoves().add(move);
                    }
                    if (move.isChargeMove())
                    {
                        pokemon.getAllChargeMoves().add(move);
                    }
                }

                allMoves = new HashSet<PokemonMove>();
                allMoves.addAll(getMovesFromNames(pokemon.getStandardFastMoveNames()));
                allMoves.addAll(getMovesFromNames(pokemon.getStandardChargeMoveNames()));

                pokemon.setStandardFastMoves(new HashSet<PokemonMove>());
                pokemon.setStandardChargeMoves(new HashSet<PokemonMove>());

                for (PokemonMove move : allMoves)
                {
                    if (move.isFastMove())
                    {
                        pokemon.getStandardFastMoves().add(move);
                    }
                    if (move.isChargeMove())
                    {
                        pokemon.getStandardChargeMoves().add(move);
                    }
                }
            }
            catch (ClassCastException e)
            {
            }
        }
    }

    private Set<PokemonMove> getMovesFromNames(Set<String> names)
    {
        Set<PokemonMove> moves = new HashSet<PokemonMove>();
        for (String name : names)
        {
            if (!isIgnoreMove(name))
            {
                PokemonMove move = moveData.get(name);
                moves.add(move);
            }
        }
        return moves;
    }

    private boolean isIgnoreMove(String name)
    {
        if (IGNORE_MOVES_POKEMON)
        {
            for (String ignoreMove : IGNORE_MOVES)
            {
                if (name.contains(ignoreMove))
                {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isIgnorePokemon(PokemonData pokemon)
    {
        if (IGNORE_MOVES_POKEMON && IGNORE_POKEMON.contains(pokemon.getName()))
        {
            return true;
        }
        if (pokemon.isHasEvolution())
        {
            return true;
        }
        return false;
    }


    public Double getCpmFromLevel(Integer level)
    {
        if (!cpmData.isEmpty())
        {
            loadCpmJSON();
        }
        return cpmData.get(level);
    }

    public PokemonMove getMoveByName(String name)
    {
        return moveData.get(name);
    }

    public Collection<CounterPokemon> getCounterPokemon(CounterType counterType, BattleMode battleMode)
    {
        if (CounterType.CUSTOM1.equals(counterType))
        {
            return customPrimeService.getCounterPokemon(battleMode);
        }
        if(CounterType.CUSTOM2.equals(counterType))
        {
            return customSecondService.getCounterPokemon(battleMode);
        }
        return getAllGeneralCounters(counterType, battleMode);
    }

    private Collection<CounterPokemon> getAllGeneralCounters(CounterType counterType, BattleMode battleMode)
    {
        if (pokemonData.isEmpty())
        {
            loadPokemonDataJSON();
        }


        List<CounterPokemon> pokemon = new ArrayList<CounterPokemon>();

        for (PokemonData data : pokemonData.values())
        {
            if(!isIgnorePokemon(data))
            {
                double cpm = getCpmFromLevel(LEVEL_40);
                CounterPokemon fullCounter = CounterPokemon.createFromData(data, LEVEL_40, cpm);

                if (battleMode.isRocket() && fullCounter.isMega())
                {
                    continue;
                }

                pokemon.add(fullCounter);

                if(battleMode.isRaid())
                {
                    int highLevel = LEVEL_50;
                    if (isLegendary(data))
                    {
                        highLevel = LEVEL_45;
                    }

                    cpm = getCpmFromLevel(highLevel);
                    CounterPokemon highCounter = CounterPokemon.createFromData(data, highLevel, cpm);

                    pokemon.add(highCounter);
                }
            }
        }
        return pokemon;
    }

    private boolean isLegendary(PokemonData pokemon)
    {
        String pokemonName = pokemon.getName();
        if (pokemon.isShadow())
        {
            pokemonName = pokemonName.substring(SHAODW.length()).trim();
        }
        if (pokemon.isMega())
        {
            pokemonName = pokemonName.substring(MEGA.length()).trim();
        }

        return getLegendaryBosses().contains(pokemonName);
    }

    public List<RaidBossWithAttackingType> getRaidBossForEachAttackingType()
    {
        if (raidBossesWithAttackingTypes.isEmpty())
        {
            generateRaidBossWithAttackingType();
        }
        return raidBossesWithAttackingTypes;
    }

    public List<RocketLeader> getRocketLeaders()
    {
        if (rocketLeaders.isEmpty())
        {
            generateRocketLeaders();
        }
        return rocketLeaders;
    }

    public List<RaidBossPokemon> getRaidBosses()
    {
        if (raidBosses.isEmpty())
        {
            generateRaidBosses();
        }
        return raidBosses;
    }

    public PokemonData getDataByName(String name)
    {
        if (pokemonData.isEmpty())
        {
            loadPokemonDataJSON();
        }
        PokemonData data = pokemonData.get(name);
        if (data == null)
        {
            throw new RuntimeException("Pokemon Not Found: " + name);
        }
        return data;
    }

    private static final List<String> PREFERRED_TYPE_ORDER = Arrays.asList(
            PokemonCounterCalculationServiceImpl.FIRE,
            PokemonCounterCalculationServiceImpl.WATER,
            PokemonCounterCalculationServiceImpl.ELECTRIC,
            PokemonCounterCalculationServiceImpl.GRASS,
            PokemonCounterCalculationServiceImpl.ICE,
            PokemonCounterCalculationServiceImpl.FIGHTING,
            PokemonCounterCalculationServiceImpl.POISON,
            PokemonCounterCalculationServiceImpl.GROUND,
            PokemonCounterCalculationServiceImpl.FLYING,
            PokemonCounterCalculationServiceImpl.PSYCHIC,
            PokemonCounterCalculationServiceImpl.BUG,
            PokemonCounterCalculationServiceImpl.ROCK,
            PokemonCounterCalculationServiceImpl.GHOST,
            PokemonCounterCalculationServiceImpl.DRAGON,
            PokemonCounterCalculationServiceImpl.DARK,
            PokemonCounterCalculationServiceImpl.STEEL,
            PokemonCounterCalculationServiceImpl.FAIRY,
            PokemonCounterCalculationServiceImpl.NORMAL
            );

    private void generateRaidBossWithAttackingType()
    {
        if (!raidBossesWithAttackingTypes.isEmpty())
        {
            return;
        }

        if (pokemonData.isEmpty())
        {
            loadPokemonDataJSON();
        }

        PokemonData arceus = getDataByName(ARCEUS);

        Map<String, String> attackingToDefendingTypes = calculationService.getMapOfAttackingTypeToWeakType();
        for (String attackingType : PREFERRED_TYPE_ORDER)
        {
            String defendingType = attackingToDefendingTypes.get(attackingType);

            RaidBossWithAttackingType typedArceus = RaidBossWithAttackingType.createFromData(arceus, defendingType, attackingType);

            raidBossesWithAttackingTypes.add(typedArceus);
        }
    }

    private void generateRocketLeaders()
    {
        if (!rocketLeaders.isEmpty())
        {
            return;
        }

        if (pokemonData.isEmpty())
        {
            loadPokemonDataJSON();
        }

        for (RocketLeaderName leaderName : RocketLeaderName.values())
        {
            RocketLeader leader = new RocketLeader(leaderName);

            for (String pokemonName : leaderName.pokemonNames())
            {
                RocketBossPokemon pokemon = rocketBosses.get(pokemonName);
                if (pokemon == null)
                {
                    PokemonData data = getDataByName(pokemonName);

                    pokemon = RocketBossPokemon.createFromData(data);

                    rocketBosses.put(pokemonName, pokemon);
                }

                leader.addPokemon(pokemon);
            }

            rocketLeaders.add(leader);
        }
    }

    private void generateRaidBosses()
    {
        if (!raidBosses.isEmpty())
        {
            return;
        }

        for (String name : getRaidBossNames())
        {
            PokemonData data = pokemonData.get(name);
            if (data == null)
            {
                throw new RuntimeException("Pokemon Not Found: " + name);
            }
            RaidBossPokemon raidBoss = RaidBossPokemon.createFromData(data);

            raidBosses.add(raidBoss);
        }
    }

    private List<String> getRaidBossNames()
    {
        List<String> names = new ArrayList<String>();

        //TODO: Add random bosses here
        names.addAll(GEN1_BOSSES);
        names.addAll(GEN2_BOSSES);
        names.addAll(GEN3_BOSSES);
        names.addAll(GEN4_BOSSES);
        names.addAll(GEN5_BOSSES);
        names.addAll(GEN6_BOSSES);
        names.addAll(GEN7_BOSSES);
        names.addAll(GEN8_BOSSES);
        names.addAll(GEN9_BOSSES);
        names.addAll(MEGA_BOSSES);

        return names;
    }

    private List<String> getLegendaryBosses()
    {
        List<String> legendary = new ArrayList<String>();
        legendary.addAll(GEN1_BOSSES);
        legendary.addAll(GEN2_BOSSES);
        legendary.addAll(GEN3_BOSSES);
        legendary.addAll(GEN4_BOSSES);
        legendary.addAll(GEN5_BOSSES);
        legendary.addAll(GEN6_BOSSES);
        legendary.addAll(GEN7_BOSSES);
        legendary.addAll(GEN8_BOSSES);
        legendary.addAll(GEN9_BOSSES);
        legendary.addAll(NONRAID_LEGENDARIES);

        return legendary;
    }

    private static final List<String> NONRAID_LEGENDARIES = Arrays.asList(
            "Hoopa (Confined)",
            "Hoopa (Unbound)",
            "Mew",
            "Jirachu",
            "Celebi",
            "Shaymin (Land Forme)",
            "Shaymin (Sky Forme)",
            "Meloetta (Pirouette Forme)",
            "Meloetta (Aria Forme)"
    );


    private static final List<String> GEN1_BOSSES = Arrays.asList(
            "Mewtwo",
            "Armored Mewtwo",
            "Articuno",
            "Zapdos",
            "Moltres",
            "Galarian Articuno",
            "Galarian Zapdos",
            "Galarian Moltres"
    );

    private static final List<String> GEN2_BOSSES = Arrays.asList(
            "Raikou",
            "Entei",
            "Suicune",
            "Lugia",
            "Ho-Oh"
    );

    private static final List<String> GEN3_BOSSES = Arrays.asList(
            "Regirock",
            "Regice",
            "Registeel",
            "Latias",
            "Latios",
            "Kyogre",
            "Groudon",
            "Rayquaza",
            "Deoxys (Normal Forme)",
            "Deoxys (Attack Forme)",
            "Deoxys (Defense Forme)",
            "Deoxys (Speed Forme)"
    );

    private static final List<String> GEN4_BOSSES = Arrays.asList(
            "Uxie",
            "Mesprit",
            "Azelf",
            "Dialga",
            "Palkia",
            "Heatran",
            "Regigigas",
            "Giratina (Altered Forme)",
            "Giratina (Origin Forme)",
            "Cresselia",
            "Darkrai",
            "Arceus"
    );

    private static final List<String> GEN5_BOSSES = Arrays.asList(
            "Cobalion",
            "Terrakion",
            "Virizion",
            "Tornadus (Incarnate Forme)",
            "Tornadus (Therian Forme)",
            "Thundurus  (Incarnate Forme)",
            "Thundurus (Therian Forme)",
            "Reshiram",
            "Zekrom",
            "Landorus (Incarnate Forme)",
            "Landorus (Therian Forme)",
            "Kyurem",
            "White Kyurem",
            "Black Kyurem",
            "Keldeo",
            "Genesect - Burn Drive",
            "Genesect - Shock Drive",
            "Genesect - Douse Drive",
            "Genesect - Chill Drive"
    );

    private static final List<String> GEN6_BOSSES = Arrays.asList(
            "Xerneas",
            "Yveltal",
            "Zygarde (50% Forme)",
            "Zygarde (Complete Forme)",
            "Volcanion",
            "Hoopa (Unbound)"
    );

    private static final List<String> GEN7_BOSSES = Arrays.asList(
            "Tapu Koko",
            "Tapu Lele",
            "Tapu Bulu",
            "Tapu Fini",
            "Solgaleo",
            "Lunala",
            "Nihilego",
            "Buzzwole",
            "Pheromosa",
            "Xurkitree",
            "Celesteela",
            "Kartana",
            "Guzzlord",
            "Necrozma",
            "Stakataka",
            "Blacephalon",
            "Zeraora"
    );

    private static final List<String> GEN8_BOSSES = Arrays.asList(
            "Zacian - Hero of Many Battles",
            "Zamazenta - Hero of Many Battles",
            "Regieleki",
            "Regidrago"
    );

    private static final List<String> GEN9_BOSSES = Arrays.asList(

    );

    private static final List<String> MEGA_BOSSES = Arrays.asList(
            "Mega Venusaur",
            "Mega Charizard X",
            "Mega Charizard Y",
            "Mega Blastoise",

            "Mega Beedrill",
            "Mega Pidgeot",
            "Mega Gengar",
            "Mega Slowbro",
            "Mega Gyarados",
            "Mega Aerodactyl",

            "Mega Ampharos",
            "Mega Houndoom",
            "Mega Steelix",
            "Mega Manectric",
            "Mega Altaria",
            "Mega Absol",
            "Mega Abomasnow",
            "Mega Lopunny",

            "Mega Alakazam",
            "Mega Kangaskhan",
            "Mega Pinsir",
            "Mega Heracross",
            "Mega Scizor",

            "Mega Blaziken",
            "Mega Sceptile",
            "Mega Swampert",

            "Mega Gardevoir",
            "Mega Gallade",
            "Mega Audino",
            "Mega Mawile",
            "Mega Aggron",
            "Mega Medicham",
            "Mega Banette",
            "Mega Sableye",
            "Mega Sharpedo",
            "Mega Camerupt",
            "Mega Glalie",

            "Mega Tyranitar",
            "Mega Salamence",
            "Mega Metagross",
            "Mega Garchomp",
            "Mega Lucario",

            "Primal Groudon",
            "Primal Kyogre",
            "Mega Mewtwo X",
            "Mega Mewtwo Y",
            "Mega Latias",
            "Mega Latios",
            "Mega Rayquaza",
            "Mega Diancie"
    );

}
