package com.schlock.website.services.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.RocketCounterPokemon;
import com.schlock.website.entities.apps.pokemon.RocketLeader;
import com.schlock.website.entities.apps.pokemon.RocketPokemon;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.pokemon.PokemonRocketCounterService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PokemonRocketCounterServiceImpl implements PokemonRocketCounterService
{
    private static final String POKEMON_DIR = "pokemon/rocket/";
    private static final String HTML = ".html";

    private static final String[] ARLO2 = {"arlo2", "dec2019", "bagon", "blastoise", "charizard", "steelix", "dragonite", "salamence", "scizor"};
    private static final String[] ARLO1 = {"arlo1", "oct2019", "scyther", "crobat", "gyarados", "magnezone", "charizard", "dragonite", "scizor"};

    private static final String[] CLIFF2 = {"cliff2", "dec2019", "stantler", "electivire", "marowak", "onix", "swampert", "torterra", "tyranitar"};
    private static final String[] CLIFF1 = {"cliff1", "oct2019", "meowth", "flygon", "sandslash", "snorlax", "infernape", "torterra", "tyranitar"};

    private static final String[] SIERRA2 = {"sierra2", "dec2019", "absol", "alakazam", "cacturne", "lapras", "gallade", "houndoom", "shiftry"};
    private static final String[] SIERRA1 = {"sierra1", "oct2019", "sneasel", "hypno", "lapras", "sableye", "alakazam", "gardevoir", "houndoom"};

    //    private static final String[] GIOVANNI3 = {"giovanni3", "feb2020", "persian", "cloyster", "hippowdon", "steelix", "raikou"};
    private static final String[] GIOVANNI2 = {"giovanni2", "dec2019", "persian", "cloyster", "garchomp", "kangaskhan", "moltres"};
    private static final String[] GIOVANNI1 = {"giovanni1", "oct2019", "persian", "dugtrio", "hippowdon", "rhydon", "articuno", "zapdos"};

    private static final String[] EXTRA = {"others", "extra", "lapras", "poliwrath", "snorlax", "gyarados", "gardevoir", "dragonite", "venusaur"};


    private static final List<String[]> ROCKET_LEADERS = Arrays.asList(ARLO2, CLIFF2, SIERRA2, GIOVANNI2, EXTRA, ARLO1, CLIFF1, SIERRA1, GIOVANNI1);


    private List<String> leaderGroups = new ArrayList<String>();

    private Map<String, List<RocketLeader>> rocketLeaders = new HashMap<String, List<RocketLeader>>();
    private Map<String, List<RocketCounterPokemon>> counterPokemon = new HashMap<String, List<RocketCounterPokemon>>();


    private final DeploymentContext deploymentContext;

    public PokemonRocketCounterServiceImpl(DeploymentContext deploymentContext)
    {
        this.deploymentContext = deploymentContext;

        createLeaders();
        loadAndCreateCounters();
    }

    private void createLeaders()
    {
        leaderGroups.clear();
        rocketLeaders.clear();
        for (String[] leaderData : ROCKET_LEADERS)
        {
            String name = leaderData[0];
            RocketLeader leader = new RocketLeader(name);

            String group = leaderData[1];

            for (int i = 2; i < leaderData.length; i++)
            {
                String pokemonName = leaderData[i];

                RocketPokemon pokemon = new RocketPokemon(pokemonName);
                leader.addPokemon(pokemon);
            }

            if (!leaderGroups.contains(group))
            {
                leaderGroups.add(group);

                List<RocketLeader> leaders = new ArrayList<RocketLeader>();
                leaders.add(leader);

                rocketLeaders.put(group, leaders);
            }
            else
            {
                rocketLeaders.get(group).add(leader);
            }
        }
    }

    private void loadAndCreateCounters()
    {
        counterPokemon.clear();
        for (String group : leaderGroups)
        {
            for (RocketLeader leader : rocketLeaders.get(group))
            {
                for (RocketPokemon rocketPokemon : leader.getPokemon())
                {
                    String name = rocketPokemon.getName();
                    if (!counterPokemon.containsKey(name))
                    {
                        List<RocketCounterPokemon> list = createCounters(name);
                        counterPokemon.put(name, list);
                    }
                }
            }
        }
    }

    private List<RocketCounterPokemon> createCounters(String rocketPokemonName)
    {
        String fileLocation = deploymentContext.webDirectory() + POKEMON_DIR + rocketPokemonName + HTML;

        Document htmlFile = null;
        try
        {
            htmlFile = Jsoup.parse(new File(fileLocation), "ISO-8859-1");
        } catch (IOException e)
        {
            e.printStackTrace();
        }


        List<RocketCounterPokemon> counters = new ArrayList<RocketCounterPokemon>();

        for (int i = 0; i < 30; i++)
        {
            String rocketId = "ROCKET" + (i + 1);

            Element counterBody = htmlFile.getElementById(rocketId);

            /*  data element structure
            <div (data)>
                <div>
                    <div><label>Overall</label> XX </div>
                    <div><label>CP</label> XX </div>
                </div>
                <div>
                    <div><label>Time</label> XX </div>
                    <div>
                        <div><label>Power</label> XX </div>
                    </div>
                </div>
            </div>
             */
            Element dataElement = counterBody.child(1);

            Element overallAndCP = dataElement.child(0);
            String overall = overallAndCP.child(0).textNodes().get(0).text();
            String cp = overallAndCP.child(1).textNodes().get(0).text();

            Element timePower = dataElement.child(1);
            String time = timePower.child(0).textNodes().get(0).text();
            String power = timePower.child(1).child(0).textNodes().get(0).text();


            /* details element structure
            <div (details)>
                <div>
                    <div>
                        <div> XX <img><img></div>
                    </div>

                    <div>
                        <div>
                            <div><img> XX </div>
                            <div><img> XX </div>
                        </div>
                    </div>
                </div>
            </div>
             */
            Element detailsElement = counterBody.child(3);
            String name = detailsElement.child(0).child(0).child(0).textNodes().get(0).text();

            Element moves = detailsElement.child(0).child(1).child(0);

            String fastMove = moves.child(0).textNodes().get(0).text();
            String chargeMove = moves.child(1).textNodes().get(0).text();


            RocketCounterPokemon counter = new RocketCounterPokemon(name, fastMove, chargeMove, overall, cp, time, power);
            counters.add(counter);
        }
        return counters;
    }


    public List<RocketLeader> getRocketLeaders()
    {
        List<RocketLeader> leaders = new ArrayList<RocketLeader>();
        for (String group : leaderGroups)
        {
            leaders.addAll(rocketLeaders.get(group));
        }
        return leaders;
    }

    public List<RocketCounterPokemon> getCounterPokemon(String rocketPokemonName)
    {
        return counterPokemon.get(rocketPokemonName);
    }
}
