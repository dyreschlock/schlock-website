package com.schlock.website.services.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.RaidCounterPokemon;
import com.schlock.website.entities.apps.pokemon.LegendaryPokemon;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.pokemon.PokemonRaidCounterService;

import java.io.*;
import java.util.*;

public class PokemonRaidCounterServiceImpl implements PokemonRaidCounterService
{
    private static final int NUMBER_OF_LINES_TO_READ_FROM_FILE = 50;
    private static final int NUMBER_OF_TOP_UNIQUE_COUNTERS_PER_POKEMON = 10;

    private static final String POKEMON_DIR = "pokemon/raid/";
    private static final String CSV = ".csv";

    private List<String> gen1 = Arrays.asList("mewtwo",
                                                "mewtwo_armored",
                                                "articuno",
                                                "zapdos",
                                                "moltres");

    private List<String> gen2 = Arrays.asList("raikou",
                                                "entei",
                                                "suicune",
                                                "lugia",
                                                "ho-oh");

    private List<String> gen3 = Arrays.asList("regirock",
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

    private List<String> gen4 = Arrays.asList("uxie",
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

    private List<String> gen5 = Arrays.asList("cobalion",
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

    private List<String> legendaryPokemonFilenames = new ArrayList<String>();


    private List<LegendaryPokemon> legendaryPokemon = new ArrayList<LegendaryPokemon>();
    private HashMap<String, List<RaidCounterPokemon>> counterPokemon = new HashMap<String, List<RaidCounterPokemon>>();


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
        boolean isReleased = isReleased(newPokemon);

        return isUnique & isReleased;
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


    private final static List<String> UNRELEASED_POKEMON = Arrays.asList("Black Kyurem",
            "White Kyurem",
            "Landorus (Therian Forme)",
            "Vanilluxe");


    private boolean isReleased(RaidCounterPokemon pokemon)
    {
//        if (UNRELEASED_POKEMON.contains(pokemon.getName()))
//        {
//            return false;
//        }
        return true;
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
