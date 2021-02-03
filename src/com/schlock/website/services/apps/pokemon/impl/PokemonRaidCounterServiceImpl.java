package com.schlock.website.services.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.RaidCounterPokemon;
import com.schlock.website.entities.apps.pokemon.LegendaryPokemon;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.pokemon.PokemonRaidCounterService;

import java.io.*;
import java.util.*;

/**
 * Data Taken from : https://gamepress.gg/pokemongo/comprehensive-dps-spreadsheet
 *
 */
public class PokemonRaidCounterServiceImpl implements PokemonRaidCounterService
{
    private static final int NUMBER_OF_LINES_TO_READ_FROM_FILE = 100;
    private static final int NUMBER_OF_TOP_UNIQUE_COUNTERS_PER_POKEMON = 20;

    private static final String POKEMON_DIR = "pokemon/raid/";
    private static final String CSV = ".csv";

    private final static boolean IGNORE_THESE = true;

    private final static List<String> IGNORE_POKEMON = Arrays.asList(
            "Shaymin (Sky Forme)",
            "White Kyurem",
            "Ash Greninja",
            "Mega Garchomp",
            "Mega Mewtwo X",
            "Mega Charizard X",
            "Mega Salamence",
            "Mega Lucario",
            "Mega Gardevoir",
            "Mega Alakazam",
            "Mega Heracross",
            "Mega Banette",
            "Mega Pinsir",
            "Mega Gallade");





    private final List<String> current = Arrays.asList(
            "raikou", "suicune", "latias", "latios", "gyarados_mega", "ampharos_mega");

