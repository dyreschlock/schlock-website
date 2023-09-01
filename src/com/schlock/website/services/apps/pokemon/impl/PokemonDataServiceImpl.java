package com.schlock.website.services.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.*;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.pokemon.PokemonCounterCalculationService;
import com.schlock.website.services.apps.pokemon.PokemonCustomCounterPrimeService;
import com.schlock.website.services.apps.pokemon.PokemonCustomCounterSecondService;
import com.schlock.website.services.apps.pokemon.PokemonDataService;
import com.schlock.website.services.database.apps.pokemon.PokemonDataDAO;
import com.schlock.website.services.database.apps.pokemon.PokemonMoveDAO;
import org.apache.commons.lang.StringUtils;
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

    private static final String POKEMON_DIR = "pokemon-data/counter-data/";

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

    private final PokemonDataDAO dataDAO;
    private final PokemonMoveDAO moveDAO;

    private final DeploymentContext deploymentContext;

    public PokemonDataServiceImpl(PokemonCustomCounterPrimeService customPrimeService,
                                  PokemonCustomCounterSecondService customSecondService,
                                  PokemonCounterCalculationService calculationService,
                                  PokemonDataDAO dataDAO,
                                  PokemonMoveDAO moveDAO,
                                  DeploymentContext deploymentContext)
    {
        this.customPrimeService = customPrimeService;
        this.customSecondService = customSecondService;
        this.calculationService = calculationService;

        this.dataDAO = dataDAO;
        this.moveDAO = moveDAO;

        this.deploymentContext = deploymentContext;

        loadCpmJSON();
//        updateDatabaseFromGamepress();
    }




    public void updateDatabaseFromGamepress()
    {
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
        createGeomancy();
        createObliviionWing();
        createWaterShuriken();
        createDragonAscent();
        createMagmaStorm();

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

        //Community Day Moves
        addEliteMoveToPokemon("Chandelure", "Poltergeist");
        addEliteMoveToPokemon("Greninja", "Hydro Cannon");
        addEliteMoveToPokemon("Delphox", "Blast Burn");
        addEliteMoveToPokemon("Chesnaught", "Frenzy Plant");
        addEliteMoveToPokemon("Haxorus", "Breaking Swipe");
        addEliteMoveToPokemon("Poliwrath", "Counter");
        addEliteMoveToPokemon("Politoed", "Ice Beam");

        //Summer 2023 Signature Move additions
        addEliteMoveToPokemon("Heatran", "Magma Storm");
        addEliteMoveToPokemon("Xerneas", "Geomancy");
        addEliteMoveToPokemon("Yveltal", "Oblivion Wing");
        addEliteMoveToPokemon("Rayquaza", "Dragon Ascent");
        addStandardMoveToPokemon("Greninja", "Water Shuriken");

//        addEliteMoveToPokemon("Decidueye", "Frenzy Plant");
//        addEliteMoveToPokemon("Incineroar", "Blast Burn");
//        addEliteMoveToPokemon("Primarina", "Hydro Cannon");

        //missing in json
        addEliteMoveToPokemon("Umbreon", "Psychic");
        addEliteMoveToPokemon("Rhyhorn", "Earthquake");

        addEliteMoveToPokemon("Kyogre", "Origin Pulse");

        addEliteMoveToPokemon("Rayquaza", "Breaking Swipe");

        addStandardMoveToPokemon("Tyranitar", "Brutal Swing");
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

            pokemon.getAllMoves().add(move);
            pokemon.setAllMoveNames(pokemon.getAllMoveNames() + PokemonData.MOVE_DELIM + move.getName());

            if (standard)
            {
                pokemon.getStandardMoves().add(move);
                pokemon.setStandardMoveNames(pokemon.getStandardMoveNames() + PokemonData.MOVE_DELIM + move.getName());
            }

            dataDAO.save(pokemon);
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
        createChargeMove(
                "High Horsepower",
                "Ground",
                110,
                "1.6",
                "-100",
                "1.0 seconds",
                "0.2 seconds",
                "100",
                "-60"
        );
    }

    private void createShadowForce()
    {
        // https://gamepress.gg/pokemongo/pokemon-move/shadow-force

        createChargeMove(
                "Shadow Force",
                "Ghost",
                140,
                "1.9",
                "-100",
                "1.7 seconds",
                "0.2 seconds",
                "120",
                "-100"
        );
    }

    private void createObliviionWing()
    {
        // https://gamepress.gg/pokemongo/pokemon-move/oblivion-wing
        createChargeMove(
                "Oblivion Wing",
                "Flying",
                85,
                "2.0",
                "-50",
                "1.5 seconds",
                "0.5 seconds",
                "85",
                "-50"
        );
    }

    private void createDragonAscent()
    {
        // https://gamepress.gg/pokemongo/pokemon-move/dragon-ascent
        createChargeMove(
                "Dragon Ascent",
                "Flying",
                140,
                "3.50",
                "-50",
                "3.2 seconds",
                "0.3 seconds",
                "150",
                "-70"
        );
    }

    private void createMagmaStorm()
    {
        createChargeMove(
                "Magma Storm",
                "Fire",
                75,
                "2.50",
                "-33",
                "1.3 seconds",
                "1.2 seconds",
                "65",
                "-40"
        );
    }

    private void createChargeMove(String title,
                                  String type,
                                  int power,
                                  String cooldown,
                                  String energyCost, // full bar = -100 / half bar = -50 / three bars = -33
                                  String dodgeWindow,
                                  String damageWindow, // = Cooldown - Dodge Window
                                  String pvpDamage,
                                  String pvpEnergy)
    {
        JSONObject object = new JSONObject();
        object.put(PokemonMove.TITLE, title);
        object.put(PokemonMove.TYPE, type);
        object.put(PokemonMove.CATEGORY, "Charge Move");
        object.put(PokemonMove.POWER, power);
        object.put(PokemonMove.COOLDOWN, cooldown);
        object.put(PokemonMove.ENERGY_GAIN, "0");
        object.put(PokemonMove.ENERGY_COST, energyCost);
        object.put(PokemonMove.DODGE_WINDOW, dodgeWindow);
        object.put(PokemonMove.DAMAGE_WINDOW, damageWindow);

        object.put(PokemonMove.PVP_FAST_POWER, "");
        object.put(PokemonMove.PVP_FAST_ENERGY, "");
        object.put(PokemonMove.PVP_FAST_DURATION, "");

        object.put(PokemonMove.PVP_CHARGE_DAMAGE, pvpDamage);
        object.put(PokemonMove.PVP_CHARGE_ENERGY, pvpEnergy);

        PokemonMove chargeMove = PokemonMove.createFromJSON(object);

        if (moveData.get(chargeMove.getName()) != null)
        {
            throw new RuntimeException(chargeMove.getName() + " already exists.");
        }
        moveData.put(chargeMove.getName(), chargeMove);

        moveDAO.save(chargeMove);
    }

    private void createGeomancy()
    {
        // https://gamepress.gg/pokemongo/pokemon-move/geomancy
        createFastMove(
                "Geomancy",
                "Fairy",
                "20",
                "1.50",
                "14",
                "0.50 seconds",
                "1.00 seconds",
                "4",
                "13",
                "1.5"
        );
    }

    private void createWaterShuriken()
    {
        // https://gamepress.gg/pokemongo/pokemon-move/water-shuriken
        createFastMove(
                "Water Shuriken",
                "Water",
                "10",
                "1.10",
                "15",
                "0.1 seconds",
                "1.1 seconds",
                "6",
                "14",
                "1.5"
        );
    }

    private void createFairyWind()
    {
        // https://gamepress.gg/pokemongo/pokemon-move/fairy-wind
        createFastMove(
                "Fairy Wind",
                "Fairy",
                "9",
                "0.97",
                "13",
                "0.37 seconds",
                "0.60 seconds",
                "3",
                "9",
                "1"
        );
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

    private void createFastMove(String title,
                                String type,
                                String power,
                                String cooldown,
                                String energyDelta,
                                String dodgeWindow, // Cooldown - Damage Window
                                String damageWindow,
                                String pvpPower,
                                String pvpEnergy,
                                String pvpDuration)
    {
        JSONObject object = new JSONObject();
        object.put(PokemonMove.TITLE, title);
        object.put(PokemonMove.TYPE, type);
        object.put(PokemonMove.CATEGORY, "Fast Move");
        object.put(PokemonMove.POWER, power);
        object.put(PokemonMove.COOLDOWN, cooldown);
        object.put(PokemonMove.ENERGY_GAIN, energyDelta);
        object.put(PokemonMove.ENERGY_COST, "0");
        object.put(PokemonMove.DODGE_WINDOW, dodgeWindow);
        object.put(PokemonMove.DAMAGE_WINDOW, damageWindow);

        object.put(PokemonMove.PVP_FAST_POWER, pvpPower);
        object.put(PokemonMove.PVP_FAST_ENERGY, pvpEnergy);
        object.put(PokemonMove.PVP_FAST_DURATION, pvpDuration);

        object.put(PokemonMove.PVP_CHARGE_DAMAGE, "");
        object.put(PokemonMove.PVP_CHARGE_ENERGY, "");

        PokemonMove newFastMove = PokemonMove.createFromJSON(object);

        if (moveData.get(newFastMove.getName()) != null)
        {
            throw new RuntimeException(newFastMove.getName() + " already exists.");
        }
        moveData.put(newFastMove.getName(), newFastMove);

        moveDAO.save(newFastMove);
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
            String fileLocation = deploymentContext.dataDirectory() + POKEMON_DIR + filename;

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

                moveDAO.save(move);
            }
            catch (ClassCastException e)
            {
            }
        }
    }

    private void loadPokemonDataFromDatabase()
    {
        if (!pokemonData.isEmpty())
        {
            return;
        }

        List<PokemonData> allData = dataDAO.getAll();
        for(PokemonData data : allData)
        {
            pokemonData.put(data.getName(), data);
        }

        List<PokemonMove> allMoves = moveDAO.getAll();
        for(PokemonMove move : allMoves)
        {
            moveData.put(move.getName(), move);
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

                Set<PokemonMove> moves = new HashSet<PokemonMove>();
                moves.addAll(getMovesFromNames(pokemon.getAllMoveNames()));

                pokemon.setAllMoves(moves);

                moves = new HashSet<PokemonMove>();
                moves.addAll(getMovesFromNames(pokemon.getStandardMoveNames()));

                pokemon.setStandardMoves(moves);

                dataDAO.save(pokemon);
            }
            catch (ClassCastException e)
            {
            }
        }
    }

    private Set<PokemonMove> getMovesFromNames(String names)
    {
        Set<PokemonMove> moves = new HashSet<PokemonMove>();
        if (StringUtils.isEmpty(names))
        {
            return moves;
        }

        for (String name : names.split(PokemonData.MOVE_DELIM))
        {
            if (!isIgnoreMove(name))
            {
                PokemonMove move = moveData.get(name);
                if (move == null)
                {
                    throw new RuntimeException("Move is missing: " + name);
                }
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
        if (IGNORE_MOVES_POKEMON && pokemon.isIgnore())
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
        return getAllGeneralCounters(battleMode);
    }

    private Collection<CounterPokemon> getAllGeneralCounters(BattleMode battleMode)
    {
        if (pokemonData.isEmpty())
        {
            loadPokemonDataFromDatabase();
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
                    if(data.isLegendary())
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
            loadPokemonDataFromDatabase();
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
            loadPokemonDataFromDatabase();
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
            loadPokemonDataFromDatabase();
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

        if (pokemonData.isEmpty())
        {
            loadPokemonDataFromDatabase();
        }

        List<PokemonData> bosses = dataDAO.getRaidBosses();
        for(PokemonData boss : bosses)
        {
            RaidBossPokemon raidBoss = RaidBossPokemon.createFromData(boss);
            raidBosses.add(raidBoss);
        }
    }
}
