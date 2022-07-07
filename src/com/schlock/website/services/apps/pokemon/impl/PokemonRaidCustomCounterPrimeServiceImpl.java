package com.schlock.website.services.apps.pokemon.impl;

import com.schlock.website.services.apps.pokemon.PokemonRaidCustomCounterPrimeService;
import com.schlock.website.services.apps.pokemon.PokemonRaidDataService;

public class PokemonRaidCustomCounterPrimeServiceImpl extends AbstractRaidCustomCounterServiceImpl implements PokemonRaidCustomCounterPrimeService
{
    public PokemonRaidCustomCounterPrimeServiceImpl(PokemonRaidDataService dataService)
    {
        super(dataService);
    }

    protected void megas()
    {
        addCustom("Mega Venusaur", 50, 15, 15, 15, null, "Frenzy Plant, Sludge Bomb");

        addCustom("Mega Charizard Y", 44, 12, 14, 15, "Fire Spin", "Blast Burn, Dragon Claw");
        addCustom("Mega Charizard X", 44, 12, 14, 15, "Fire Spin", "Blast Burn, Dragon Claw");

        addCustom("Mega Blastoise", 35, 15, 14, 14, "Water Gun", "Hydro Cannon, Ice Beam");

        addCustom("Mega Beedrill", 47, 15, 12, 11, null, "Sludge Bomb, X-Scissor");

        addCustom("Mega Pidgeot", 50, 15, 15, 15, "Gust", "Brave Bird, Feather Dance");
        addCustom("Mega Slowbro", 40, 15, 14, 15, "Confusion", "Psychic");

        addCustom("Mega Gengar", 50, 15, 14, 15, "Shadow Claw", "Shadow Ball");

        addCustom("Mega Kangaskhan", 25, 14, 15, 15, "Mud Slap", "Earthquake");
        addCustom("Mega Kangaskhan", 20, 14, 10, 15, "Mud Slap", "Crunch");

        addCustom("Mega Gyarados", 50, 15, 14, 14, "Waterfall", "Aqua Tail, Hydro Pump");
        addCustom("Mega Gyarados", 40, 15, 15, 15, "Bite", "Crunch");

        addCustom("Mega Aerodactyl", 45, 15, 13, 14, "Rock Throw", "Rock Slide");
        addCustom("Mega Ampharos", 39, 15, 15, 15, null, "Dragon Pulse, Zap Cannon");
        addCustom("Mega Steelix", 30, 13, 13, 12, "Iron Tail", "Heavy Slam");
        addCustom("Mega Houndoom", 30, 14, 15, 14, "Snarl", "Foul Play");

        addCustom("Mega Manectric", 40, 15, 13, 15, "Charge Beam", "Wild Charge");
        addCustom("Mega Altaria", 50, 14, 15, 13, "Dragon Breath", "Moonblast, Sky Attack");
        addCustom("Mega Altaria", 25, 2, 12, 15, "Dragon Breath", "Moonblast, Sky Attack");
        addCustom("Mega Absol", 40, 15, 12, 13, "Snarl", "Dark Pulse");
        addCustom("Mega Latias", 25, 11, 11, 14);
        addCustom("Mega Latios", 40, 15, 13, 15);

        addCustom("Mega Lopunny", 40, 13, 15, 15, "Low Kick", "Focus Blast");
        addCustom("Mega Lopunny", 40, 14, 15, 13, "Low Kick", "Focus Blast");
        addCustom("Mega Abomasnow", 45, 14, 14, 15, "Powder Snow", "Weather Ball Ice, Energy Ball");
    }

    protected void shadows()
    {
        addCustom("Shadow Machamp", 50, 14, 13, 15, "Counter", "Dynamic Punch");
        addCustom("Shadow Mamoswine", 40, 8, 14, 14, "Powder Snow", "Avalanche");
        addCustom("Shadow Metagross", 30, 14, 8, 12, "Bullet Punch", "Meteor Mash");
        addCustom("Shadow Weavile", 30, 14, 14, 1, "Ice Shard", "Avalanche");
        addCustom("Shadow Metagross", 25, 10, 14, 13, "Bullet Punch", "Meteor Mash");
        addCustom("Shadow Machamp", 29, 14, 10, 11, "Counter", "Dynamic Punch");
        addCustom("Shadow Lugia", 15, 13, 10, 15, "Extrasensory", "Aeroblast+");
        addCustom("Shadow Dragonite", 40, 15, 14, 10, "Dragon Tail", "Outrage");

        addCustom("Shadow Tyranitar", 15, 14, 8, 14, "Smack Down", "Stone Edge");
    }

