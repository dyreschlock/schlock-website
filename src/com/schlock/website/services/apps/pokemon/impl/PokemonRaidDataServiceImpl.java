package com.schlock.website.services.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.*;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.pokemon.PokemonRaidDataService;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;

import java.io.*;
import java.util.*;

/**
 * Data Taken from : https://gamepress.gg/pokemongo/comprehensive-dps-spreadsheet
 */
public class PokemonRaidDataServiceImpl implements PokemonRaidDataService
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
            "Mega Alakazam",
            "Mega Pinsir",

            "Mega Heracross",

            "Mega Banette",
            "Mega Glalie",
            "Mega Gardevoir",
            "Mega Gallade",
            "Mega Sableye",
            "Mega Mawile",
            "Mega Aggron",
            "Mega Medicham",
            "Mega Shapedo",
            "Mega Camerupt",
            "Mega Audino",

            "Mega Swampert",
            "Mega Blaziken",
            "Mega Sceptile",

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


    private List<RaidBoss> raidBosses = new ArrayList<RaidBoss>();

    private HashMap<String, RaidMove> raidMoveData = new HashMap<String, RaidMove>();
    private HashMap<String, RaidPokemonData> raidPokemonData = new HashMap<String, RaidPokemonData>();

    private HashMap<Integer, Double> cpmData = new HashMap<Integer, Double>();

    private final DeploymentContext deploymentContext;

    public PokemonRaidDataServiceImpl(DeploymentContext deploymentContext)
    {
        this.deploymentContext = deploymentContext;

        loadCpmJSON();
        loadRaidMoveJSON();
        loadRaidPokemonDataJSON();

        if (USE_OVERWRITES)
        {
            addOverwrites();
        }
    }


    private void addOverwrites()
    {
        createBrutalSwing();

        addMoveToPokemon("Hydreigon", "Brutal Swing");

        addMoveToPokemon("Xurkitree", "Thunder Shock", "Discharge");

        addMoveToPokemon("Buzzwole", "Counter", "Poison Jab", "Power-Up Punch", "Fell Stinger", "Lunge", "Superpower");
        addMoveToPokemon("Pheromosa", "Bug Bite", "Low Kick", "Focus Blast", "Bug Buzz", "Lunge", "Close Combat");
    }

    private void addMoveToPokemon(String pokemonName, String... moveNames)
    {
        RaidPokemonData pokemon = raidPokemonData.get(pokemonName);
        if (pokemon == null)
        {
            throw new RuntimeException("Pokemon not found: " + pokemonName);
        }

        for (String moveName : moveNames)
        {
            RaidMove move = raidMoveData.get(moveName);
            if (move == null)
            {
                throw new RuntimeException("Move not found: " + moveName);
            }

            if (move.isFastMove())
            {
                pokemon.getAllFastMoves().add(move);
            }
            if (move.isChargeMove())
            {
                pokemon.getAllChargeMoves().add(move);
            }
        }
    }

    private void createBrutalSwing()
    {
        JSONObject object = new JSONObject();
        object.put(RaidMove.TITLE, "Brutal Swing");
        object.put(RaidMove.TYPE, "Dark");
        object.put(RaidMove.CATEGORY, "Charge Move");
        object.put(RaidMove.POWER, "65");
        object.put(RaidMove.COOLDOWN, "1.70");
        object.put(RaidMove.ENERGY_GAIN, "");
        object.put(RaidMove.ENERGY_COST, "-50");
        object.put(RaidMove.DODGE_WINDOW, "0.20 seconds");
        object.put(RaidMove.DAMAGE_WINDOW, "1.40 seconds");

        RaidMove brutalSwing = RaidMove.createFromJSON(object);
        raidMoveData.put(brutalSwing.getName(), brutalSwing);
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

    private void loadRaidMoveJSON()
    {
        if (!raidMoveData.isEmpty())
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

                RaidMove move = RaidMove.createFromJSON(json);
                raidMoveData.put(move.getName(), move);
            }
            catch (ClassCastException e)
            {
            }
        }
    }

    private void loadRaidPokemonDataJSON()
    {
        if (!raidPokemonData.isEmpty())
        {
            return;
        }
        if (raidMoveData.isEmpty())
        {
            loadRaidMoveJSON();
        }

        JSONArray objects = readJSONArrayFromFile(POKEMON_FILE);

        Iterator iter = objects.iterator();
        while (iter.hasNext())
        {
            Object obj = iter.next();
            try
            {
                JSONObject json = (JSONObject) obj;

                RaidPokemonData pokemon = RaidPokemonData.createFromJSON(json);
                raidPokemonData.put(pokemon.getName(), pokemon);

                Set<RaidMove> fastMoves = getRaidMovesFromNames(pokemon.getAllFastMoveNames());
                Set<RaidMove> chargeMoves = getRaidMovesFromNames(pokemon.getAllChargeMoveNames());

                pokemon.setAllFastMoves(fastMoves);
                pokemon.setAllChargeMoves(chargeMoves);

                fastMoves = getRaidMovesFromNames(pokemon.getStandardFastMoveNames());
                chargeMoves = getRaidMovesFromNames(pokemon.getStandardChargeMoveNames());

                pokemon.setStandardFastMoves(fastMoves);
                pokemon.setStandardChargeMoves(chargeMoves);
            }
            catch (ClassCastException e)
            {
            }
        }
    }

    private Set<RaidMove> getRaidMovesFromNames(Set<String> names)
    {
        Set<RaidMove> moves = new HashSet<RaidMove>();
        for (String name : names)
        {
            if (!isIgnoreMove(name))
            {
                RaidMove move = raidMoveData.get(name);
                moves.add(move);
            }
        }
        return moves;
    }

    private boolean isIgnoreMove(String name)
    {
        return IGNORE_MOVES_POKEMON && IGNORE_MOVES.contains(name);
    }

    private boolean isIgnorePokemon(RaidPokemonData pokemon)
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

    public Collection<RaidCounter> getSuitableCounterPokemon(RaidCounterType counterType)
    {
        return getAllGeneralPokemon();
    }

    private Collection<RaidCounter> getAllGeneralPokemon()
    {
        if (raidPokemonData.isEmpty())
        {
            loadRaidPokemonDataJSON();
        }

        List<RaidCounter> pokemon = new ArrayList<RaidCounter>();

        for (RaidPokemonData data : raidPokemonData.values())
        {
            if (IGNORE_MOVES_POKEMON && !IGNORE_POKEMON.contains(data.getName()))
            {
                RaidCounter fullCounter = RaidCounter.createFromData(data, LEVEL_40);

                int highLevel = LEVEL_50;
                if (isLegendary(data.getName()))
                {
                    highLevel = LEVEL_45;
                }

                RaidCounter highCounter = RaidCounter.createFromData(data, highLevel);

                pokemon.add(fullCounter);
                pokemon.add(highCounter);
            }
        }
        return pokemon;
    }

    private boolean isLegendary(String pokemonName)
    {
        return getLegendaryBosses().contains(pokemonName);
    }

    public List<RaidBoss> getRaidBosses()
    {
        if (raidBosses.isEmpty())
        {
            generateRaidBosses();
        }
        return raidBosses;
    }

    private void generateRaidBosses()
    {
        if (!raidBosses.isEmpty())
        {
            return;
        }

        for (String name : getRaidBossNames())
        {
            RaidPokemonData data = raidPokemonData.get(name);
            if (data == null)
            {
                throw new RuntimeException("Pokemon Not Found: " + name);
            }
            RaidBoss raidBoss = RaidBoss.createFromData(data);

            raidBosses.add(raidBoss);
        }
    }

    private List<String> getRaidBossNames()
    {
        List<String> names = new ArrayList<String>();
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
            "Hoopa (Unbound)"
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
            "Genesect"
    );

    private static final List<String> GEN6_BOSSES = Arrays.asList(
            "Xerneas",
            "Yveltal",
            "Zygarde (50% Forme)",
            "Zygarde (Complete Forme)",
            "Volcanion"
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
