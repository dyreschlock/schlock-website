package com.schlock.website.services.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.*;
import com.schlock.website.services.apps.pokemon.*;
import com.schlock.website.services.database.apps.pokemon.PokemonDataDAO;
import com.schlock.website.services.database.apps.pokemon.PokemonMoveDAO;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class PokemonDataServiceImpl implements PokemonDataService
{
    private static final boolean IGNORE_MOVES_POKEMON = true;
//    private static final boolean IGNORE_MOVES_POKEMON = false;

    private static final int LEVEL_40 = 40;
    private static final int LEVEL_45 = 45;
    private static final int LEVEL_50 = 50;

    private final static List<String> IGNORE_MOVES = Arrays.asList(
            "Hidden Power"
    );

    private static final String ARCEUS = "Arceus";


    private List<RocketLeader> rocketLeaders = new ArrayList<RocketLeader>();
    private Map<String, RocketBossPokemon> rocketBosses = new HashMap<String, RocketBossPokemon>();

    private List<RaidBossWithAttackingType> raidBossesWithAttackingTypes = new ArrayList<RaidBossWithAttackingType>();

    private HashMap<Integer, Double> cpmData = new HashMap<Integer, Double>();


    private final PokemonCustomCounterPrimeService customPrimeService;
    private final PokemonCustomCounterSecondService customSecondService;
    private final PokemonCounterCalculationService calculationService;

    private final PokemonDataGamepressService gamepressService;

    private final PokemonDataDAO dataDAO;
    private final PokemonMoveDAO moveDAO;

    public PokemonDataServiceImpl(PokemonCustomCounterPrimeService customPrimeService,
                                  PokemonCustomCounterSecondService customSecondService,
                                  PokemonCounterCalculationService calculationService,
                                  PokemonDataGamepressService gamepressService,
                                  PokemonDataDAO dataDAO,
                                  PokemonMoveDAO moveDAO)
    {
        this.customPrimeService = customPrimeService;
        this.customSecondService = customSecondService;
        this.calculationService = calculationService;
        this.gamepressService = gamepressService;

        this.dataDAO = dataDAO;
        this.moveDAO = moveDAO;
    }

    private void loadCpmData()
    {
        if (!cpmData.isEmpty())
        {
            return;
        }

        cpmData = gamepressService.getCpmData();
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
        if (cpmData.isEmpty())
        {
            loadCpmData();
        }
        return cpmData.get(level);
    }

    public PokemonMove getMoveByName(String name)
    {
        return moveDAO.getByName(name);
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
        List<CounterPokemon> pokemon = new ArrayList<CounterPokemon>();

        for (PokemonData data : dataDAO.getAll())
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
        List<PokemonData> bosses = dataDAO.getRaidBosses();

        Collections.sort(bosses, new Comparator<PokemonData>()
        {
            final int O1_FIRST = -1;
            final int O2_FIRST = 1;

            public int compare(PokemonData o1, PokemonData o2)
            {
                if (o1.isLegendary() && !o2.isLegendary())
                {
                    return O1_FIRST;
                }
                if (!o1.isLegendary() && o2.isLegendary())
                {
                    return O2_FIRST;
                }

                int n1 = o1.getSanitizedNumber();
                int n2 = o2.getSanitizedNumber();

                int compare = n1 - n2;
                if (compare != 0)
                {
                    return compare;
                }

                String num1 = o1.getNumber();
                String num2 = o2.getNumber();

                if (StringUtils.equals(num1, num2))
                {
                    return O1_FIRST;
                }

                if (StringUtils.isNumeric(num1))
                {
                    return O1_FIRST;
                }
                return O2_FIRST;
            }
        });

        List<RaidBossPokemon> raidBosses = new ArrayList<RaidBossPokemon>();
        for(PokemonData boss : bosses)
        {
            raidBosses.add(RaidBossPokemon.createFromData(boss));
        }
        return raidBosses;
    }

    public PokemonData getDataByName(String name)
    {
        PokemonData data = dataDAO.getByName(name);
        if (data == null)
        {
            throw new RuntimeException("Pokemon Not Found: " + name);
        }
        return data;
    }

    private void generateRaidBossWithAttackingType()
    {
        if (!raidBossesWithAttackingTypes.isEmpty())
        {
            return;
        }

        PokemonData arceus = getDataByName(ARCEUS);

        Map<String, String> attackingToDefendingTypes = calculationService.getMapOfAttackingTypeToWeakType();
        for (PokemonType attackingType : PokemonType.preferredTypeOrder())
        {
            String defendingType = attackingToDefendingTypes.get(attackingType.text());

            RaidBossWithAttackingType typedArceus = RaidBossWithAttackingType.createFromData(arceus, defendingType, attackingType.text());

            raidBossesWithAttackingTypes.add(typedArceus);
        }
    }

    private void generateRocketLeaders()
    {
        if (!rocketLeaders.isEmpty())
        {
            return;
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
}
