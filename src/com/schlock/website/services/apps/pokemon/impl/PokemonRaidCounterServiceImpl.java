package com.schlock.website.services.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.*;
import com.schlock.website.services.apps.pokemon.PokemonRaidCounterCalculationService;
import com.schlock.website.services.apps.pokemon.PokemonRaidCounterService;
import com.schlock.website.services.apps.pokemon.PokemonRaidDataService;

import java.util.*;

public class PokemonRaidCounterServiceImpl implements PokemonRaidCounterService
{
    private static final int NUMBER_OF_TOP_MEGA_COUNTERS_PER_POKEMON = 10;
    private static final int NUMBER_OF_TOP_SHADOW_COUNTERS_PER_POKEMON = 20;
    private static final int NUMBER_OF_TOP_REGULAR_COUNTERS_PER_POKEMON = 32;

    private static final int NUMBER_OF_MEGA_COUNTERS_PER_POKEMON = 4;
    private static final int NUMBER_OF_SHADOW_COUNTERS_PER_POKEMON = 8;
    private static final int NUMBER_OF_REGULAR_COUNTERS_PER_POKEMON = 20;


    private List<RaidCounterInstance> topMegaCounterPokemon = new ArrayList<RaidCounterInstance>();
    private List<RaidCounterInstance> topShadowCounterPokemon = new ArrayList<RaidCounterInstance>();
    private List<RaidCounterInstance> topRegularCounterPokemon = new ArrayList<RaidCounterInstance>();

    private final PokemonRaidCounterCalculationService calculationService;
    private final PokemonRaidDataService dataService;


    public PokemonRaidCounterServiceImpl(PokemonRaidCounterCalculationService calculationService,
                                            PokemonRaidDataService dataService)
    {
        this.calculationService = calculationService;
        this.dataService = dataService;
    }

    private void generateRaidCounters(RaidBoss raidBoss, RaidCounterType counterType)
    {
        List<RaidCounterInstance> megaCounters = new ArrayList<RaidCounterInstance>();
        List<RaidCounterInstance> shadowCounters = new ArrayList<RaidCounterInstance>();
        List<RaidCounterInstance> regularCounters = new ArrayList<RaidCounterInstance>();

        for (RaidPokemonData pokemonData : dataService.getSuitableCounterPokemon(counterType))
        {
            List<RaidCounterInstance> counters = new ArrayList<RaidCounterInstance>();

            counters.addAll(generateRaidCounters(raidBoss, pokemonData, 40));
            counters.addAll(generateRaidCounters(raidBoss, pokemonData, 50));

            if (pokemonData.isMega())
            {
                megaCounters.addAll(counters);
            }
            else if (pokemonData.isShadow())
            {
                shadowCounters.addAll(counters);
            }
            else
            {
                regularCounters.addAll(counters);
            }
        }

        Collections.sort(megaCounters, new RaidCounterComparator());
        Collections.sort(shadowCounters, new RaidCounterComparator());
        Collections.sort(regularCounters, new RaidCounterComparator());

        raidBoss.setMegaCounters(megaCounters.subList(0, NUMBER_OF_MEGA_COUNTERS_PER_POKEMON));
        raidBoss.setShadowCounters(shadowCounters.subList(0, NUMBER_OF_SHADOW_COUNTERS_PER_POKEMON));
        raidBoss.setRegularCounters(regularCounters.subList(0, NUMBER_OF_REGULAR_COUNTERS_PER_POKEMON));

        raidBoss.setCounterType(counterType);
    }

