package com.schlock.website.services.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.RaidCounterPokemon;
import com.schlock.website.entities.apps.pokemon.RaidBoss;
import com.schlock.website.entities.apps.pokemon.RaidMove;
import com.schlock.website.entities.apps.pokemon.RaidPokemonData;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.pokemon.PokemonRaidCounterCalculationService;
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


    private static final int NUMBER_OF_TOP_MEGA_COUNTERS_PER_POKEMON = 10;
    private static final int NUMBER_OF_TOP_SHADOW_COUNTERS_PER_POKEMON = 20;
    private static final int NUMBER_OF_TOP_REGULAR_COUNTERS_PER_POKEMON = 32;

    private static final int NUMBER_OF_MEGA_COUNTERS_PER_POKEMON = 4;
    private static final int NUMBER_OF_SHADOW_COUNTERS_PER_POKEMON = 8;
    private static final int NUMBER_OF_REGULAR_COUNTERS_PER_POKEMON = 20;


    private static final String POKEMON_DIR = "pokemon/raid/";

    private final static boolean IGNORE_THESE = true;
//    private final static boolean IGNORE_THESE = false;

    private final static List<String> IGNORE_MOVE = Arrays.asList(
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

    private List<String> raidBossNames = new ArrayList<String>();

    private HashMap<String, RaidBoss> raidBossData = new HashMap<String, RaidBoss>();
    private HashMap<String, RaidMove> raidMoveData = new HashMap<String, RaidMove>();
    private HashMap<String, RaidPokemonData> raidPokemonData = new HashMap<String, RaidPokemonData>();

    private List<RaidCounterPokemon> topMegaCounterPokemon = new ArrayList<RaidCounterPokemon>();
    private List<RaidCounterPokemon> topShadowCounterPokemon = new ArrayList<RaidCounterPokemon>();
    private List<RaidCounterPokemon> topRegularCounterPokemon = new ArrayList<RaidCounterPokemon>();

    private final PokemonRaidCounterCalculationService calculationService;

    private final DeploymentContext deploymentContext;


    public PokemonRaidCounterServiceImpl(PokemonRaidCounterCalculationService calculationService,
                                            DeploymentContext deploymentContext)
    {
        this.calculationService = calculationService;
        this.deploymentContext = deploymentContext;

        raidBossNames.clear();
        raidBossNames.addAll(GEN1_BOSSES);
        raidBossNames.addAll(GEN2_BOSSES);
        raidBossNames.addAll(GEN3_BOSSES);
        raidBossNames.addAll(GEN4_BOSSES);
        raidBossNames.addAll(GEN5_BOSSES);
        raidBossNames.addAll(GEN6_BOSSES);
        raidBossNames.addAll(GEN7_BOSSES);
        raidBossNames.addAll(GEN8_BOSSES);
        raidBossNames.addAll(MEGA_BOSSES);

        loadRaidMoveJSON();
        loadRaidPokemonDataJSON();

        generateRaidCounterData();
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



    private void generateRaidCounterData()
    {
        for (String name : raidBossNames)
        {
            RaidBoss raidBoss = raidBossData.get(name);
            if (raidBoss == null)
            {
                RaidPokemonData data = raidPokemonData.get(name);
                if (data == null)
                {
                    throw new RuntimeException("Pokemon Not Found: " + name);
                }

                raidBoss = RaidBoss.createFromData(data);
                raidBossData.put(raidBoss.getName(), raidBoss);
            }

            if (!raidBoss.isCountersGenerated())
            {
                generateRaidCounters(raidBoss);
            }
        }

        generateTopCounters();
    }

    private void generateRaidCounters(RaidBoss raidBoss)
    {
        for (RaidPokemonData pokemonData : raidPokemonData.values())
        {
            if (!isIgnorePokemon(pokemonData.getName()))
            {
                List<RaidCounterPokemon> counters = new ArrayList<RaidCounterPokemon>();

                counters.addAll(generateRaidCounters(raidBoss, pokemonData, 40));
                counters.addAll(generateRaidCounters(raidBoss, pokemonData, 50));

                if (pokemonData.isMega())
                {
                    raidBoss.getMegaCounters().addAll(counters);
                }
                else if (pokemonData.isShadow())
                {
                    raidBoss.getShadowCounters().addAll(counters);
                }
                else
                {
                    raidBoss.getRegularCounters().addAll(counters);
                }
            }
        }

        List<RaidCounterPokemon> megaCounters;
        megaCounters = filterListForTopCounters(NUMBER_OF_MEGA_COUNTERS_PER_POKEMON, raidBoss.getMegaCounters());
        raidBoss.setMegaCounters(megaCounters);

        List<RaidCounterPokemon> shadowCounters;
        shadowCounters = filterListForTopCounters(NUMBER_OF_SHADOW_COUNTERS_PER_POKEMON, raidBoss.getShadowCounters());
        raidBoss.setShadowCounters(shadowCounters);

        List<RaidCounterPokemon> regularCounters;
        regularCounters = filterListForTopCounters(NUMBER_OF_REGULAR_COUNTERS_PER_POKEMON, raidBoss.getRegularCounters());
        raidBoss.setRegularCounters(regularCounters);
    }

    private List<RaidCounterPokemon> filterListForTopCounters(int numberOfCounters, List<RaidCounterPokemon> counterList)
    {
        List<RaidCounterPokemon> allCounters = new ArrayList<RaidCounterPokemon>();
        allCounters.addAll(counterList);

        Collections.sort(allCounters, new RaidCounterComparator());

        List<RaidCounterPokemon> topCounters = new ArrayList<RaidCounterPokemon>();
        for(int i = 0; i < numberOfCounters; i++)
        {
            RaidCounterPokemon pokemon = allCounters.get(i);
            topCounters.add(pokemon);
        }
        return topCounters;
    }

    private List<RaidCounterPokemon> generateRaidCounters(RaidBoss raidBoss, RaidPokemonData pokemonData, int level)
    {
        List<RaidCounterPokemon> counters = new ArrayList<RaidCounterPokemon>();

        for (RaidMove fastMove : pokemonData.getAllFastMoves())
        {
            for (RaidMove chargeMove : pokemonData.getAllChargeMoves())
            {
                RaidCounterPokemon counter = calculationService.generateRaidCounter(raidBoss, pokemonData, fastMove, chargeMove, level);
                if (counter != null)
                {
                    counters.add(counter);
                }
            }
        }
        return filterListForBest(counters);
    }


    private static final String GENGAR = "Gengar";
    private static final String LICK = "Lick";
    private static final String SHADOW_CLAW = "Shadow Claw";

    private static final String MEWTWO = "Mewtwo";
    private static final String PSYSTRIKE = "Psystrike";
    private static final String PSYCHIC = "Psychic";

    private List<RaidCounterPokemon> filterListForBest(List<RaidCounterPokemon> counterList)
    {
        List<RaidCounterPokemon> allCounters = new ArrayList<RaidCounterPokemon>();
        allCounters.addAll(counterList);

        Collections.sort(allCounters, new RaidCounterComparator());

        List<RaidCounterPokemon> uniqueCounters = new ArrayList<RaidCounterPokemon>();

        String topFast = "";
        String topCharge = "";

        for (int i = 0; i < allCounters.size(); i++)
        {
            RaidCounterPokemon counter = allCounters.get(i);
            if (i == 0)
            {
                uniqueCounters.add(counter);

                topFast = counter.getFastMove();
                topCharge = counter.getChargeMove();
            }
            else
            {
                if (GENGAR.equalsIgnoreCase(counter.getName()))
                {
                    if (LICK.equalsIgnoreCase(topFast) && SHADOW_CLAW.equalsIgnoreCase(counter.getFastMove()))
                    {
                        uniqueCounters.add(counter);
                    }
                    if (SHADOW_CLAW.equalsIgnoreCase(topFast) && LICK.equalsIgnoreCase(counter.getFastMove()))
                    {
                        uniqueCounters.add(counter);
                    }
                }

                if (MEWTWO.equalsIgnoreCase(counter.getName()))
                {
                    if (PSYSTRIKE.equals(topCharge) && PSYCHIC.equalsIgnoreCase(counter.getChargeMove()))
                    {
                        uniqueCounters.add(counter);
                    }
                    if (PSYCHIC.equalsIgnoreCase(topCharge) && PSYSTRIKE.equalsIgnoreCase(counter.getChargeMove()))
                    {
                        uniqueCounters.add(counter);
                    }
                }
            }
        }
        return uniqueCounters;
    }

    private boolean isIgnorePokemon(String name)
    {
        if (IGNORE_THESE && IGNORE_POKEMON.contains(name))
        {
            return true;
        }
        return false;
    }

    private boolean isIgnoreMove(String name)
    {
        return IGNORE_MOVE.contains(name);
    }

    private void generateTopCounters()
    {
        if (!topRegularCounterPokemon.isEmpty() &&
                !topMegaCounterPokemon.isEmpty() &&
                !topShadowCounterPokemon.isEmpty())
        {
            return;
        }

        Map<String, RaidCounterPokemon> counters = new HashMap<String, RaidCounterPokemon>();

        for (RaidBoss boss : getLegendaryPokemon())
        {
            if (!boss.isCountersGenerated())
            {
                generateRaidCounters(boss);
            }

            List<RaidCounterPokemon> allCounters = new ArrayList<RaidCounterPokemon>();
            allCounters.addAll(boss.getRegularCounters());
            allCounters.addAll(boss.getMegaCounters());
            allCounters.addAll(boss.getShadowCounters());

            for (RaidCounterPokemon currentCounter : allCounters)
            {
                String uniqueId = currentCounter.getUniqueID();

                RaidCounterPokemon pokemon = counters.get(uniqueId);
                if(pokemon == null)
                {
                    counters.put(uniqueId, currentCounter);
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
        if (raidBossData.isEmpty())
        {
            generateRaidCounterData();
        }

        List<RaidBoss> bosses = new ArrayList<RaidBoss>();
        for (String bossName : raidBossNames)
        {
            bosses.add(raidBossData.get(bossName));
        }
        return bosses;
    }

    public List<RaidCounterPokemon> getCounterPokemon(RaidBoss boss)
    {
        if (!boss.isCountersGenerated())
        {
            generateRaidCounters(boss);
        }

        List<RaidCounterPokemon> counters = new ArrayList<RaidCounterPokemon>();

        counters.addAll(boss.getMegaCounters());
        counters.addAll(boss.getShadowCounters());
        counters.addAll(boss.getRegularCounters());

        return counters;
    }

    public List<RaidCounterPokemon> getTopMegaCounterPokemon()
    {
        if (topMegaCounterPokemon.isEmpty())
        {
            generateTopCounters();
        }
        return topMegaCounterPokemon;
    }

    public List<RaidCounterPokemon> getTopShadowCounterPokemon()
    {
        if (topShadowCounterPokemon.isEmpty())
        {
            generateTopCounters();
        }
        return topShadowCounterPokemon;
    }

    public List<RaidCounterPokemon> getTopRegularCounterPokemon()
    {
        if (topRegularCounterPokemon.isEmpty())
        {
            generateTopCounters();
        }
        return topRegularCounterPokemon;
    }


    private class RaidCounterComparator implements Comparator<RaidCounterPokemon>
    {
        public int compare(RaidCounterPokemon o1, RaidCounterPokemon o2)
        {
            if (o2.getDps4tdo() > o1.getDps4tdo())
            {
                return 1;
            }
            if (o2.getDps4tdo() < o1.getDps4tdo())
            {
                return -1;
            }
            return 0;
        }
    }
}
