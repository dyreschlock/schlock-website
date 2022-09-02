package com.schlock.website.services.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.*;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.pokemon.PokemonDataService;
import com.schlock.website.services.apps.pokemon.PokemonRocketCounterService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Find the information at : https://www.pokebattler.com/rocket
 *
 * Change the parameter :
 * https://www.pokebattler.com/rocket/defenders/MOLTRES_SHADOW_FORM/levels/RAID_LEVEL_3/attackers/levels/40?sort=POWER&view=GROUPED&shieldStrategy=SHIELD_0&defenderShieldStrategy=SHIELD_0&meta=DUAL_MOVE
 */
public class PokemonRocketCounterServiceImpl implements PokemonRocketCounterService
{
    private static final String POKEMON_DIR = "pokemon/rocket/";
    private static final String HTML = ".html";


    private Map<String, List<RocketCounterInstance>> counterPokemon = new HashMap<String, List<RocketCounterInstance>>();

    private final PokemonDataService dataService;
    private final DeploymentContext deploymentContext;

    public PokemonRocketCounterServiceImpl(PokemonDataService dataService,
                                           DeploymentContext deploymentContext)
    {
        this.dataService = dataService;
        this.deploymentContext = deploymentContext;
    }


    public List<RocketLeader> getRocketLeaders()
    {
        return dataService.getRocketLeaders();
    }

    public List<RocketCounterInstance> getCounterPokemon(CounterType counterType, RocketBossPokemon pokemon)
    {
        if (pokemon.getCounters(counterType) == null || pokemon.getCounters(counterType).isEmpty())
        {
            generateCounters(counterType, pokemon);
        }
        return pokemon.getCounters(counterType);
    }


    private String mapKey(CounterType counterType, RocketBossPokemon pokemon)
    {
        return counterType.name() + pokemon.getName();
    }

    private void generateCounters(CounterType counterType, RocketBossPokemon pokemon)
    {
        String key = mapKey(counterType, pokemon);
        if (!counterPokemon.containsKey(key))
        {
            try
            {
                List<RocketCounterInstance> list = createCounters(counterType, pokemon);
                counterPokemon.put(key, list);
            }
            catch(Exception e)
            {
                System.out.println(pokemon.getName());
            }
        }
        pokemon.setCounters(counterType, counterPokemon.get(key));
    }

    private List<RocketCounterInstance> createCounters(CounterType counterType, RocketBossPokemon pokemon)
    {
        String rocketPokemonName = pokemon.getName().toLowerCase();
        String fileLocation = deploymentContext.webDirectory() + POKEMON_DIR + rocketPokemonName + HTML;

        Document htmlFile = null;
        try
        {
            htmlFile = Jsoup.parse(new File(fileLocation), "ISO-8859-1");
        } catch (IOException e)
        {
            e.printStackTrace();
            System.out.println(fileLocation);
        }


        List<RocketCounterInstance> counters = new ArrayList<RocketCounterInstance>();

        for (int i = 0; i < 30; i++)
        {
            String rocketId = "ROCKET" + (i + 1);

            Element counterBody = htmlFile.getElementById(rocketId);
            if(counterBody == null)
            {
                String stop = "";
            }

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


            RocketCounterInstance counter = new RocketCounterInstance(name, fastMove, chargeMove, overall, cp, time, power);
            counters.add(counter);
        }
        return counters;
    }
}