    protected void rocketCounters()
    {
        addCustom("Togekiss", 40, 13, 14, 14, "Charm", "Dazzling Gleam, Ancient Power");
        addCustom("Melmetal", 50, 13, 15, 15, "Thunder Shock", "Rock Slide, Thunderbolt");

        addCustom("Mewtwo", 40, 13, 13, 15, "Confusion", "Psystrike, Ice Beam");

    }

    protected void fireSquad()
    {
        addCustom("Emboar", 50, 15, 15, 14, "Ember", "Blast Burn");
        addCustom("Chandelure", 40, 14, 12, 12);
        addCustom("Blaziken", 40, 13, 13, 15, null, "Blast Burn, Blaze Kick");
        addCustom("Chandelure", 32, 13, 14, 7, "Fire Spin", "Overheat");
        addCustom("Typhlosion", 35, 15, 14, 6, "Incinerate", "Blast Burn");
        addCustom("Typhlosion", 33, 15, 10, 12, "Incinerate", "Blast Burn");
        addCustom("Blaziken", 33, 10, 15, 14, "Fire Spin", "Blast Burn, Focus Blast");
        addCustom("Charizard", 30, 10, 4, 11, "Fire Spin", "Blast Burn");
        addCustom("Reshiram", 20, 15, 11, 14);
    }

    protected void waterSquad()
    {
        addCustom("Swampert", 50, 15, 15, 15, "Water Gun", "Hydro Cannon, Muddy Water");
        addCustom("Swampert", 41, 13, 15, 15, null, "Hydro Cannon, Muddy Water");

    }

    protected void grassSquad()
    {
        addCustom("Sceptile", 40, 15, 14, 15, "Bullet Seed", "Frenzy Plant, Dragon Claw");
        addCustom("Torterra", 40, 15, 15, 12, "Razor Leaf", "Frenzy Plant, Sand Tomb");
        addCustom("Venusaur", 50, 15, 15, 13, null, "Frenzy Plant, Sludge Bomb");

    }

    protected void electricSquad()
    {
        addCustom("Zekrom", 45, 14, 15, 14, "Charge Beam", "Wild Charge");
        addCustom("Raikou", 40, 13, 15, 15, "Thunder Shock", "Wild Charge");
        addCustom("Magnezone", 40, 15, 15, 15, "Spark", "Wild Charge");
        addCustom("Electivire", 40, 15, 15, 14, "Thunder Shock", "Wild Charge");
        addCustom("Magnezone", 35, 12, 15, 8, "Spark", "Wild Charge");
        addCustom("Luxray", 40, 13, 14, 14, "Spark", "Psychic Fangs, Wild Charge");
        addCustom("Electivire", 35, 13, 11, 9, "Thunder Shock", "Wild Charge");
        addCustom("Electivire", 30, 15, 15, 8, "Thunder Shock", "Wild Charge");
        addCustom("Electivire", 30, 13, 12, 13, "Thunder Shock", "Wild Charge");

    }

    protected void iceSquad()
    {
        addCustom("Galarian Darmanitan", 50, 15, 15, 15, "Ice Fang", "Avalanche");
        addCustom("Galarian Darmanitan", 20, 15, 15, 12, "Ice Fang", "Avalanche");
        addCustom("Galarian Darmanitan", 12, 14, 13, 14, "Ice Fang", "Avalanche");

        addCustom("Mamoswine", 45, 14, 14, 15, null, "Avalanche, Bulldoze");
        addCustom("Mamoswine", 35, 14, 15, 13, "Powder Snow", "Avalanche");
        addCustom("Mamoswine", 35, 14, 11, 15, "Powder Snow", "Avalanche");
        addCustom("Mamoswine", 33, 15, 12, 14, "Powder Snow", "Avalanche");
        addCustom("Mamoswine", 32, 15, 12, 13, "Powder Snow", "Avalanche");
        addCustom("Mamoswine", 32, 11, 13, 14, "Powder Snow", "Avalanche");

        addCustom("Shadow Weavile", 30, 14, 14, 1, null, "Avalanche, Foul Play");

        addCustom("Walrein", 20, 15, 14, 15, "Powder Snow", "Icicle Spear");
        addCustom("Glaceon", 10, 15, 15, 14, "Frost Breath", "Water Pulse, Avalanche");
        addCustom("Avalugg", 10, 14, 13, 12, "Ice Fang", "Avalanche");

    }

