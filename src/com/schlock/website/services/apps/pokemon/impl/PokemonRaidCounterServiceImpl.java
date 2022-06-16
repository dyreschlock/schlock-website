package com.schlock.website.services.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.RaidCounterPokemon;
import com.schlock.website.entities.apps.pokemon.RaidBoss;
import com.schlock.website.entities.apps.pokemon.RaidMove;
import com.schlock.website.entities.apps.pokemon.RaidPokemonData;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.pokemon.PokemonRaidCounterService;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;

import java.io.*;
import java.util.*;

/**
 * Data Taken from : https://gamepress.gg/pokemongo/comprehensive-dps-spreadsheet
 *
 */
public class PokemonRaidCounterServiceImpl implements PokemonRaidCounterService
{
    private static final String MOVE_FILE = "move-data-full-PoGO.json";
    private static final String BOSS_FILE = "raid-boss-list-PoGO.json";
    private static final String POKEMON_FILE = "pokemon-data-full-en-PoGO.json";


    private static final int NUMBER_OF_LINES_TO_READ_FROM_FILE = 200;

    private static final int NUMBER_OF_TOP_MEGA_COUNTERS_PER_POKEMON = 10;
    private static final int NUMBER_OF_TOP_SHADOW_COUNTERS_PER_POKEMON = 20;
    private static final int NUMBER_OF_TOP_REGULAR_COUNTERS_PER_POKEMON = 32;

    private static final int NUMBER_OF_MEGA_COUNTERS_PER_POKEMON = 4;
    private static final int NUMBER_OF_SHADOW_COUNTERS_PER_POKEMON = 8;
    private static final int NUMBER_OF_REGULAR_COUNTERS_PER_POKEMON = 20;


    private static final String POKEMON_DIR = "pokemon/raid/";
    private static final String CSV = ".csv";

    private static final int LEVEL40 = 40;
    private static final int LEVEL50 = 50;

