package com.schlock.website.services.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.*;
import com.schlock.website.services.apps.pokemon.PokemonCounterCalculationService;
import com.schlock.website.services.apps.pokemon.PokemonRaidCounterService;
import com.schlock.website.services.apps.pokemon.PokemonDataService;

import java.util.*;

public class PokemonRaidCounterServiceImpl implements PokemonRaidCounterService
{
    private static final int NUMBER_OF_TOP_MEGA_COUNTERS_PER_POKEMON = 10;
    private static final int NUMBER_OF_TOP_SHADOW_COUNTERS_PER_POKEMON = 20;
    private static final int NUMBER_OF_TOP_REGULAR_COUNTERS_PER_POKEMON = 32;

    private static final int NUMBER_OF_MEGA_COUNTERS_PER_POKEMON = 4;
    private static final int NUMBER_OF_SHADOW_COUNTERS_PER_POKEMON = 8;
    private static final int NUMBER_OF_REGULAR_COUNTERS_PER_POKEMON = 20;

    private static final int PARTY_LIMIT = 6;

    private List<RaidCounterInstance> topMegaCounterPokemon = new ArrayList<RaidCounterInstance>();
    private List<RaidCounterInstance> topShadowCounterPokemon = new ArrayList<RaidCounterInstance>();
    private List<RaidCounterInstance> topRegularCounterPokemon = new ArrayList<RaidCounterInstance>();

    private final PokemonCounterCalculationService calculationService;
    private final PokemonDataService dataService;


    public PokemonRaidCounterServiceImpl(PokemonCounterCalculationService calculationService,
                                         PokemonDataService dataService)
    {
        this.calculationService = calculationService;
        this.dataService = dataService;
    }

    private void generateRaidCounters(RaidBossPokemon raidBoss, CounterType counterType)
    {
        List<RaidCounterInstance> megaCounters = new ArrayList<RaidCounterInstance>();
        List<RaidCounterInstance> shadowCounters = new ArrayList<RaidCounterInstance>();
        List<RaidCounterInstance> regularCounters = new ArrayList<RaidCounterInstance>();

        for (CounterPokemon counterPoke : dataService.getCounterPokemon(counterType))
        {
            List<RaidCounterInstance> counterInstances = new ArrayList<RaidCounterInstance>();

            counterInstances.addAll(generateRaidCounters(raidBoss, counterPoke));

            if (counterPoke.isMega())
            {
                megaCounters.addAll(counterInstances);
            }
            else if (counterPoke.isShadow())
            {
                shadowCounters.addAll(counterInstances);
            }
            else
            {
                regularCounters.addAll(counterInstances);
            }
        }

        Collections.sort(megaCounters, new CounterTDOComparator());
        Collections.sort(shadowCounters, new CounterTDOComparator());
        Collections.sort(regularCounters, new CounterTDOComparator());

        raidBoss.setMegaCounters(counterType, megaCounters.subList(0, NUMBER_OF_MEGA_COUNTERS_PER_POKEMON));
        raidBoss.setShadowCounters(counterType, shadowCounters.subList(0, NUMBER_OF_SHADOW_COUNTERS_PER_POKEMON));
        raidBoss.setRegularCounters(counterType, regularCounters.subList(0, NUMBER_OF_REGULAR_COUNTERS_PER_POKEMON));
    }

    private List<RaidCounterInstance> generateRaidCounters(RaidBossPokemon raidBoss, CounterPokemon counterPokemon)
    {
        List<RaidCounterInstance> counters = new ArrayList<RaidCounterInstance>();

        for (PokemonMove fastMove : counterPokemon.getAllFastMoves())
        {
            for (PokemonMove chargeMove : counterPokemon.getAllChargeMoves())
            {
                RaidCounterInstance counter = calculationService.generateRaidCounter(raidBoss, counterPokemon, fastMove, chargeMove);
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

    private static final String DARKRAI = "Darkrai";
    private static final String DARK_TYPE = "Dark";

    private List<RaidCounterInstance> filterListForBest(List<RaidCounterInstance> counterList)
    {
        List<RaidCounterInstance> allCounters = new ArrayList<RaidCounterInstance>();
        allCounters.addAll(counterList);

        Collections.sort(allCounters, new CounterTDOComparator());

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
                if (DARKRAI.equalsIgnoreCase(counter.getName()))
                {
                    PokemonMove move = dataService.getMoveByName(counter.getChargeMove());
                    if (topFast.equals(counter.getFastMove()) && DARK_TYPE.equalsIgnoreCase(move.getType()))
                    {
                        uniqueCounters.add(counter);
                    }
                }

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
        List<RaidCounterInstance> getCounters(RaidBossPokemon pokemon);
    }

    private List<RaidCounterInstance> generateTopCounters(ReturnCounters filter, CounterType counterType, int count)
    {
        Map<String, RaidCounterInstance> counters = new HashMap<String, RaidCounterInstance>();

        for (RaidBossPokemon boss : getRaidBosses())
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
                //higher count better
                int compare = o2.getCount() - o1.getCount();
                if (compare == 0)
                {
                    //lower level better
                    compare = o1.getLevel() - o2.getLevel();
                    if (compare == 0)
                    {
                        //alphabetical
                        compare = o1.getName().compareTo(o2.getName());
                    }
                }
                return compare;
            }
        });

        if (counterPokemon.size() < count)
        {
            return counterPokemon;
        }
        return counterPokemon.subList(0, count);
    }