    protected void rockSquad()
    {
        addCustom("Tyranitar", 40, 14, 8, 12, "Smack Down", "Stone Edge, Crunch");
        addCustom("Tyranitar", 40, 15, 15, 12, "Smack Down", "Stone Edge");

        addCustom("Rampardos", 50, 15, 14, 15, "Smack Down", "Rock Slide");
        addCustom("Rampardos", 50, 15, 15, 14, "Smack Down", "Rock Slide");
        addCustom("Rampardos", 40, 11, 14, 8, "Smack Down", "Rock Slide");
        addCustom("Rampardos", 30, 15, 15, 13, "Smack Down", "Rock Slide");

        addCustom("Rhyperior", 48, 14, 15, 14, "Smack Down", "Rock Wrecker");
        addCustom("Rhyperior", 40, 15, 15, 11, "Smack Down", "Rock Wrecker");
        addCustom("Rhyperior", 34, 14, 9, 14, "Smack Down", "Rock Wrecker");
        addCustom("Rhyperior", 30, 15, 13, 5, "Smack Down", "Rock Wrecker");
        addCustom("Rhyperior", 30, 15, 10, 8, "Smack Down", "Rock Wrecker");
        addCustom("Rhyperior", 30, 10, 12, 8, "Smack Down", "Rock Wrecker");
        addCustom("Rhyperior", 30, 8, 15, 7, "Smack Down", "Rock Wrecker");

        addCustom("Gigalith", 20, 13, 14, 14, "Smack Down", "Rock Slide");

    }

    protected void groundSquad()
    {
        addCustom("Garchomp", 50, 14, 13, 15, "Mud Shot", "Earth Power");
        addCustom("Rhyperior", 34, 14, 15, 15, "Mud Slap", "Earthquake");
        addCustom("Rhyperior", 33, 15, 9, 11, "Mud Slap", "Earthquake");
        addCustom("Excadrill", 40, 15, 15, 12, "Mud Slap", "Drill Run");
        addCustom("Mamoswine", 30, 14, 15, 13, "Mud Slap", "Bulldoze");
        addCustom("Groudon", 25, 14, 14, 13, "Mud Shot", "Earthquake");
        addCustom("Groudon", 15, 14, 14, 12, "Mud Shot", "Earthquake");

        addCustom("Rhyhorn", 33, 9, 15, 5, "Mud Slap", "Earthquake");
        addCustom("Excadrill", 31, 14, 12, 13, "Mud Shot", "Drill Run");
        addCustom("Excadrill", 30, 14, 13, 14, "Mud Shot", "Drill Run");

    }

    protected void dragonSquad()
    {
        addCustom("Dialga", 45, 14, 15, 15, "Dragon Breath", "Iron Head, Draco Meteor");
        addCustom("Garchomp", 50, 15, 14, 15, null, "Earth Power, Outrage");

    }

    protected void psychicSquad()
    {
        addCustom("Mewtwo", 40, 13, 13, 15, null, "Psystrike, Ice Beam");

    }

    protected void ghostDarkSquad()
    {
        addCustom("Tyranitar", 40, 12, 13, 15, "Bite", "Crunch");

        addCustom("Gengar", 50, 14, 14, 14, "Shadow Claw", "Shadow Ball, Shadow Punch");
        addCustom("Gengar", 40, 15, 14, 15, "Shadow Claw", "Shadow Ball");

    }

    protected void fightSquad()
    {
        addCustom("Lucario", 35, 13, 14, 13, "Counter", "Aura Sphere, Power-Up Punch");

    }

    protected void metalPoisonSquad()
    {
        addCustom("Metagross", 40, 13, 12, 10, "Bullet Punch", "Meteor Mash, Psychic");

    }
}