    private final static boolean IGNORE_THESE = true;
//    private final static boolean IGNORE_THESE = false;

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
                                    "Calyrex - Ice Rider");


    private static final List<String> GEN1_BOSSES = Arrays.asList(
//            "Armored Mewtwo",
            "Mewtwo",
            "Articuno",
            "Zapdos",
            "Moltres"//,
//            "Galarian Articuno",
//            "Galarian Zapdos",
//            "Galarian Moltres"
    );

    private static final List<String> GEN2_BOSSES = Arrays.asList(
            "Raikou",
            "Entei",
            "Suicune",
            "Lugia",
            "Ho-oh"
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

    private final List<String> gen1 = Arrays.asList("mewtwo",
                                                    "mewtwo_armored",
                                                    "articuno",
                                                    "zapdos",
                                                    "moltres",
                                                    "articuno_galarian",
                                                    "zapdos_galarian",
                                                    "moltres_galarian");

    private final List<String> gen2 = Arrays.asList("raikou",
                                                    "entei",
                                                    "suicune",
                                                    "lugia",
                                                    "ho-oh");

    private final List<String> gen3 = Arrays.asList("regirock",
                                                    "regice",
                                                    "registeel",
                                                    "latias",
                                                    "latios",
                                                    "kyogre",
                                                    "groudon",
                                                    "rayquaza",
                                                    "deoxys_normal",
                                                    "deoxys_attack",
                                                    "deoxys_defense",
                                                    "deoxys_speed");

    private final List<String> gen4 = Arrays.asList("uxie",
                                                    "mesprit",
                                                    "azelf",
                                                    "dialga",
                                                    "palkia",
                                                    "heatran",
                                                    "regigigas",
                                                    "giratina_altered",
                                                    "giratina_origin",
                                                    "cresselia",
                                                    "darkrai",
                                                    "arceus");

    private final List<String> gen5 = Arrays.asList("cobalion",
                                                    "terrakion",
                                                    "virizion",
                                                    "tornadus_incarnate",
                                                    "tornadus_therian",
                                                    "thundurus_incarnate",
                                                    "thundurus_therian",
                                                    "reshiram",
                                                    "zekrom",
                                                    "landorus_incarnate",
                                                    "landorus_therian",
                                                    "kyurem",
                                                    "kyurem_white",
                                                    "kyurem_black",
                                                    "keldeo",
                                                    "genesect");

    private final List<String> gen6 = Arrays.asList("xerneas",
                                                    "yveltal",
                                                    "zygarde",
                                                    "zygarde_complete",
                                                    "volcanion");

    private final List<String> gen7 = Arrays.asList("tapu_koko",
                                                    "tapu_lele",
                                                    "tapu_bulu",
                                                    "tapu_fini",
                                                    "solgaleo",
                                                    "lunala",
                                                    "nihilego",
                                                    "buzzwole",
                                                    "pheromosa",
                                                    "xurkitree",
                                                    "celesteela",
                                                    "kartana",
                                                    "guzzlord",
                                                    "necrozma",
                                                    "stakataka",
                                                    "blacephalon",
                                                    "zeraora");

    private final List<String> gen8 = Arrays.asList("zacian",
                                                    "zamazenta",
                                                    "regieleki",
                                                    "regidrago");

    private final List<String> mega = Arrays.asList("venusaur_mega",
                                                    "charizard_mega_x",
                                                    "charizard_mega_y",
                                                    "blastoise_mega",

                                                    "beedrill_mega",
                                                    "pidgeot_mega",
                                                    "gengar_mega",
                                                    "slowbro_mega",
                                                    "gyarados_mega",
                                                    "aerodactyl_mega",

                                                    "ampharos_mega",
                                                    "houndoom_mega",
                                                    "steelix_mega",
                                                    "manectric_mega",
                                                    "altaria_mega",
                                                    "absol_mega",
                                                    "abomasnow_mega",
                                                    "lopunny_mega",

                                                    "alakazam_mega",
                                                    "kangaskhan_mega",
                                                    "pinsir_mega",
                                                    "heracross_mega",
                                                    "scizor_mega",

                                                    "blaziken_mega",
                                                    "sceptile_mega",
                                                    "swampert_mega",

                                                    "gardevoir_mega",
                                                    "gallade_mega",
                                                    "audino_mega",
                                                    "mawile_mega",
                                                    "aggron_mega",
                                                    "medicham_mega",
                                                    "banette_mega",
                                                    "sableye_mega",
                                                    "sharpedo_mega",
                                                    "camerupt_mega",
                                                    "glalie_mega",

                                                    "tyranitar_mega",
                                                    "salamence_mega",
                                                    "metagross_mega",
                                                    "garchomp_mega",
                                                    "lucario_mega",

                                                    "groudon_primal",
                                                    "kyogre_primal",
                                                    "mewtwo_mega_x",
                                                    "mewtwo_mega_y",
                                                    "latias_mega",
                                                    "latios_mega",
                                                    "rayquaza_mega",
                                                    "diancie_mega");

    private List<String> legendaryPokemonFilenames = new ArrayList<String>();

    private List<RaidBoss> raidBoss = new ArrayList<RaidBoss>();
    private HashMap<String, List<RaidCounterPokemon>> counterPokemon = new HashMap<String, List<RaidCounterPokemon>>();

    private List<RaidCounterPokemon> topMegaCounterPokemon = new ArrayList<RaidCounterPokemon>();
    private List<RaidCounterPokemon> topShadowCounterPokemon = new ArrayList<RaidCounterPokemon>();
    private List<RaidCounterPokemon> topRegularCounterPokemon = new ArrayList<RaidCounterPokemon>();



    private List<String> raidBossPokemon = new ArrayList<String>();



    private HashMap<String, RaidBoss> raidBossData = new HashMap<String, RaidBoss>();
    private HashMap<String, RaidMove> raidMoveData = new HashMap<String, RaidMove>();
    private Set<RaidPokemonData> raidPokemonData = new HashSet<RaidPokemonData>();

    private final DeploymentContext deploymentContext;


    public PokemonRaidCounterServiceImpl(DeploymentContext deploymentContext)
    {
        this.deploymentContext = deploymentContext;

        legendaryPokemonFilenames.clear();
        legendaryPokemonFilenames.addAll(gen1);
        legendaryPokemonFilenames.addAll(gen2);
        legendaryPokemonFilenames.addAll(gen3);
        legendaryPokemonFilenames.addAll(gen4);
        legendaryPokemonFilenames.addAll(gen5);
        legendaryPokemonFilenames.addAll(gen6);
        legendaryPokemonFilenames.addAll(gen7);
        legendaryPokemonFilenames.addAll(gen8);
        legendaryPokemonFilenames.addAll(mega);

        raidBossPokemon.clear();
        raidBossPokemon.addAll(GEN1_BOSSES);
//        raidBossPokemon.addAll(GEN2_BOSSES);
//        raidBossPokemon.addAll(GEN3_BOSSES);

        loadRaidBossJSON();
        loadRaidMoveJSON();
        loadRaidPokemonDataJSON();

        loadCounterData();
    }

    private void loadRaidBossJSON()
    {
        if (!raidBossData.isEmpty())
        {
            return;
        }

        JSONArray objects = readJSONArrayFromFile(BOSS_FILE);

        Iterator iter = objects.iterator();
        while(iter.hasNext())
        {
            Object obj = iter.next();

            try
            {
                JSONObject json = (JSONObject) obj;

                RaidBoss boss = RaidBoss.createFromJSON(json);
                raidBossData.put(boss.getName(), boss);
            }
            catch(ClassCastException e)
            {
            }
        }
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

        JSONArray objects = readJSONArrayFromFile(POKEMON_FILE);

        Iterator iter = objects.iterator();
        while (iter.hasNext())
        {
            Object obj = iter.next();
            try
            {
                JSONObject json = (JSONObject) obj;

                RaidPokemonData pokemon = RaidPokemonData.createFromJSON(json);
                raidPokemonData.add(pokemon);
            }
            catch (ClassCastException e)
            {
            }
        }
    }

    private void generateRaidCounterData()
    {

    }

    private void loadCounterData()
    {
        for (String pokemonName : legendaryPokemonFilenames)
        {
            RaidBoss pokemon = new RaidBoss(pokemonName);
            raidBoss.add(pokemon);

            List<RaidCounterPokemon> counterList = loadCounterList(pokemonName);
            counterPokemon.put(pokemonName, counterList);
        }
    }

    private List<RaidCounterPokemon> loadCounterList(String pokemonName)
    {
        List<String> linesFrom40File = new ArrayList<String>();
        List<String> linesFrom50File = new ArrayList<String>();
        try
        {
            linesFrom40File = readLinesFromFile(pokemonName, LEVEL40);
            linesFrom50File = readLinesFromFile(pokemonName, LEVEL50);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        if (linesFrom40File.isEmpty() || linesFrom50File.isEmpty())
        {
            return Collections.emptyList();
        }

        List<RaidCounterPokemon> counterList = parseStringsIntoList(linesFrom40File, linesFrom50File);
        return counterList;
    }

    private JSONArray readJSONArrayFromFile(String filename)
    {
        String content = "";
        try
        {
            String fileLocation = deploymentContext.webDirectory() + POKEMON_DIR + filename;

            InputStream in = new FileInputStream(fileLocation);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            content = reader.readLine();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return new JSONArray(content);
    }

    private List<String> readLinesFromFile(String pokemonFile, int level) throws IOException
    {
        String filename = pokemonFile + "." + level + CSV;
        String fileLocation = deploymentContext.webDirectory() + POKEMON_DIR + filename;

        InputStream in = new FileInputStream(fileLocation);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String columnNames = reader.readLine();

        List<String> lines = new ArrayList<String>();
        for(int i = 0; i < NUMBER_OF_LINES_TO_READ_FROM_FILE; i++)
        {
            String line = reader.readLine();
            lines.add(line);
        }
        return lines;
    }

    private List<RaidCounterPokemon> parseStringsIntoList(List<String> pokemonStrings40, List<String> pokemonStrings50)
    {
        List<RaidCounterPokemon> megaCounters = new ArrayList<RaidCounterPokemon>();
        List<RaidCounterPokemon> shadowCounters = new ArrayList<RaidCounterPokemon>();

        List<RaidCounterPokemon> regularCounters = new ArrayList<RaidCounterPokemon>();

        megaCounters.addAll(getTopMegasFromList(pokemonStrings50, NUMBER_OF_TOP_MEGA_COUNTERS_PER_POKEMON, LEVEL50));
        megaCounters.addAll(getTopMegasFromList(pokemonStrings40, NUMBER_OF_TOP_MEGA_COUNTERS_PER_POKEMON, LEVEL40));

        shadowCounters.addAll(getTopShadowsFromList(pokemonStrings50, NUMBER_OF_TOP_SHADOW_COUNTERS_PER_POKEMON, LEVEL50));
        shadowCounters.addAll(getTopShadowsFromList(pokemonStrings40, NUMBER_OF_TOP_SHADOW_COUNTERS_PER_POKEMON, LEVEL40));

        regularCounters.addAll(getTopRegularsFromList(pokemonStrings50, NUMBER_OF_TOP_REGULAR_COUNTERS_PER_POKEMON, LEVEL50));
        regularCounters.addAll(getTopRegularsFromList(pokemonStrings40, NUMBER_OF_TOP_REGULAR_COUNTERS_PER_POKEMON, LEVEL40));

        Collections.sort(megaCounters);
        Collections.sort(shadowCounters);
        Collections.sort(regularCounters);

        List<RaidCounterPokemon> counterPokemonList = new ArrayList<RaidCounterPokemon>();
        counterPokemonList.addAll(getTopNumberPerList(megaCounters, NUMBER_OF_MEGA_COUNTERS_PER_POKEMON));
        counterPokemonList.addAll(getTopNumberPerList(shadowCounters, NUMBER_OF_SHADOW_COUNTERS_PER_POKEMON));
        counterPokemonList.addAll(getTopNumberPerList(regularCounters, NUMBER_OF_REGULAR_COUNTERS_PER_POKEMON));

        return counterPokemonList;
    }

    private List<RaidCounterPokemon> getTopNumberPerList(List<RaidCounterPokemon> pokemon, int topNumber)
    {
        if (pokemon.size() > topNumber)
        {
            List<RaidCounterPokemon> counters = new ArrayList<RaidCounterPokemon>();
            for(int i = 0; i < topNumber; i++)
            {
                RaidCounterPokemon poke = pokemon.get(i);
                counters.add(poke);
            }
            return counters;
        }
        return pokemon;
    }

    private List<RaidCounterPokemon> getTopMegasFromList(List<String> pokemonStrings, final int MAX, final int LEVEL)
    {
        List<RaidCounterPokemon> topMegas = new ArrayList<RaidCounterPokemon>();

        for (String pokemonString : pokemonStrings)
        {
            RaidCounterPokemon pokemon = createPokemonFromString(pokemonString, LEVEL);
            if (pokemon.isMega())
            {
                if (topMegas.size() < MAX && isAcceptablePokemon(pokemon, topMegas))
                {
                    topMegas.add(pokemon);
                }
            }
            if (topMegas.size() >= MAX)
            {
                break;
            }
        }
        return topMegas;
    }

    private List<RaidCounterPokemon> getTopShadowsFromList(List<String> pokemonStrings, final int MAX, final int LEVEL)
    {
        List<RaidCounterPokemon> topShadows = new ArrayList<RaidCounterPokemon>();

        for (String pokemonString : pokemonStrings)
        {
            RaidCounterPokemon pokemon = createPokemonFromString(pokemonString, LEVEL);
            if (pokemon.isShadow())
            {
                if (topShadows.size() < MAX && isAcceptablePokemon(pokemon, topShadows))
                {
                    topShadows.add(pokemon);
                }
            }
            if (topShadows.size() >= MAX)
            {
                break;
            }
        }
        return topShadows;
    }

    private List<RaidCounterPokemon> getTopRegularsFromList(List<String> pokemonStrings, final int MAX, final int LEVEL)
    {
        List<RaidCounterPokemon> topCounters = new ArrayList<RaidCounterPokemon>();

        for (String pokemonString : pokemonStrings)
        {
            RaidCounterPokemon pokemon = createPokemonFromString(pokemonString, LEVEL);
            if (pokemon.isRegular())
            {
                if (topCounters.size() < MAX && isAcceptablePokemon(pokemon, topCounters))
                {
                    topCounters.add(pokemon);
                }
            }
            if (topCounters.size() >= MAX)
            {
                break;
            }
        }
        return topCounters;
    }

    private RaidCounterPokemon createPokemonFromString(String pokemonString, int level)
    {
        String[] data = pokemonString.replaceAll("\"", "").split(",");

        //Gengar,Lick,Shadow Ball,28.761,301.6,7175.4,2878

        String name = data[0];
        String fastMove = data[1];
        String chargeMove = data[2];

        double dps = Double.parseDouble(data[3]);
        double tdo = Double.parseDouble(data[4]);
        double dps3tdo = Double.parseDouble(data[5]);

        int cp = Integer.parseInt(data[6]);

        RaidCounterPokemon pokemon = new RaidCounterPokemon(name, fastMove, chargeMove, level, dps, tdo, dps3tdo, cp);
        return pokemon;
    }

    private boolean isAcceptablePokemon(RaidCounterPokemon newPokemon, List<RaidCounterPokemon> counterPokemonList)
    {
        boolean isUnique = isUniquePokemon(newPokemon, counterPokemonList);
        boolean isIgnore = isIgnore(newPokemon);
        boolean hasHiddenPower = hasHiddenPower(newPokemon);

        return isUnique & !isIgnore & !hasHiddenPower;
    }

    private static final String GENGAR = "Gengar";
    private static final String LICK = "Lick";
    private static final String SHADOW_CLAW = "Shadow Claw";

    private static final String MEWTWO = "Mewtwo";
    private static final String PSYSTRIKE = "Psystrike";
    private static final String PSYCHIC = "Psychic";

    private boolean isUniquePokemon(RaidCounterPokemon newPokemon, List<RaidCounterPokemon> counterPokemonList)
    {
        for (RaidCounterPokemon pokemon : counterPokemonList)
        {
            String newName = newPokemon.getName();
            String pokemonName = pokemon.getName();

            if (newName.equals(pokemonName))
            {
                if (newName.equals(GENGAR) &&
                        (newPokemon.getFastMove().equals(LICK) ||
                                newPokemon.getFastMove().equals(SHADOW_CLAW)))
                {
                    return true;
                }
                if (newName.equals(MEWTWO))
                {
                    String chargeMove = newPokemon.getChargeMove();

                    if (chargeMove.equals(pokemon.getChargeMove()))
                    {
                        return false;
                    }
                    if (!chargeMove.equals(PSYSTRIKE) && !chargeMove.equals(PSYCHIC))
                    {
                        return false;
                    }
                    if (chargeMove.equals(PSYCHIC) && pokemon.getChargeMove().equals(PSYSTRIKE))
                    {
                        continue;
                    }
                    return true;
                }

                return false;
            }
        }
        return true;
    }


    private boolean isIgnore(RaidCounterPokemon pokemon)
    {
        if (IGNORE_THESE && IGNORE_POKEMON.contains(pokemon.getName()))
        {
            return true;
        }
        return false;
    }

    private static final String HIDDEN_POWER = "Hidden Power";


    private boolean hasHiddenPower(RaidCounterPokemon pokemon)
    {
        return pokemon.getFastMove().contains(HIDDEN_POWER);
    }


    private void createTopCounterPokemon()
    {
        Map<String, RaidCounterPokemon> counters = new HashMap<String, RaidCounterPokemon>();

        for (RaidBoss legendary : getLegendaryPokemon())
        {
            for (RaidCounterPokemon newPokemon : getCounterPokemon(legendary))
            {
                String uniqueId = newPokemon.getUniqueID() ;

                RaidCounterPokemon pokemon = counters.get(uniqueId);
                if(pokemon == null)
                {
                    counters.put(uniqueId, newPokemon);
                }
                else
                {
                    pokemon.incrementCount();
                }
            }
        }

        List<RaidCounterPokemon> counterPokemon = new ArrayList<RaidCounterPokemon>();
        counterPokemon.addAll(counters.values());
        Collections.sort(counterPokemon, new Comparator<RaidCounterPokemon>()
        {
            @Override
            public int compare(RaidCounterPokemon o1, RaidCounterPokemon o2)
            {
                return o2.getCount() - o1.getCount();
            }
        });


        topMegaCounterPokemon.clear();
        topShadowCounterPokemon.clear();
        topRegularCounterPokemon.clear();

        for (RaidCounterPokemon pokemon : counterPokemon)
        {
            if (pokemon.isMega())
            {
                if (topMegaCounterPokemon.size() < NUMBER_OF_TOP_MEGA_COUNTERS_PER_POKEMON)
                {
                    topMegaCounterPokemon.add(pokemon);
                }
            }
            if (pokemon.isShadow())
            {
                if (topShadowCounterPokemon.size() < NUMBER_OF_TOP_SHADOW_COUNTERS_PER_POKEMON)
                {
                    topShadowCounterPokemon.add(pokemon);
                }
            }
            if (pokemon.isRegular())
            {
                if (topRegularCounterPokemon.size() < NUMBER_OF_TOP_REGULAR_COUNTERS_PER_POKEMON)
                {
                    topRegularCounterPokemon.add(pokemon);
                }
            }
        }
    }


    public List<RaidBoss> getLegendaryPokemon()
    {
        if (raidBoss.isEmpty())
        {
            loadCounterData();
        }
        return raidBoss;
    }

    public List<RaidCounterPokemon> getCounterPokemon(RaidBoss legendary)
    {
        String legendaryName = legendary.getId();
        if (counterPokemon.isEmpty())
        {
            loadCounterData();
        }
        return counterPokemon.get(legendaryName);
    }

    public List<RaidCounterPokemon> getTopMegaCounterPokemon()
    {
        if (topMegaCounterPokemon.isEmpty())
        {
            createTopCounterPokemon();
        }
        return topMegaCounterPokemon;
    }

    public List<RaidCounterPokemon> getTopShadowCounterPokemon()
    {
        if (topShadowCounterPokemon.isEmpty())
        {
            createTopCounterPokemon();
        }
        return topShadowCounterPokemon;
    }

    public List<RaidCounterPokemon> getTopRegularCounterPokemon()
    {
        if (topRegularCounterPokemon.isEmpty())
        {
            createTopCounterPokemon();
        }
        return topRegularCounterPokemon;
    }
}