    public List<RaidBossPokemon> getRaidBosses()
    {
        return dataService.getRaidBosses();
    }

    public List<RaidCounterInstance> getCounterPokemon(RaidBossPokemon boss, CounterType counterType)
    {
        if (!boss.isCountersGenerated(counterType))
        {
            generateRaidCounters(boss, counterType);
        }

        if(counterType.isCustom())
        {
            List<RaidCounterInstance> counters = new ArrayList<RaidCounterInstance>();

            counters.add(boss.getMegaCounters(counterType).get(0));
            counters.addAll(boss.getShadowCounters(counterType));
            counters.addAll(boss.getRegularCounters(counterType));

            Collections.sort(counters, new CounterTDOComparator());

            return counters.subList(0, PARTY_LIMIT);
        }

        List<RaidCounterInstance> counters = new ArrayList<RaidCounterInstance>();

        counters.addAll(boss.getMegaCounters(counterType));
        counters.addAll(boss.getShadowCounters(counterType));
        counters.addAll(boss.getRegularCounters(counterType));

        return counters;
    }

    public List<RaidCounterInstance> getTopMegaCounterPokemon(final CounterType counterType)
    {
        if (topMegaCounterPokemon.isEmpty())
        {
            ReturnCounters megaCounters = new ReturnCounters()
            {
                public List<RaidCounterInstance> getCounters(RaidBossPokemon pokemon)
                {
                    return pokemon.getMegaCounters(counterType);
                }
            };
            topMegaCounterPokemon = generateTopCounters(megaCounters, counterType, NUMBER_OF_TOP_MEGA_COUNTERS_PER_POKEMON);
        }
        return topMegaCounterPokemon;
    }

    public List<RaidCounterInstance> getTopShadowCounterPokemon(final CounterType counterType)
    {
        if (topShadowCounterPokemon.isEmpty())
        {
            ReturnCounters shadowCounters = new ReturnCounters()
            {
                public List<RaidCounterInstance> getCounters(RaidBossPokemon pokemon)
                {
                    return pokemon.getShadowCounters(counterType);
                }
            };
            topShadowCounterPokemon = generateTopCounters(shadowCounters, counterType, NUMBER_OF_TOP_SHADOW_COUNTERS_PER_POKEMON);
        }
        return topShadowCounterPokemon;
    }

    public List<RaidCounterInstance> getTopRegularCounterPokemon(final CounterType counterType)
    {
        if (topRegularCounterPokemon.isEmpty())
        {
            ReturnCounters regularCounters = new ReturnCounters()
            {
                public List<RaidCounterInstance> getCounters(RaidBossPokemon pokemon)
                {
                    return pokemon.getRegularCounters(counterType);
                }
            };
            topRegularCounterPokemon = generateTopCounters(regularCounters, counterType, NUMBER_OF_TOP_REGULAR_COUNTERS_PER_POKEMON);
        }
        return topRegularCounterPokemon;
    }


    private class CounterTDOComparator implements Comparator<RaidCounterInstance>
    {
        public int compare(RaidCounterInstance o1, RaidCounterInstance o2)
        {
            //higher tdo better
            double compare = o2.getDps4tdo() - o1.getDps4tdo();
            if (compare == 0.0)
            {
                //lower level better
                compare = o1.getLevel() - o2.getLevel();
                if (compare == 0.0)
                {
                    //alphabetical
                    compare = o1.getName().compareTo(o2.getName());
                }
            }

            if (compare > 0.0)
            {
                return 1;
            }
            if (compare < 0.0)
            {
                return -1;
            }
            return 0;
        }
    }
}
