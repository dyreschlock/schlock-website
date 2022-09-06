package com.schlock.website.services.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.*;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.pokemon.PokemonCounterCalculationService;
import com.schlock.website.services.apps.pokemon.PokemonDataService;
import com.schlock.website.services.apps.pokemon.PokemonRocketCounterService;

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

    private final PokemonCounterCalculationService calculationService;
    private final PokemonDataService dataService;

    private final DeploymentContext deploymentContext;

    public PokemonRocketCounterServiceImpl(PokemonCounterCalculationService calculationService,
                                           PokemonDataService dataService,
                                           DeploymentContext deploymentContext)
    {
        this.calculationService = calculationService;
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
            List<RocketCounterInstance> list = createCounters(pokemon, counterType);
            counterPokemon.put(key, list);
        }
        pokemon.setCounters(counterType, counterPokemon.get(key));
    }

    private List<RocketCounterInstance> createCounters(RocketBossPokemon pokemon, CounterType counterType)
    {
        List<RocketCounterInstance> counters = new ArrayList<RocketCounterInstance>();
        for(CounterPokemon counter : dataService.getCounterPokemon(counterType, BattleMode.ROCKET))
        {
            RocketCounterInstance counterInst = generateCounter(pokemon, counter);
            if (counterInst != null)
            {
                counters.add(counterInst);
            }
        }

        if(counterType.isCustom())
        {
            counters = filterUniques(counters);
        }

        Collections.sort(counters);

        pokemon.setCounters(counterType, counters.subList(0, counterType.counterListSize()));

        return pokemon.getCounters(counterType);
    }

    private List<RocketCounterInstance> filterUniques(List<RocketCounterInstance> totalCounters)
    {
        List<RocketCounterInstance> counters = new ArrayList<RocketCounterInstance>();
        List<String> counterNames = new ArrayList<String>();

        for (RocketCounterInstance counter : totalCounters)
        {
            String name = counter.getName();
            if (!counterNames.contains(name))
            {
                counters.add(counter);
                counterNames.add(name);
            }
        }
        return counters;
    }

    private RocketCounterInstance generateCounter(RocketBossPokemon rocketBoss, CounterPokemon counterPokemon)
    {
        List<RocketCounterInstance> counters = new ArrayList<RocketCounterInstance>();

        for (PokemonMove fastMove : counterPokemon.getAllFastMoves())
        {
            for (PokemonMove chargeMove : counterPokemon.getAllChargeMoves())
            {
                RocketCounterInstance counter = calculationService.generateRocketCounter(rocketBoss, counterPokemon, fastMove, chargeMove);
                if (counter != null)
                {
                    counters.add(counter);
                }
            }
        }
        Collections.sort(counters);

        if (!counters.isEmpty())
        {
            return counters.get(0);
        }
        return null;
    }
}