    private List<RaidCounterInstance> generateRaidCounters(RaidBoss raidBoss, RaidPokemonData pokemonData, int level)
    {
        List<RaidCounterInstance> counters = new ArrayList<RaidCounterInstance>();

        for (RaidMove fastMove : pokemonData.getAllFastMoves())
        {
            for (RaidMove chargeMove : pokemonData.getAllChargeMoves())
            {
                RaidCounterInstance counter = calculationService.generateRaidCounter(raidBoss, pokemonData, fastMove, chargeMove, level);
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

    private List<RaidCounterInstance> filterListForBest(List<RaidCounterInstance> counterList)
    {
        List<RaidCounterInstance> allCounters = new ArrayList<RaidCounterInstance>();
        allCounters.addAll(counterList);

        Collections.sort(allCounters, new RaidCounterComparator());

        List<RaidCounterInstance> uniqueCounters = new ArrayList<RaidCounterInstance>();

        String topFast = "";
        String topCharge = "";

        for (int i = 0; i < allCounters.size(); i++)
        {
            RaidCounterInstance counter = allCounters.get(i);
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

    interface ReturnCounters
    {
        List<RaidCounterInstance> getCounters(RaidBoss pokemon);
    }

    private List<RaidCounterInstance> generateTopCounters(ReturnCounters filter, RaidCounterType counterType, int count)
    {
        Map<String, RaidCounterInstance> counters = new HashMap<String, RaidCounterInstance>();

        for (RaidBoss boss : getRaidBosses())
        {
            if (!boss.isCountersGenerated(counterType))
            {
                generateRaidCounters(boss, counterType);
            }

            for (RaidCounterInstance counter : filter.getCounters(boss))
            {
                String uniqueId = counter.getUniqueID();

                RaidCounterInstance currentCounter = counters.get(uniqueId);
                if (currentCounter == null)
                {
                    counters.put(uniqueId, counter);
                }
                else
                {
                    currentCounter.incrementCount();
                }
            }
        }

        List<RaidCounterInstance> counterPokemon = new ArrayList<RaidCounterInstance>(counters.values());
        Collections.sort(counterPokemon, new Comparator<RaidCounterInstance>()
        {
            @Override
            public int compare(RaidCounterInstance o1, RaidCounterInstance o2)
            {
                return o2.getCount() - o1.getCount();
            }
        });

        if (counterPokemon.size() < count)
        {
            return counterPokemon;
        }
        return counterPokemon.subList(0, count);
    }


    public List<RaidBoss> getRaidBosses()
    {
        return dataService.getRaidBosses();
    }

    public List<RaidCounterInstance> getCounterPokemon(RaidBoss boss, RaidCounterType counterType)
    {
        if (!boss.isCountersGenerated(counterType))
        {
            generateRaidCounters(boss, counterType);
        }

        List<RaidCounterInstance> counters = new ArrayList<RaidCounterInstance>();

        counters.addAll(boss.getMegaCounters());
        counters.addAll(boss.getShadowCounters());
        counters.addAll(boss.getRegularCounters());

        return counters;
    }

    public List<RaidCounterInstance> getTopMegaCounterPokemon(RaidCounterType counterType)
    {
        if (topMegaCounterPokemon.isEmpty())
        {
            ReturnCounters megaCounters = new ReturnCounters()
            {
                public List<RaidCounterInstance> getCounters(RaidBoss pokemon)
                {
                    return pokemon.getMegaCounters();
                }
            };
            topMegaCounterPokemon = generateTopCounters(megaCounters, counterType, NUMBER_OF_TOP_MEGA_COUNTERS_PER_POKEMON);
        }
        return topMegaCounterPokemon;
    }

    public List<RaidCounterInstance> getTopShadowCounterPokemon(RaidCounterType counterType)
    {
        if (topShadowCounterPokemon.isEmpty())
        {
            ReturnCounters shadowCounters = new ReturnCounters()
            {
                public List<RaidCounterInstance> getCounters(RaidBoss pokemon)
                {
                    return pokemon.getShadowCounters();
                }
            };
            topShadowCounterPokemon = generateTopCounters(shadowCounters, counterType, NUMBER_OF_TOP_SHADOW_COUNTERS_PER_POKEMON);
        }
        return topShadowCounterPokemon;
    }

    public List<RaidCounterInstance> getTopRegularCounterPokemon(RaidCounterType counterType)
    {
        if (topRegularCounterPokemon.isEmpty())
        {
            ReturnCounters regularCounters = new ReturnCounters()
            {
                public List<RaidCounterInstance> getCounters(RaidBoss pokemon)
                {
                    return pokemon.getRegularCounters();
                }
            };
            topRegularCounterPokemon = generateTopCounters(regularCounters, counterType, NUMBER_OF_TOP_REGULAR_COUNTERS_PER_POKEMON);
        }
        return topRegularCounterPokemon;
    }


    private class RaidCounterComparator implements Comparator<RaidCounterInstance>
    {
        public int compare(RaidCounterInstance o1, RaidCounterInstance o2)
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