    private final List<String> gen1 = Arrays.asList("mewtwo",
                                                    "mewtwo_armored",
                                                    "articuno",
                                                    "zapdos",
                                                    "moltres");

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
                                                    "meloetta_aria",
                                                    "meloetta_pirouette",
                                                    "genesect");

    private final List<String> gen6 = Arrays.asList("xerneas",
                                                    "yveltal",
                                                    "zygarde",
                                                    "diancie",
                                                    "hoopa_unbound",
                                                    "volcanion");

    private final List<String> mega = Arrays.asList("venusaur_mega",
                                                    "charizard_mega_x",
                                                    "charizard_mega_y",
                                                    "blastoise_mega",
                                                    "alakazam_mega",
                                                    "gengar_mega",
                                                    "kangaskhan_mega",
                                                    "pinsir_mega",
                                                    "gyarados_mega",
                                                    "aerodactyl_mega",
                                                    "mewtwo_mega_x",
                                                    "mewtwo_mega_y",
                                                    "ampharos_mega",
                                                    "scizor_mega",
                                                    "heracross_mega",
                                                    "houndoom_mega",
                                                    "tyranitar_mega",
                                                    "blaziken_mega",
                                                    "gardevoir_mega",
                                                    "mawile_mega",
                                                    "aggron_mega",
                                                    "medicham_mega",
                                                    "manectric_mega",
                                                    "banette_mega",
                                                    "absol_mega",
                                                    "latias_mega",
                                                    "latios_mega",
                                                    "garchomp_mega",
                                                    "lucario_mega",
                                                    "abomasnow_mega",
                                                    "beedrill_mega",
                                                    "pidgeot_mega",
                                                    "slowbro_mega",
                                                    "steelix_mega",
                                                    "sceptile_mega",
                                                    "swampert_mega",
                                                    "sableye_mega",
                                                    "sharpedo_mega",
                                                    "camerupt_mega",
                                                    "altaria_mega",
                                                    "glalie_mega",
                                                    "salamence_mega",
                                                    "metagross_mega",
                                                    "groudon_primal",
                                                    "kyogre_primal",
                                                    "rayquaza_mega",
                                                    "lopunny_mega",
                                                    "gallade_mega",
                                                    "audino_mega",
                                                    "diancie_mega");

    private List<String> legendaryPokemonFilenames = new ArrayList<String>();


    private List<LegendaryPokemon> legendaryPokemon = new ArrayList<LegendaryPokemon>();
    private HashMap<String, List<RaidCounterPokemon>> counterPokemon = new HashMap<String, List<RaidCounterPokemon>>();


    private final DeploymentContext deploymentContext;


    public PokemonRaidCounterServiceImpl(DeploymentContext deploymentContext)
    {
        this.deploymentContext = deploymentContext;

        legendaryPokemonFilenames.clear();
        legendaryPokemonFilenames.addAll(current);
        legendaryPokemonFilenames.addAll(gen1);
        legendaryPokemonFilenames.addAll(gen2);
        legendaryPokemonFilenames.addAll(gen3);
        legendaryPokemonFilenames.addAll(gen4);
        legendaryPokemonFilenames.addAll(gen5);
        legendaryPokemonFilenames.addAll(gen6);
        legendaryPokemonFilenames.addAll(mega);

        loadPokemonData();
    }


    private void loadPokemonData()
    {
        for (String pokemonName : legendaryPokemonFilenames)
        {
            LegendaryPokemon pokemon = new LegendaryPokemon(pokemonName);
            legendaryPokemon.add(pokemon);

            List<RaidCounterPokemon> counterList = loadCounterList(pokemonName);
            counterPokemon.put(pokemonName, counterList);
        }
    }

    private List<RaidCounterPokemon> loadCounterList(String pokemonName)
    {
        List<String> linesFromFile = new ArrayList<String>();
        try
        {
            linesFromFile = readLinesFromFile(pokemonName);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        List<RaidCounterPokemon> counterList = parseStringsIntoList(linesFromFile);
        return counterList;
    }

    private List<String> readLinesFromFile(String pokemonFile) throws IOException
    {
        String fileLocation = deploymentContext.webDirectory() + POKEMON_DIR + pokemonFile + CSV;
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

    private List<RaidCounterPokemon> parseStringsIntoList(List<String> pokemonStrings)
    {
        List<RaidCounterPokemon> counterPokemonList = new ArrayList<RaidCounterPokemon>();

        for (String pokemonString : pokemonStrings)
        {
            pokemonString = pokemonString.replaceAll("\"", "");

            String[] strings = pokemonString.split(",");
            RaidCounterPokemon pokemon = createPokemonFromString(strings);

            if (isAcceptablePokemon(pokemon, counterPokemonList))
            {
                counterPokemonList.add(pokemon);
            }
            if (counterPokemonList.size() > NUMBER_OF_TOP_UNIQUE_COUNTERS_PER_POKEMON)
            {
                return counterPokemonList;
            }
        }
        return counterPokemonList;
    }


    private RaidCounterPokemon createPokemonFromString(String[] data)
    {
        //Gengar,Lick,Shadow Ball,28.761,301.6,7175.4,2878

        String name = data[0];
        String fastMove = data[1];
        String chargeMove = data[2];

        double dps = Double.parseDouble(data[3]);
        double tdo = Double.parseDouble(data[4]);
        double dps3tdo = Double.parseDouble(data[5]);

        int cp = Integer.parseInt(data[6]);

        RaidCounterPokemon pokemon = new RaidCounterPokemon(name, fastMove, chargeMove, dps, tdo, dps3tdo, cp);
        return pokemon;
    }

    private boolean isAcceptablePokemon(RaidCounterPokemon newPokemon, List<RaidCounterPokemon> counterPokemonList)
    {
        boolean isUnique = isUniquePokemon(newPokemon, counterPokemonList);
        boolean isIgnore = isIgnore(newPokemon);

        return isUnique & !isIgnore;
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


    public List<LegendaryPokemon> getLegendaryPokemon()
    {
        if (legendaryPokemon.isEmpty())
        {
            loadPokemonData();
        }
        return legendaryPokemon;
    }

    public List<RaidCounterPokemon> getCounterPokemon(LegendaryPokemon legendary)
    {
        String legendaryName = legendary.getName();
        if (counterPokemon.isEmpty())
        {
            loadPokemonData();
        }
        return counterPokemon.get(legendaryName);
    }
}
