package com.schlock.website.services.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.*;
import com.schlock.website.services.apps.pokemon.PokemonCounterCalculationService;
import com.schlock.website.services.apps.pokemon.PokemonDataService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PokemonCounterCalculationServiceImpl implements PokemonCounterCalculationService
{
    private static final double SHADOW_DEFENSE_MULTIPLIER = 0.8333333;
    private static final double SHADOW_ATTACK_MULTIPLIER = 1.2;

    private static final double MEGA_POKEMON_STAT_MULTIPLIER = 1.1;

    private static final double BEST_FRIEND_ATTACK_BONUS = 1.1;
    private static final double SAME_TYPE_ATTACK_BONUS = 1.2;
    private static final double SAME_TYPE_ATTACK_BONUS_MEGA = 1.3;

    private static final double DEFAULT_BOSS_DPS = 900;

    private static final boolean BEST_FRIEND_BONUS_YES = true;
    private static final boolean BEST_FRIEND_BONUS_NO = false;


    private static final String BUG = "Bug";
    private static final String DARK = "Dark";
    private static final String DRAGON = "Dragon";
    private static final String ELECTRIC = "Electric";
    private static final String FAIRY = "Fairy";
    private static final String FIGHTING = "Fighting";
    private static final String FIRE = "Fire";
    private static final String FLYING = "Flying";
    private static final String GHOST = "Ghost";
    private static final String GRASS = "Grass";
    private static final String GROUND = "Ground";
    private static final String ICE = "Ice";
    private static final String NORMAL = "Normal";
    private static final String POISON = "Poison";
    private static final String PSYCHIC = "Psychic";
    private static final String ROCK = "Rock";
    private static final String STEEL = "Steel";
    private static final String WATER = "Water";

    private static final Double STRONG = 1.6;
    private static final Double NEUTRAL = 1.0;
    private static final Double WEAK = 0.625;
    private static final Double VERY_WEAK = 0.390625;

    private static final Map<String, Map<String, Double>> TYPE_EFFECTIVENESS = new HashMap<String, Map<String, Double>>();

    {
        // "bug": { "bug": 1, "dark": 1.6, "dragon": 1, "electric": 1, "fairy": 0.625, "fighting": 0.625, "fire": 0.625, "flying": 0.625,
        // "ghost": 0.625, "grass": 1.6, "ground": 1, "ice": 1, "normal": 1, "poison": 0.625, "psychic": 1.6, "rock": 1, "steel": 0.625, "water": 1 },

        Map<String, Double> bug = new HashMap<String, Double>();
        bug.put(BUG, NEUTRAL);
        bug.put(DARK, STRONG);
        bug.put(DRAGON, NEUTRAL);
        bug.put(ELECTRIC, NEUTRAL);
        bug.put(FAIRY, WEAK);
        bug.put(FIGHTING, WEAK);
        bug.put(FIRE, WEAK);
        bug.put(FLYING, WEAK);
        bug.put(GHOST, WEAK);
        bug.put(GRASS, STRONG);
        bug.put(GROUND, NEUTRAL);
        bug.put(ICE, NEUTRAL);
        bug.put(NORMAL, NEUTRAL);
        bug.put(POISON, WEAK);
        bug.put(PSYCHIC, STRONG);
        bug.put(ROCK, NEUTRAL);
        bug.put(STEEL, WEAK);
        bug.put(WATER, NEUTRAL);

        TYPE_EFFECTIVENESS.put(BUG, bug);

        //  "dark": { "bug": 1, "dark": 0.625, "dragon": 1, "electric": 1, "fairy": 0.625, "fighting": 0.625, "fire": 1, "flying": 1,
        //  "ghost": 1.6, "grass": 1, "ground": 1, "ice": 1, "normal": 1, "poison": 1, "psychic": 1.6, "rock": 1, "steel": 1, "water": 1 },

        Map<String, Double> dark = new HashMap<String, Double>();
        dark.put(BUG, NEUTRAL);
        dark.put(DARK, WEAK);
        dark.put(DRAGON, NEUTRAL);
        dark.put(ELECTRIC, NEUTRAL);
        dark.put(FAIRY, WEAK);
        dark.put(FIGHTING, WEAK);
        dark.put(FIRE, NEUTRAL);
        dark.put(FLYING, NEUTRAL);
        dark.put(GHOST, STRONG);
        dark.put(GRASS, NEUTRAL);
        dark.put(GROUND, NEUTRAL);
        dark.put(ICE, NEUTRAL);
        dark.put(NORMAL, NEUTRAL);
        dark.put(POISON, NEUTRAL);
        dark.put(PSYCHIC, STRONG);
        dark.put(ROCK, NEUTRAL);
        dark.put(STEEL, NEUTRAL);
        dark.put(WATER, NEUTRAL);

        TYPE_EFFECTIVENESS.put(DARK, dark);


        //  "dragon": { "bug": 1, "dark": 1, "dragon": 1.6, "electric": 1, "fairy": 0.390625, "fighting": 1, "fire": 1, "flying": 1,
        //  "ghost": 1, "grass": 1, "ground": 1, "ice": 1, "normal": 1, "poison": 1, "psychic": 1, "rock": 1, "steel": 0.625, "water": 1 },

        Map<String, Double> dragon = new HashMap<String, Double>();
        dragon.put(BUG, NEUTRAL);
        dragon.put(DARK, NEUTRAL);
        dragon.put(DRAGON, STRONG);
        dragon.put(ELECTRIC, NEUTRAL);
        dragon.put(FAIRY, VERY_WEAK);
        dragon.put(FIGHTING, NEUTRAL);
        dragon.put(FIRE, NEUTRAL);
        dragon.put(FLYING, NEUTRAL);
        dragon.put(GHOST, NEUTRAL);
        dragon.put(GRASS, NEUTRAL);
        dragon.put(GROUND, NEUTRAL);
        dragon.put(ICE, NEUTRAL);
        dragon.put(NORMAL, NEUTRAL);
        dragon.put(POISON, NEUTRAL);
        dragon.put(PSYCHIC, NEUTRAL);
        dragon.put(ROCK, NEUTRAL);
        dragon.put(STEEL, WEAK);
        dragon.put(WATER, NEUTRAL);

        TYPE_EFFECTIVENESS.put(DRAGON, dragon);

        //  "electric": { "bug": 1, "dark": 1, "dragon": 0.625, "electric": 0.625, "fairy": 1, "fighting": 1, "fire": 1, "flying": 1.6,
        //  "ghost": 1, "grass": 0.625, "ground": 0.390625, "ice": 1, "normal": 1, "poison": 1, "psychic": 1, "rock": 1, "steel": 1, "water": 1.6 },

        Map<String, Double> electric = new HashMap<String, Double>();
        electric.put(BUG, NEUTRAL);
        electric.put(DARK, NEUTRAL);
        electric.put(DRAGON, WEAK);
        electric.put(ELECTRIC, WEAK);
        electric.put(FAIRY, NEUTRAL);
        electric.put(FIGHTING, NEUTRAL);
        electric.put(FIRE, NEUTRAL);
        electric.put(FLYING, STRONG);
        electric.put(GHOST, NEUTRAL);
        electric.put(GRASS, WEAK);
        electric.put(GROUND, VERY_WEAK);
        electric.put(ICE, NEUTRAL);
        electric.put(NORMAL, NEUTRAL);
        electric.put(POISON, NEUTRAL);
        electric.put(PSYCHIC, NEUTRAL);
        electric.put(ROCK, NEUTRAL);
        electric.put(STEEL, NEUTRAL);
        electric.put(WATER, STRONG);

        TYPE_EFFECTIVENESS.put(ELECTRIC, electric);

        //  "fairy": { "bug": 1, "dark": 1.6, "dragon": 1.6, "electric": 1, "fairy": 1, "fighting": 1.6, "fire": 0.625, "flying": 1,
        //  "ghost": 1, "grass": 1, "ground": 1, "ice": 1, "normal": 1, "poison": 0.625, "psychic": 1, "rock": 1, "steel": 0.625, "water": 1 },

        Map<String, Double> fairy = new HashMap<String, Double>();
        fairy.put(BUG, NEUTRAL);
        fairy.put(DARK, STRONG);
        fairy.put(DRAGON, STRONG);
        fairy.put(ELECTRIC, NEUTRAL);
        fairy.put(FAIRY, NEUTRAL);
        fairy.put(FIGHTING, STRONG);
        fairy.put(FIRE, WEAK);
        fairy.put(FLYING, NEUTRAL);
        fairy.put(GHOST, NEUTRAL);
        fairy.put(GRASS, NEUTRAL);
        fairy.put(GROUND, NEUTRAL);
        fairy.put(ICE, NEUTRAL);
        fairy.put(NORMAL, NEUTRAL);
        fairy.put(POISON, WEAK);
        fairy.put(PSYCHIC, NEUTRAL);
        fairy.put(ROCK, NEUTRAL);
        fairy.put(STEEL, WEAK);
        fairy.put(WATER, NEUTRAL);

        TYPE_EFFECTIVENESS.put(FAIRY, fairy);

        //  "fighting": { "bug": 0.625, "dark": 1.6, "dragon": 1, "electric": 1, "fairy": 0.625, "fighting": 1, "fire": 1, "flying": 0.625,
        //  "ghost": 0.390625, "grass": 1, "ground": 1, "ice": 1.6, "normal": 1.6, "poison": 0.625, "psychic": 0.625, "rock": 1.6, "steel": 1.6, "water": 1 },

        Map<String, Double> fighting = new HashMap<String, Double>();
        fighting.put(BUG, WEAK);
        fighting.put(DARK, STRONG);
        fighting.put(DRAGON, NEUTRAL);
        fighting.put(ELECTRIC, NEUTRAL);
        fighting.put(FAIRY, WEAK);
        fighting.put(FIGHTING, NEUTRAL);
        fighting.put(FIRE, NEUTRAL);
        fighting.put(FLYING, WEAK);
        fighting.put(GHOST, VERY_WEAK);
        fighting.put(GRASS, NEUTRAL);
        fighting.put(GROUND, NEUTRAL);
        fighting.put(ICE, STRONG);
        fighting.put(NORMAL, STRONG);
        fighting.put(POISON, WEAK);
        fighting.put(PSYCHIC, WEAK);
        fighting.put(ROCK, STRONG);
        fighting.put(STEEL, STRONG);
        fighting.put(WATER, NEUTRAL);

        TYPE_EFFECTIVENESS.put(FIGHTING, fighting);

        //  "fire": { "bug": 1.6, "dark": 1, "dragon": 0.625, "electric": 1, "fairy": 1, "fighting": 1, "fire": 0.625, "flying": 1,
        //  "ghost": 1, "grass": 1.6, "ground": 1, "ice": 1.6, "normal": 1, "poison": 1, "psychic": 1, "rock": 0.625, "steel": 1.6, "water": 0.625 },

        Map<String, Double> fire = new HashMap<String, Double>();
        fire.put(BUG, STRONG);
        fire.put(DARK, NEUTRAL);
        fire.put(DRAGON, WEAK);
        fire.put(ELECTRIC, NEUTRAL);
        fire.put(FAIRY, NEUTRAL);
        fire.put(FIGHTING, NEUTRAL);
        fire.put(FIRE, WEAK);
        fire.put(FLYING, NEUTRAL);
        fire.put(GHOST, NEUTRAL);
        fire.put(GRASS, STRONG);
        fire.put(GROUND, NEUTRAL);
        fire.put(ICE, STRONG);
        fire.put(NORMAL, NEUTRAL);
        fire.put(POISON, NEUTRAL);
        fire.put(PSYCHIC, NEUTRAL);
        fire.put(ROCK, WEAK);
        fire.put(STEEL, STRONG);
        fire.put(WATER, WEAK);

        TYPE_EFFECTIVENESS.put(FIRE, fire);

        //  "flying": { "bug": 1.6, "dark": 1, "dragon": 1, "electric": 0.625, "fairy": 1, "fighting": 1.6, "fire": 1, "flying": 1,
        //  "ghost": 1, "grass": 1.6, "ground": 1, "ice": 1, "normal": 1, "poison": 1, "psychic": 1, "rock": 0.625, "steel": 0.625, "water": 1 },

        Map<String, Double> flying = new HashMap<String, Double>();
        flying.put(BUG, STRONG);
        flying.put(DARK, NEUTRAL);
        flying.put(DRAGON, NEUTRAL);
        flying.put(ELECTRIC, WEAK);
        flying.put(FAIRY, NEUTRAL);
        flying.put(FIGHTING, STRONG);
        flying.put(FIRE, NEUTRAL);
        flying.put(FLYING, NEUTRAL);
        flying.put(GHOST, NEUTRAL);
        flying.put(GRASS, STRONG);
        flying.put(GROUND, NEUTRAL);
        flying.put(ICE, NEUTRAL);
        flying.put(NORMAL, NEUTRAL);
        flying.put(POISON, NEUTRAL);
        flying.put(PSYCHIC, NEUTRAL);
        flying.put(ROCK, WEAK);
        flying.put(STEEL, WEAK);
        flying.put(WATER, NEUTRAL);

        TYPE_EFFECTIVENESS.put(FLYING, flying);

        //  "ghost": { "bug": 1, "dark": 0.625, "dragon": 1, "electric": 1, "fairy": 1, "fighting": 1, "fire": 1, "flying": 1,
        //  "ghost": 1.6, "grass": 1, "ground": 1, "ice": 1, "normal": 0.390625, "poison": 1, "psychic": 1.6, "rock": 1, "steel": 1, "water": 1 },

        Map<String, Double> ghost = new HashMap<String, Double>();
        ghost.put(BUG, NEUTRAL);
        ghost.put(DARK, WEAK);
        ghost.put(DRAGON, NEUTRAL);
        ghost.put(ELECTRIC, NEUTRAL);
        ghost.put(FAIRY, NEUTRAL);
        ghost.put(FIGHTING, NEUTRAL);
        ghost.put(FIRE, NEUTRAL);
        ghost.put(FLYING, NEUTRAL);
        ghost.put(GHOST, STRONG);
        ghost.put(GRASS, NEUTRAL);
        ghost.put(GROUND, NEUTRAL);
        ghost.put(ICE, NEUTRAL);
        ghost.put(NORMAL, VERY_WEAK);
        ghost.put(POISON, NEUTRAL);
        ghost.put(PSYCHIC, STRONG);
        ghost.put(ROCK, NEUTRAL);
        ghost.put(STEEL, NEUTRAL);
        ghost.put(WATER, NEUTRAL);

        TYPE_EFFECTIVENESS.put(GHOST, ghost);

        //  "grass": { "bug": 0.625, "dark": 1, "dragon": 0.625, "electric": 1, "fairy": 1, "fighting": 1, "fire": 0.625, "flying": 0.625,
        //  "ghost": 1, "grass": 0.625, "ground": 1.6, "ice": 1, "normal": 1, "poison": 0.625, "psychic": 1, "rock": 1.6, "steel": 0.625, "water": 1.6 },

        Map<String, Double> grass = new HashMap<String, Double>();
        grass.put(BUG, WEAK);
        grass.put(DARK, NEUTRAL);
        grass.put(DRAGON, WEAK);
        grass.put(ELECTRIC, NEUTRAL);
        grass.put(FAIRY, NEUTRAL);
        grass.put(FIGHTING, NEUTRAL);
        grass.put(FIRE, WEAK);
        grass.put(FLYING, WEAK);
        grass.put(GHOST, NEUTRAL);
        grass.put(GRASS, WEAK);
        grass.put(GROUND, STRONG);
        grass.put(ICE, NEUTRAL);
        grass.put(NORMAL, NEUTRAL);
        grass.put(POISON, WEAK);
        grass.put(PSYCHIC, NEUTRAL);
        grass.put(ROCK, STRONG);
        grass.put(STEEL, WEAK);
        grass.put(WATER, STRONG);

        TYPE_EFFECTIVENESS.put(GRASS, grass);

        //  "ground": { "bug": 0.625, "dark": 1, "dragon": 1, "electric": 1.6, "fairy": 1, "fighting": 1, "fire": 1.6, "flying": 0.390625,
        //  "ghost": 1, "grass": 0.625, "ground": 1, "ice": 1, "normal": 1, "poison": 1.6, "psychic": 1, "rock": 1.6, "steel": 1.6, "water": 1 },

        Map<String, Double> ground = new HashMap<String, Double>();
        ground.put(BUG, WEAK);
        ground.put(DARK, NEUTRAL);
        ground.put(DRAGON, NEUTRAL);
        ground.put(ELECTRIC, STRONG);
        ground.put(FAIRY, NEUTRAL);
        ground.put(FIGHTING, NEUTRAL);
        ground.put(FIRE, STRONG);
        ground.put(FLYING, VERY_WEAK);
        ground.put(GHOST, NEUTRAL);
        ground.put(GRASS, WEAK);
        ground.put(GROUND, NEUTRAL);
        ground.put(ICE, NEUTRAL);
        ground.put(NORMAL, NEUTRAL);
        ground.put(POISON, STRONG);
        ground.put(PSYCHIC, NEUTRAL);
        ground.put(ROCK, STRONG);
        ground.put(STEEL, STRONG);
        ground.put(WATER, NEUTRAL);

        TYPE_EFFECTIVENESS.put(GROUND, ground);

        //  "ice": { "bug": 1, "dark": 1, "dragon": 1.6, "electric": 1, "fairy": 1, "fighting": 1, "fire": 0.625, "flying": 1.6,
        //  "ghost": 1, "grass": 1.6, "ground": 1.6, "ice": 0.625, "normal": 1, "poison": 1, "psychic": 1, "rock": 1, "steel": 0.625, "water": 0.625 },

        Map<String, Double> ice = new HashMap<String, Double>();
        ice.put(BUG, NEUTRAL);
        ice.put(DARK, NEUTRAL);
        ice.put(DRAGON, STRONG);
        ice.put(ELECTRIC, NEUTRAL);
        ice.put(FAIRY, NEUTRAL);
        ice.put(FIGHTING, NEUTRAL);
        ice.put(FIRE, WEAK);
        ice.put(FLYING, STRONG);
        ice.put(GHOST, NEUTRAL);
        ice.put(GRASS, STRONG);
        ice.put(GROUND, STRONG);
        ice.put(ICE, WEAK);
        ice.put(NORMAL, NEUTRAL);
        ice.put(POISON, NEUTRAL);
        ice.put(PSYCHIC, NEUTRAL);
        ice.put(ROCK, NEUTRAL);
        ice.put(STEEL, WEAK);
        ice.put(WATER, WEAK);

        TYPE_EFFECTIVENESS.put(ICE, ice);

        //  "normal": { "bug": 1, "dark": 1, "dragon": 1, "electric": 1, "fairy": 1, "fighting": 1, "fire": 1, "flying": 1,
        //  "ghost": 0.390625, "grass": 1, "ground": 1, "ice": 1, "normal": 1, "poison": 1, "psychic": 1, "rock": 0.625, "steel": 0.625, "water": 1 },

        Map<String, Double> normal = new HashMap<String, Double>();
        normal.put(BUG, NEUTRAL);
        normal.put(DARK, NEUTRAL);
        normal.put(DRAGON, NEUTRAL);
        normal.put(ELECTRIC, NEUTRAL);
        normal.put(FAIRY, NEUTRAL);
        normal.put(FIGHTING, NEUTRAL);
        normal.put(FIRE, NEUTRAL);
        normal.put(FLYING, NEUTRAL);
        normal.put(GHOST, VERY_WEAK);
        normal.put(GRASS, NEUTRAL);
        normal.put(GROUND, NEUTRAL);
        normal.put(ICE, NEUTRAL);
        normal.put(NORMAL, NEUTRAL);
        normal.put(POISON, NEUTRAL);
        normal.put(PSYCHIC, NEUTRAL);
        normal.put(ROCK, WEAK);
        normal.put(STEEL, WEAK);
        normal.put(WATER, NEUTRAL);

        TYPE_EFFECTIVENESS.put(NORMAL, normal);

        //  "poison": { "bug": 1, "dark": 1, "dragon": 1, "electric": 1, "fairy": 1.6, "fighting": 1, "fire": 1, "flying": 1,
        //  "ghost": 0.625, "grass": 1.6, "ground": 0.625, "ice": 1, "normal": 1, "poison": 0.625, "psychic": 1, "rock": 0.625, "steel": 0.390625, "water": 1 },

        Map<String, Double> poison = new HashMap<String, Double>();
        poison.put(BUG, NEUTRAL);
        poison.put(DARK, NEUTRAL);
        poison.put(DRAGON, NEUTRAL);
        poison.put(ELECTRIC, NEUTRAL);
        poison.put(FAIRY, STRONG);
        poison.put(FIGHTING, NEUTRAL);
        poison.put(FIRE, NEUTRAL);
        poison.put(FLYING, NEUTRAL);
        poison.put(GHOST, WEAK);
        poison.put(GRASS, STRONG);
        poison.put(GROUND, WEAK);
        poison.put(ICE, NEUTRAL);
        poison.put(NORMAL, NEUTRAL);
        poison.put(POISON, WEAK);
        poison.put(PSYCHIC, NEUTRAL);
        poison.put(ROCK, WEAK);
        poison.put(STEEL, VERY_WEAK);
        poison.put(WATER, NEUTRAL);

        TYPE_EFFECTIVENESS.put(POISON, poison);


        //  "psychic": { "bug": 1, "dark": 0.390625, "dragon": 1, "electric": 1, "fairy": 1, "fighting": 1.6, "fire": 1, "flying": 1,
        //  "ghost": 1, "grass": 1, "ground": 1, "ice": 1, "normal": 1, "poison": 1.6, "psychic": 0.625, "rock": 1, "steel": 0.625, "water": 1 },

        Map<String, Double> psychic = new HashMap<String, Double>();
        psychic.put(BUG, NEUTRAL);
        psychic.put(DARK, VERY_WEAK);
        psychic.put(DRAGON, NEUTRAL);
        psychic.put(ELECTRIC, NEUTRAL);
        psychic.put(FAIRY, NEUTRAL);
        psychic.put(FIGHTING, STRONG);
        psychic.put(FIRE, NEUTRAL);
        psychic.put(FLYING, NEUTRAL);
        psychic.put(GHOST, NEUTRAL);
        psychic.put(GRASS, NEUTRAL);
        psychic.put(GROUND, NEUTRAL);
        psychic.put(ICE, NEUTRAL);
        psychic.put(NORMAL, NEUTRAL);
        psychic.put(POISON, STRONG);
        psychic.put(PSYCHIC, WEAK);
        psychic.put(ROCK, NEUTRAL);
        psychic.put(STEEL, WEAK);
        psychic.put(WATER, NEUTRAL);

        TYPE_EFFECTIVENESS.put(PSYCHIC, psychic);

        //  "rock": { "bug": 1.6, "dark": 1, "dragon": 1, "electric": 1, "fairy": 1, "fighting": 0.625, "fire": 1.6, "flying": 1.6,
        //  "ghost": 1, "grass": 1, "ground": 0.625, "ice": 1.6, "normal": 1, "poison": 1, "psychic": 1, "rock": 1, "steel": 0.625, "water": 1 },

        Map<String, Double> rock = new HashMap<String, Double>();
        rock.put(BUG, STRONG);
        rock.put(DARK, NEUTRAL);
        rock.put(DRAGON, NEUTRAL);
        rock.put(ELECTRIC, NEUTRAL);
        rock.put(FAIRY, NEUTRAL);
        rock.put(FIGHTING, WEAK);
        rock.put(FIRE, STRONG);
        rock.put(FLYING, STRONG);
        rock.put(GHOST, NEUTRAL);
        rock.put(GRASS, NEUTRAL);
        rock.put(GROUND, WEAK);
        rock.put(ICE, STRONG);
        rock.put(NORMAL, NEUTRAL);
        rock.put(POISON, NEUTRAL);
        rock.put(PSYCHIC, NEUTRAL);
        rock.put(ROCK, NEUTRAL);
        rock.put(STEEL, WEAK);
        rock.put(WATER, NEUTRAL);

        TYPE_EFFECTIVENESS.put(ROCK, rock);

        //  "steel": { "bug": 1, "dark": 1, "dragon": 1, "electric": 0.625, "fairy": 1.6, "fighting": 1, "fire": 0.625, "flying": 1,
        //  "ghost": 1, "grass": 1, "ground": 1, "ice": 1.6, "normal": 1, "poison": 1, "psychic": 1, "rock": 1.6, "steel": 0.625, "water": 0.625 },

        Map<String, Double> steel = new HashMap<String, Double>();
        steel.put(BUG, NEUTRAL);
        steel.put(DARK, NEUTRAL);
        steel.put(DRAGON, NEUTRAL);
        steel.put(ELECTRIC, WEAK);
        steel.put(FAIRY, STRONG);
        steel.put(FIGHTING, NEUTRAL);
        steel.put(FIRE, WEAK);
        steel.put(FLYING, NEUTRAL);
        steel.put(GHOST, NEUTRAL);
        steel.put(GRASS, NEUTRAL);
        steel.put(GROUND, NEUTRAL);
        steel.put(ICE, STRONG);
        steel.put(NORMAL, NEUTRAL);
        steel.put(POISON, NEUTRAL);
        steel.put(PSYCHIC, NEUTRAL);
        steel.put(ROCK, STRONG);
        steel.put(STEEL, WEAK);
        steel.put(WATER, WEAK);

        TYPE_EFFECTIVENESS.put(STEEL, steel);

        //  "water": { "bug": 1, "dark": 1, "dragon": 0.625, "electric": 1, "fairy": 1, "fighting": 1, "fire": 1.6, "flying": 1,
        //  "ghost": 1, "grass": 0.625, "ground": 1.6, "ice": 1, "normal": 1, "poison": 1, "psychic": 1, "rock": 1.6, "steel": 1, "water": 0.625 }

        Map<String, Double> water = new HashMap<String, Double>();
        water.put(BUG, NEUTRAL);
        water.put(DARK, NEUTRAL);
        water.put(DRAGON, WEAK);
        water.put(ELECTRIC, NEUTRAL);
        water.put(FAIRY, NEUTRAL);
        water.put(FIGHTING, NEUTRAL);
        water.put(FIRE, STRONG);
        water.put(FLYING, NEUTRAL);
        water.put(GHOST, NEUTRAL);
        water.put(GRASS, WEAK);
        water.put(GROUND, STRONG);
        water.put(ICE, NEUTRAL);
        water.put(NORMAL, NEUTRAL);
        water.put(POISON, NEUTRAL);
        water.put(PSYCHIC, NEUTRAL);
        water.put(ROCK, STRONG);
        water.put(STEEL, NEUTRAL);
        water.put(WATER, WEAK);

        TYPE_EFFECTIVENESS.put(WATER, water);
    }

    private final PokemonDataService dataService;

    public PokemonCounterCalculationServiceImpl(PokemonDataService dataService)
    {
        this.dataService = dataService;
    }

    public RaidCounterInstance generateRaidCounter(RaidBossPokemon raidBoss, CounterPokemon counter, PokemonMove fastMove, PokemonMove chargeMove)
    {
        return (RaidCounterInstance) generateCounter(raidBoss, counter, fastMove, chargeMove, BattleMode.RAID);
    }

    public RocketCounterInstance generateRocketCounter(RocketBossPokemon rocketBoss, CounterPokemon counter, PokemonMove fastMove, PokemonMove chargeMove)
    {
        return (RocketCounterInstance) generateCounter(rocketBoss, counter, fastMove, chargeMove, BattleMode.ROCKET);
    }

    public AbstractCounterInstance generateCounter(AbstractPokemon raidBoss,
                                                   CounterPokemon counter,
                                                   PokemonMove fastMove,
                                                   PokemonMove chargeMove,
                                                   BattleMode battleMode)
    {
        try
        {
            return calculateCounter(raidBoss, counter, fastMove, chargeMove, battleMode);
        }
        catch(Exception e)
        {
            String bossName = raidBoss.getName();
            String counterName = counter.getName();
            String fastName = fastMove.getName();
            String chargeName = chargeMove.getName();

            String exceptionString = bossName + " / " + counterName + " / " + fastName + " / " + chargeName;

            throw new RuntimeException("Exception with (" + exceptionString + ")", e);
        }
    }

    /**
     * view-source:https://gamepress.gg/pokemongo/comprehensive-dps-spreadsheet
     * <p>
     * function calculateDPS(pokemon, kwargs)
     */
    private AbstractCounterInstance calculateCounter(AbstractPokemon boss,
                                                     CounterPokemon counter,
                                                     PokemonMove fastMove,
                                                     PokemonMove chargeMove,
                                                     BattleMode battleMode)
    {
        double damageIntakeX = generateDamageIntakeX(boss, counter, fastMove, chargeMove, battleMode);
        double damageIntakeY = generateDamageIntakeY(boss, counter, battleMode);

        double stamina = getStamina(counter);

        boolean BEST_FRIEND_BONUS = BEST_FRIEND_BONUS_YES;
        if (battleMode.isRocket())
        {
            BEST_FRIEND_BONUS = BEST_FRIEND_BONUS_NO;
        }

        double fastDamage = generateDamage(counter, fastMove, boss, BEST_FRIEND_BONUS, battleMode);
        double chargeDamage = generateDamage(counter, chargeMove, boss, BEST_FRIEND_BONUS, battleMode);

        double fastEnergyDelta = fastMove.getEnergyDelta(battleMode);
        double chargeEnergyDelta = -chargeMove.getEnergyDelta(battleMode);

        double dps = 0.0;
        double tdo = 0.0;

        if(battleMode.isRaid())
        {
            double fastDuration = fastMove.getDuration(battleMode) / 1000;
            double chargeDuration = chargeMove.getDuration(battleMode) / 1000;
            double chargeDamageWindow = chargeMove.getDamageWindow(battleMode) / 1000;

            if(battleMode.isRocket())
            {
                fastDuration = fastMove.getDuration(battleMode) * 0.5;
            }

            if (chargeEnergyDelta >= 100)
            {
                chargeEnergyDelta = chargeEnergyDelta + 0.5 * fastEnergyDelta + 0.5 * damageIntakeY * chargeDamageWindow;
            }

            double fastDPS = fastDamage / fastDuration;
            double fastEPS = fastEnergyDelta / fastDuration;
            double chargeDPS = chargeDamage / chargeDuration;
            double chargeEPS = chargeEnergyDelta / chargeDuration;

            /**
             * pokemon.dps = (FDPS * CEPS + CDPS * FEPS) / (CEPS + FEPS) + (CDPS - FDPS) / (CEPS + FEPS) * (1 / 2 - x / pokemon.Stm) * y;
             * pokemon.st = pokemon.Stm / y;
             * pokemon.tdo = pokemon.dps * pokemon.st;
             */

            dps = ((fastDPS * chargeEPS) + (chargeDPS * fastEPS)) / (chargeEPS + fastEPS)
                    + ((chargeDPS - fastDPS) / (chargeEPS + fastEPS)) * (0.5 - damageIntakeX / stamina) * damageIntakeY;

            if (dps > chargeDPS)
            {
                dps = chargeDPS;
            }
            if (dps < fastDPS)
            {
                dps = fastDPS;
            }

            tdo = dps * stamina / damageIntakeY;
        }

        if (battleMode.isRocket())
        {
            double fastDuration = fastMove.getDuration(battleMode) * 0.5;

            double fastDPS = fastDamage / fastDuration;
            double fastEPS = fastEnergyDelta / fastDuration;

            /**
             * 		let modFEPS = Math.max(0, FEPS - x / pokemon.st);
             * 		let totalEnergyGained = 3 * pokemon.st * modFEPS;
             * 		let discountFactor = (totalEnergyGained - 2 * CE) / totalEnergyGained;
             * 		if (discountFactor < 0 || discountFactor > 1) {
             * 			discountFactor = 0;
             *                }
             * 		CDmg = CDmg * discountFactor;
             * 		pokemon.dps = FDPS + modFEPS * CDmg / CE;
             */

            stamina = stamina / damageIntakeY;

            double fastEPSmod = fastEPS - damageIntakeX / stamina;
            if (fastEPSmod < 0.0)
            {
                fastEPSmod = 0.0;
            }

            double energyGained = 3.0 * stamina * fastEPSmod;
            double discountFactor = (energyGained - 2.0 * chargeEnergyDelta) / energyGained;
            if (discountFactor < 0.0 || discountFactor > 1.0)
            {
                discountFactor = 0.0;
            }
            chargeDamage = chargeDamage * discountFactor;

            dps = fastDPS + fastEPSmod * chargeDamage / chargeEnergyDelta;
            tdo = dps * stamina;
        }


        String fastName = fastMove.getName();
        String chargeName = chargeMove.getName();

        AbstractCounterInstance counterInstance = null;
        if(battleMode.isRaid())
        {
            counterInstance = new RaidCounterInstance(counter, fastName, chargeName, dps, tdo);
        }

        if(battleMode.isRocket())
        {
            /**
             * pkmInstance.ui_overall = Math.ceil(-pkmInstance.cmove.energyDelta / (pkmInstance.fmove.energyDelta || 1)) * pkmInstance.fmove.duration;
             */
            double activation = Math.ceil(-chargeMove.getEnergyDelta(battleMode) / (fastMove.getEnergyDelta(battleMode))) * fastMove.getDuration(battleMode);

            counterInstance = new RocketCounterInstance(counter, fastName, chargeName, tdo, activation);
        }

        return counterInstance;
    }

    /**
     * x: -pokemon.cmove.energyDelta * 0.5 + pokemon.fmove.energyDelta * 0.5,
     * y: DEFAULT_ENEMY_DPS1 / pokemon.Def
     */
    private double generateDamageIntakeX(AbstractPokemon boss, CounterPokemon counter, PokemonMove pFastMove, PokemonMove pChargeMove, BattleMode battleMode)
    {
        if (boss.getStandardFastMoves().isEmpty() || boss.getStandardChargeMoves().isEmpty())
        {
            if(battleMode.isRocket())
            {
                return - pChargeMove.getEnergyDelta(battleMode) * 0.5;
            }
            return pFastMove.getEnergyDelta(battleMode) * 0.5 - pChargeMove.getEnergyDelta(battleMode) * 0.5;
        }

        double sum = 0.0;
        int count = 0;
        for (PokemonMove fastMove : boss.getStandardFastMoves())
        {
            double fastDamage = generateDamage(boss, fastMove, counter, battleMode);
            double fastDuration = fastMove.getDuration(battleMode) / 1000.0 + 2;
            double fastEnergyDelta = fastMove.getEnergyDelta(battleMode);

            for (PokemonMove chargeMove : boss.getStandardChargeMoves())
            {
                double chargeDamage = generateDamage(boss, chargeMove, counter, battleMode);
                double chargeDuration = chargeMove.getDuration(battleMode) / 1000.0 + 2;
                double chargeEnergyDelta = -chargeMove.getEnergyDelta(battleMode);

                double factor = Math.max(1.0, 3.0 * chargeEnergyDelta / 100.0);

                double x = 0.0;
                if(battleMode.isRaid())
                {
                    x = pFastMove.getEnergyDelta(battleMode) * 0.5 - pChargeMove.getEnergyDelta(battleMode) * 0.5 +
                            0.5 * (factor * fastDamage + chargeDamage) / (factor + 1.0);
                }

                sum += x;
                count++;
            }
        }
        return (sum / count);
    }

    /**
     * var FDmg = damage(kwargs.enemy, pokemon, kwargs.enemy.fmove, kwargs.weather);
     * var CDmg = damage(kwargs.enemy, pokemon, kwargs.enemy.cmove, kwargs.weather);
     * <p>
     * var FE = kwargs.enemy.fmove.energyDelta;
     * var CE = -kwargs.enemy.cmove.energyDelta;
     * <p>
     * var FDur = kwargs.enemy.fmove.duration / 1000 + 2;
     * var CDur = kwargs.enemy.cmove.duration / 1000 + 2;
     * <p>
     * var n = Math.max(1, 3 * CE / 100);
     * return {
     * x: -pokemon.cmove.energyDelta * 0.5 + pokemon.fmove.energyDelta * 0.5 + 0.5 * (n * FDmg + CDmg) / (n + 1),
     * y: (n * FDmg + CDmg) / (n * FDur + CDur)
     * };
     */
    private double generateDamageIntakeY(AbstractPokemon boss, CounterPokemon counter, BattleMode battleMode)
    {
        if (boss.getStandardFastMoves().isEmpty() || boss.getStandardChargeMoves().isEmpty())
        {
            if(battleMode.isRocket())
            {
                return DEFAULT_BOSS_DPS * 1.5 / getDefense(counter);
            }
            return DEFAULT_BOSS_DPS / getDefense(counter);
        }

        double sum = 0.0;
        int count = 0;
        for (PokemonMove fastMove : boss.getStandardFastMoves())
        {
            double fastDamage = generateDamage(boss, fastMove, counter, battleMode);
            double fastDuration = fastMove.getDuration(battleMode) / 1000 + 2;
            double fastEnergyDelta = fastMove.getEnergyDelta(battleMode);

            if(battleMode.isRocket())
            {
                fastDuration = fastMove.getDuration(battleMode) * 0.5;
            }

            for (PokemonMove chargeMove : boss.getStandardChargeMoves())
            {
                double chargeDamage = generateDamage(boss, chargeMove, counter, battleMode);
                double chargeDuration = chargeMove.getDuration(battleMode) / 1000 + 2;
                double chargeEnergyDelta = -chargeMove.getEnergyDelta(battleMode);

                double factor = Math.max(1, 3 * chargeEnergyDelta / 100);

                double y = (factor * fastDamage + chargeDamage) / (factor * fastDuration + chargeDuration);
                if(battleMode.isRocket())
                {
                    y = fastDamage / (fastDuration - 2.0) + fastEnergyDelta / (fastDuration - 2.0) * (chargeDamage / chargeEnergyDelta);
                }

                sum += y;
                count++;
            }
        }

        return (sum / count);
    }

    private double generateDamage(AbstractPokemon attacker, PokemonMove move, AbstractPokemon defender, BattleMode battleMode)
    {
        return generateDamage(attacker, move, defender, BEST_FRIEND_BONUS_NO, battleMode);
    }


    private double generateDamage(AbstractPokemon attacker, PokemonMove move, AbstractPokemon defender, boolean bestFriendBonus, BattleMode battleMode)
    {
        final String MOVE_TYPE = move.getType();

        final String ATTACKER_TYPE1 = attacker.getType1();
        final String ATTACKER_TYPE2 = attacker.getType2();

        final String DEFENDER_TYPE1 = defender.getType1();
        final String DEFENDER_TYPE2 = defender.getType2();

        double multipliers = 1;

        if (MOVE_TYPE.equalsIgnoreCase(ATTACKER_TYPE1) || MOVE_TYPE.equalsIgnoreCase(ATTACKER_TYPE2))
        {
            multipliers *= SAME_TYPE_ATTACK_BONUS;
        }

        multipliers *= TYPE_EFFECTIVENESS.get(MOVE_TYPE).get(DEFENDER_TYPE1);
        if (DEFENDER_TYPE2 != null)
        {
            multipliers *= TYPE_EFFECTIVENESS.get(MOVE_TYPE).get(DEFENDER_TYPE2);
        }

        double attack = getAttack(attacker);
        if (attacker.isShadow())
        {
            attack *= SHADOW_ATTACK_MULTIPLIER;
        }

        if (bestFriendBonus)
        {
            attack *= BEST_FRIEND_ATTACK_BONUS;
        }

        double defense = getDefense(defender);

        //return 0.5 * atk / dmg_taker.Def * move.power * multipliers + 0.5;
        return 0.5 * attack / defense * move.getPower(battleMode) * multipliers + 0.5;
    }

    private double getAttack(AbstractPokemon pokemon)
    {
        final double CPM = getCPM(pokemon);

        return (pokemon.getBaseAttack() + pokemon.getAttackIV()) * CPM;
    }

    private double getDefense(AbstractPokemon pokemon)
    {
        final double CPM = getCPM(pokemon);

        double defense = (pokemon.getBaseDefense() + pokemon.getDefenseIV()) * CPM;

        if (!RaidBossPokemon.class.isAssignableFrom(pokemon.getClass()))
        {
            if (pokemon.isMega())
            {
                defense *= MEGA_POKEMON_STAT_MULTIPLIER;
            }
            if (pokemon.isShadow())
            {
                defense *= SHADOW_DEFENSE_MULTIPLIER;
            }
        }
        return defense;
    }

    private double getStamina(AbstractPokemon pokemon)
    {
        final double CPM = getCPM(pokemon);

        return (pokemon.getBaseStamina() + pokemon.getStaminaIV()) * CPM;
    }

    private double getCPM(AbstractPokemon pokemon)
    {
        Integer level = pokemon.getLevel();

        return dataService.getCpmFromLevel(level);
    }


    public Integer getTotalDamageForParty(List<RaidCounterInstance> party)
    {
        Integer totalDamage = 0;

        for (RaidCounterInstance pokemon : party)
        {
            totalDamage += (int) pokemon.getTdo();
        }
        return totalDamage;
    }

    public Integer getTotalTimeForParty(List<RaidCounterInstance> party)
    {
        Integer totalTime = 0;

        for (RaidCounterInstance pokemon : party)
        {
            Double damage = pokemon.getTdo();
            Double dps = pokemon.getDps();

            totalTime += (int) (damage / dps);
        }
        return totalTime;
    }
}
