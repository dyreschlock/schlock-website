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

    public static final int NUMBER_OF_MEGA_COUNTERS_PER_POKEMON = 3;
    public static final int NUMBER_OF_SHADOW_COUNTERS_PER_POKEMON = 5;
    public static final int NUMBER_OF_REGULAR_COUNTERS_PER_POKEMON = 20;

    private static final int PARTY_LIMIT = 6;

    private static final int TOP_OVERALL_BEST_COUNTERS = 15;
    private static final int TOP_TYPE_COUNTER_LIMIT = 7;

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

    public void generateRaidCounters(RaidBossPokemon raidBoss, CounterType counterType)
    {
        if (!raidBoss.isCountersGenerated(counterType))
        {
            List<RaidCounterInstance> megaCounters = new ArrayList<RaidCounterInstance>();
            List<RaidCounterInstance> shadowCounters = new ArrayList<RaidCounterInstance>();
            List<RaidCounterInstance> regularCounters = new ArrayList<RaidCounterInstance>();

            for (CounterPokemon counterPoke : dataService.getCounterPokemon(counterType, BattleMode.RAID))
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

            Collections.sort(megaCounters);
            Collections.sort(shadowCounters);
            Collections.sort(regularCounters);

            raidBoss.setMegaCounters(counterType, megaCounters);
            raidBoss.setShadowCounters(counterType, shadowCounters);
            raidBoss.setRegularCounters(counterType, regularCounters);
        }
    }

    private List<RaidCounterInstance> subList(List<RaidCounterInstance> counters, int length)
    {
        if (counters.size() < length)
        {
            return counters;
        }
        return counters.subList(0, length);
    }

    private List<RaidCounterInstance> generateRaidCounters(RaidBossPokemon raidBoss, CounterPokemon counterPokemon)
    {
        List<RaidCounterInstance> counters = new ArrayList<RaidCounterInstance>();

        for (PokemonMove fastMove : counterPokemon.getAllFastMoves())
        {
            for (PokemonMove chargeMove : counterPokemon.getAllChargeMoves())
            {
                if(RaidBossWithAttackingType.class.isAssignableFrom(raidBoss.getClass()))
                {
                    String type = ((RaidBossWithAttackingType) raidBoss).getAttackingType();

                    String fastType = fastMove.getType();
                    String chargeType = chargeMove.getType();

                    if (!type.equalsIgnoreCase(fastType) || !type.equalsIgnoreCase(chargeType))
                    {
                        continue;
                    }
                }

                RaidCounterInstance counter = calculationService.generateRaidCounter(raidBoss, counterPokemon, fastMove, chargeMove);
                if (counter != null)
                {
                    counters.add(counter);
                }
            }
        }
        counters = filterListForBest(counters);
        if (RaidBossWithAttackingType.class.isAssignableFrom(raidBoss.getClass()))
        {
            if (!counters.isEmpty())
            {
                return counters.subList(0, 1);
            }
        }
        return counters;
    }


    private static final String GENGAR = "Gengar";
    private static final String LICK = "Lick";
    private static final String SHADOW_CLAW = "Shadow Claw";

    private static final String MEWTWO = "Mewtwo";
    private static final String PSYSTRIKE = "Psystrike";
    private static final String PSYCHIC = "Psychic";

    private static final String DARKRAI = "Darkrai";
    private static final String DARK_TYPE = "Dark";

    private static final String CHANDELURE = "Chandelure";
    private static final String SHADOW_BALL = "Shadow Ball";
    private static final String POLTERGEIST = "Poltergeist";

    private List<RaidCounterInstance> filterListForBest(List<RaidCounterInstance> counterList)
    {
        List<RaidCounterInstance> allCounters = new ArrayList<RaidCounterInstance>();
        allCounters.addAll(counterList);

        Collections.sort(allCounters);

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

                if (CHANDELURE.equalsIgnoreCase(counter.getName()))
                {
                    if (SHADOW_BALL.equalsIgnoreCase(topCharge) && POLTERGEIST.equalsIgnoreCase(counter.getChargeMove()))
                    {
                        uniqueCounters.add(counter);
                    }
                    if (POLTERGEIST.equalsIgnoreCase(topCharge) && SHADOW_BALL.equalsIgnoreCase(counter.getChargeMove()))
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

    public RaidBossPokemon getRaidBossByNameId(String nameId)
    {
        return dataService.getRaidBossByNameId(nameId);
    }

    public List<RaidBossWithAttackingType> getRaidBossForEachAttackingType()
    {
        return dataService.getRaidBossForEachAttackingType();
    }

    public List<RaidCounterInstance> getCounterPokemon(RaidBossPokemon boss, CounterType counterType)
    {
        if (!boss.isCountersGenerated(counterType))
        {
            generateRaidCounters(boss, counterType);
        }

        if(counterType.isCustom())
        {
            List<RaidCounterInstance> shadowCounters = subList(boss.getShadowCounters(counterType), NUMBER_OF_SHADOW_COUNTERS_PER_POKEMON);
            List<RaidCounterInstance> regularCounters = subList(boss.getRegularCounters(counterType), NUMBER_OF_REGULAR_COUNTERS_PER_POKEMON);

            List<RaidCounterInstance> counters = new ArrayList<RaidCounterInstance>();

            counters.add(boss.getMegaCounters(counterType).get(0));
            counters.addAll(shadowCounters);
            counters.addAll(regularCounters);

            Collections.sort(counters);

            return counters.subList(0, PARTY_LIMIT);
        }

        List<RaidCounterInstance> megaCounters = subList(boss.getMegaCounters(counterType), NUMBER_OF_MEGA_COUNTERS_PER_POKEMON);
        List<RaidCounterInstance> shadowCounters = subList(boss.getShadowCounters(counterType), NUMBER_OF_SHADOW_COUNTERS_PER_POKEMON);
        List<RaidCounterInstance> regularCounters = subList(boss.getRegularCounters(counterType), NUMBER_OF_REGULAR_COUNTERS_PER_POKEMON);

        List<RaidCounterInstance> counters = new ArrayList<RaidCounterInstance>();

        counters.addAll(megaCounters);
        counters.addAll(shadowCounters);
        counters.addAll(regularCounters);

        return counters;
    }

    public List<RaidCounterInstance> getCounterPokemonByAttackingType(RaidBossWithAttackingType boss, CounterType type)
    {
        if (!boss.isCountersGenerated(type))
        {
            generateRaidCounters(boss, type);
        }

        List<RaidCounterInstance> counters = new ArrayList<RaidCounterInstance>();

        counters.addAll(boss.getRegularCounters(type));
        counters.addAll(boss.getMegaCounters(type));
        counters.addAll(boss.getShadowCounters(type));

        Collections.sort(counters);

        return counters.subList(0, TOP_TYPE_COUNTER_LIMIT);
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

    public List<RaidCounterInstance> getTopCounterPokemonByAttackingType(CounterType type)
    {
        List<RaidCounterInstance> counters = new ArrayList<RaidCounterInstance>();
        for (RaidBossWithAttackingType boss : getRaidBossForEachAttackingType())
        {
            counters.addAll(getCounterPokemonByAttackingType(boss, type));
        }

        Collections.sort(counters);

        return counters.subList(0, TOP_OVERALL_BEST_COUNTERS);
    }
}
