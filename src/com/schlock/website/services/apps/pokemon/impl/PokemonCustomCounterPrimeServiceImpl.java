package com.schlock.website.services.apps.pokemon.impl;

import com.schlock.website.services.apps.pokemon.PokemonCustomCounterPrimeService;
import com.schlock.website.services.apps.pokemon.PokemonDataService;

public class PokemonCustomCounterPrimeServiceImpl extends AbstractCustomCounterServiceImpl implements PokemonCustomCounterPrimeService
{
    public PokemonCustomCounterPrimeServiceImpl(PokemonDataService dataService)
    {
        super(dataService);
    }

    protected void megas()
    {
        addCustom("Mega Venusaur", 50, 15, 15, 15, null, "Frenzy Plant, Sludge Bomb");

        addCustom("Mega Charizard Y", 44, 12, 14, 15, "Fire Spin", "Blast Burn, Dragon Claw");
        addCustom("Mega Charizard X", 24, 15, 13, 15, "Dragon Breath", "Blast Burn, Dragon Claw");

        addCustom("Mega Blastoise", 35, 15, 14, 14, "Water Gun", "Hydro Cannon, Ice Beam");

        addCustom("Mega Pidgeot", 50, 15, 15, 15, "Gust", "Brave Bird, Feather Dance");
        addCustom("Mega Slowbro", 40, 15, 14, 15, "Confusion", "Psychic");

        addCustom("Mega Beedrill", 47, 15, 12, 11, null, "Sludge Bomb, X-Scissor");

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



        addCustom("Scizor", 12, 15, 14, 15, "Bullet Punch", "Iron Head");
        addCustom("Aggron", 32, 15, 15, 13, "Iron Tail", "Heavy Slam");
        addCustom("Sharpedo", 25, 15, 15, 15, "Bite", "Crunch");

    }

    protected void shadows()
    {
        addCustom("Shadow Machamp", 50, 14, 13, 15, "Counter", "Dynamic Punch");
        addCustom("Shadow Machamp", 29, 14, 10, 11, "Counter", "Dynamic Punch");

        addCustom("Shadow Mamoswine", 40, 8, 14, 14, "Powder Snow", "Avalanche");

        addCustom("Shadow Metagross", 30, 14, 8, 12, "Bullet Punch", "Meteor Mash");
        addCustom("Shadow Metagross", 25, 10, 14, 13, "Bullet Punch", "Meteor Mash");

        addCustom("Shadow Weavile", 30, 14, 14, 1, "Ice Shard", "Avalanche");

        addCustom("Shadow Lugia", 15, 13, 10, 15, "Extrasensory", "Aeroblast+");

        addCustom("Shadow Dragonite", 40, 15, 14, 10, "Dragon Tail", "Outrage");

        addCustom("Shadow Tyranitar", 15, 14, 8, 14, "Smack Down", "Stone Edge");
    }

    protected void rocketCounters()
    {
        addCustom("Togekiss", 40, 13, 14, 14, "Charm", "Dazzling Gleam, Ancient Power");
        addCustom("Sylveon", 50, 15, 15, 13, "Charm", "Psyshock, Moonblast");


        addCustom("Melmetal", 50, 13, 15, 15, "Thunder Shock", "Rock Slide, Thunderbolt");

    }

    protected void fireSquad()
    {
        addCustom("Charizard", 30, 10, 4, 11, "Fire Spin", "Blast Burn");
        addCustom("Charizard", 29, 12, 11, 11, "Fire Spin", "Blast Burn");

        addCustom("Typhlosion", 35, 15, 14, 6, "Incinerate", "Blast Burn");
        addCustom("Typhlosion", 33, 15, 10, 12, "Incinerate", "Blast Burn");

        addCustom("Blaziken", 40, 15, 13, 14, null, "Blast Burn, Blaze Kick");
        addCustom("Blaziken", 40, 13, 13, 15, null, "Blast Burn, Blaze Kick");
        addCustom("Blaziken", 33, 10, 15, 14, null, "Blast Burn, Focus Blast");
        addCustom("Blaziken", 30, 15, 12, 15, null, "Blast Burn");
        addCustom("Blaziken", 30, 15, 14, 11, null, "Blast Burn");
        addCustom("Blaziken", 30, 14, 8, 12, null, "Blast Burn");

        addCustom("Infernape", 35, 7, 13, 8, "Fire Spin", "Blast Burn");

        addCustom("Emboar", 50, 15, 15, 14, "Ember", "Blast Burn");
        addCustom("Emboar", 30, 14, 13, 15, "Ember", "Blast Burn");

        addCustom("Chandelure", 40, 14, 12, 12);
        addCustom("Chandelure", 32, 13, 14, 7, "Fire Spin", "Overheat");

        addCustom("Reshiram", 40, 15, 11, 14);

        addCustom("Talonflame", 50, 15, 14, 14, "Incinerate", "Flame Charge, Brave Bird");

    }

    protected void waterSquad()
    {
        addCustom("Kingler", 30, 12, 15, 13, null, "Crabhammer");
        addCustom("Kingler", 20, 14, 11, 13, null, "Crabhammer");
        addCustom("Kingler", 19, 13, 15, 15, null, "Crabhammer");
        addCustom("Kingler", 16, 15, 14, 14, null, "Crabhammer");

        addCustom("Feraligatr", 30, 12, 15, 13, "Waterfall", "Hydro Cannon");
        addCustom("Feraligatr", 30, 14, 13, 8, "Waterfall", "Hydro Cannon");
        addCustom("Feraligatr", 30, 13, 9, 10, "Waterfall", "Hydro Cannon");

        addCustom("Swampert", 50, 15, 15, 15, "Water Gun", "Hydro Cannon, Muddy Water");
        addCustom("Swampert", 41, 13, 15, 15, null, "Hydro Cannon, Muddy Water");
        addCustom("Swampert", 40, 14, 14, 14, null, "Hydro Cannon, Muddy Water");
        addCustom("Swampert", 33, 15, 15, 12, null, "Hydro Cannon");
        addCustom("Swampert", 30, 15, 15, 12, null, "Hydro Cannon");
        addCustom("Swampert", 30, 14, 13, 12, null, "Hydro Cannon");
        addCustom("Swampert", 30, 12, 12, 15, null, "Hydro Cannon");
        addCustom("Swampert", 20, 15, 15, 14, null, "Hydro Cannon");
        addCustom("Swampert", 12, 15, 15, 15, null, "Hydro Cannon");

        addCustom("Kyogre", 30, 13, 12, 14, "Waterfall", "Surf");


        addCustom("Samurott", 14, 14, 14, 14, "Waterfall", "Hydro Cannon");
    }

    protected void grassSquad()
    {
        addCustom("Venusaur", 50, 15, 15, 13, null, "Frenzy Plant, Sludge Bomb");
        addCustom("Venusaur", 50, 13, 14, 14, null, "Frenzy Plant");

        addCustom("Exeggutor", 37, 14, 14, 9, "Bullet Seed", "Solar Beam");
        addCustom("Exeggutor", 35, 7, 10, 6, "Bullet Seed", "Solar Beam");
        addCustom("Alolan Exeggutor", 35, 14, 14, 15, "Bullet Seed", "Solar Beam");

        addCustom("Sceptile", 40, 15, 14, 15, "Bullet Seed", "Frenzy Plant, Dragon Claw");
        addCustom("Sceptile", 33, 11, 14, 6, "Bullet Seed", "Frenzy Plant");
        addCustom("Sceptile", 31, 13, 12, 15, "Bullet Seed", "Frenzy Plant");
        addCustom("Sceptile", 30, 13, 15, 10, "Bullet Seed", "Frenzy Plant");
        addCustom("Sceptile", 30, 8, 15, 13, "Bullet Seed", "Frenzy Plant");
        addCustom("Sceptile", 30, 15, 0, 15, "Bullet Seed", "Frenzy Plant");

        addCustom("Torterra", 40, 15, 15, 12, "Razor Leaf", "Frenzy Plant, Sand Tomb");
        addCustom("Torterra", 30, 15, 15, 14, "Razor Leaf", "Frenzy Plant");
        addCustom("Shadow Torterra", 15, 9, 8, 11, "Razor Leaf", "Frenzy Plant");
        addCustom("Shadow Torterra", 15, 0, 3, 6, "Razor Leaf", "Frenzy Plant");

        addCustom("Roserade", 40, 15, 15, 14, null, "Sludge Bomb, Grass Knot");
        addCustom("Roserade", 40, 14, 15, 14, null, "Grass Knot");
        addCustom("Roserade", 35, 15, 15, 12);
        addCustom("Roserade", 35, 14, 14, 14, "Razor Leaf", "Grass Knot");
        addCustom("Roserade", 35, 12, 7, 15, "Razor Leaf", "Grass Knot");
        addCustom("Roserade", 33, 14, 12, 15, "Razor Leaf", "Grass Knot");

        addCustom("Serperior", 30, 15, 14, 14, null, "Frenzy Plant");
        addCustom("Serperior", 20, 15, 15, 15, null, "Frenzy Plant");
    }

    protected void electricSquad()
    {
        addCustom("Raikou", 40, 13, 15, 15, "Thunder Shock", "Wild Charge");

        addCustom("Magnezone", 40, 15, 15, 15, "Spark", "Wild Charge");
        addCustom("Magnezone", 35, 12, 15, 8, "Spark", "Wild Charge");

        addCustom("Luxray", 40, 13, 14, 14, "Spark", "Psychic Fangs, Wild Charge");

        addCustom("Electivire", 40, 15, 15, 14, "Thunder Shock", "Wild Charge");
        addCustom("Electivire", 35, 13, 11, 9, "Thunder Shock", "Wild Charge");
        addCustom("Electivire", 30, 15, 15, 8, "Thunder Shock", "Wild Charge");
        addCustom("Electivire", 30, 13, 12, 13, "Thunder Shock", "Wild Charge");

        addCustom("Shadow Electivire", 15, 14, 12, 2);

        addCustom("Zekrom", 45, 14, 15, 14, null, null);
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
        addCustom("Rhyhorn", 33, 9, 15, 5, "Mud Slap", "Earthquake");
        addCustom("Rhyhorn", 30, 10, 15, 7, "Mud Slap", "Earthquake");

        addCustom("Rhyperior", 34, 14, 15, 15, "Mud Slap", "Earthquake");
        addCustom("Rhyperior", 33, 15, 9, 11, "Mud Slap", "Earthquake");

        addCustom("Groudon", 25, 14, 14, 13, "Mud Shot", "Earthquake");
        addCustom("Groudon", 15, 14, 14, 12, "Mud Shot", "Earthquake");

        addCustom("Garchomp", 50, 15, 14, 15, null, "Earth Power, Outrage");
        addCustom("Garchomp", 50, 14, 13, 15, null, "Earth Power");
        addCustom("Garchomp", 40, 15, 14, 13, "Mud Shot", "Earth Power");
        addCustom("Garchomp", 20, 14, 12, 13, "Mud Shot", "Earth Power");
        addCustom("Garchomp", 20, 12, 14, 13, "Mud Shot", "Earth Power");
        addCustom("Garchomp", 20, 13, 13, 12, "Mud Shot", "Earth Power");
        addCustom("Garchomp", 20, 13, 13, 12, "Mud Shot", "Earth Power");
        addCustom("Garchomp", 20, 12, 13, 12, "Mud Shot", "Earth Power");

        addCustom("Mamoswine", 30, 14, 15, 13, "Mud Slap", "Bulldoze");

        addCustom("Excadrill", 40, 15, 15, 12, "Mud Slap", "Drill Run");
        addCustom("Excadrill", 40, 14, 13, 14, "Mud Slap", "Drill Run");
        addCustom("Excadrill", 40, 14, 13, 15, "Mud Slap", "Drill Run");
        addCustom("Excadrill", 31, 14, 12, 13, "Mud Shot", "Drill Run");
        addCustom("Excadrill", 26, 13, 14, 13, "Mud Shot", "Drill Run");
        addCustom("Excadrill", 25, 15, 11, 12, "Mud Shot", "Drill Run");
        addCustom("Excadrill", 23, 10, 11, 13, "Mud Shot", "Drill Run");
        addCustom("Excadrill", 20, 12, 15, 14, "Mud Shot", "Drill Run");
    }

    protected void dragonSquad()
    {
        addCustom("Alolan Exeggutor", 25, 10, 13, 14, null, "Draco Meteor");
        addCustom("Alolan Exeggutor", 25, 10, 11, 13, null, "Draco Meteor");
        addCustom("Alolan Exeggutor", 25, 10, 10, 11, null, "Draco Meteor");
        addCustom("Alolan Exeggutor", 20, 15, 15, 11, null, "Draco Meteor");
        addCustom("Shadow Alolan Exeggutor", 10, 5, 2, 4, null, "Draco Meteor");

        addCustom("Dragonite", 26, 15, 15, 15, null, null);
        addCustom("Dragonite", 35, 15, 14, 13, "Dragon Breath", "Outrage, Dragon Claw");

        addCustom("Salamence", 40, 14, 12, 14, "Dragon Tail", "Outrage");
        addCustom("Salamence", 30, 13, 10, 15, "Dragon Tail", "Outrage");
        addCustom("Salamence", 30, 9, 14, 13, "Dragon Tail", "Outrage");
        addCustom("Salamence", 30, 12, 11, 8, "Dragon Tail", "Outrage");
        addCustom("Salamence", 29, 12, 13, 8, "Dragon Tail", "Outrage");
        addCustom("Salamence", 28, 13, 12, 13, "Dragon Tail", "Outrage");
        addCustom("Salamence", 27, 15, 10, 12, "Dragon Tail", "Outrage");
        addCustom("Salamence", 27, 12, 13, 12, "Dragon Tail", "Outrage");
        addCustom("Salamence", 26, 12, 13, 15, "Dragon Tail", "Outrage");
        addCustom("Salamence", 24, 12, 6, 3, "Dragon Tail", "Outrage");

        addCustom("Rayquaza", 40, 14, 15, 11, "Dragon Tail", "Outrage");
        addCustom("Rayquaza", 40, 12, 12, 14, "Dragon Tail", "Outrage");
        addCustom("Rayquaza", 30, 15, 15, 14, "Dragon Tail", "Outrage");

        addCustom("Garchomp", 15, 15, 13, 14, "Dragon Tail", "Outrage");

        addCustom("Dialga", 45, 14, 15, 15, "Dragon Breath", "Iron Head, Draco Meteor");
    }

    protected void psychicSquad()
    {
        addCustom("Mewtwo", 40, 13, 13, 15, null, "Psystrike, Ice Beam");
        addCustom("Mewtwo", 35, 14, 14, 15, null, null);
        addCustom("Mewtwo", 34, 15, 15, 12, null, null);
        addCustom("Mewtwo", 35, 15, 13, 11, null, "Psystrike");
        addCustom("Mewtwo", 25, 15, 15, 14, null, "Psystrike");
        addCustom("Mewtwo", 25, 14, 15, 14, null, "Psystrike");
        addCustom("Mewtwo", 25, 13, 11, 11, null, "Psystrike");

        addCustom("Gardevoir", 35, 12, 15, 12);
    }

    protected void ghostDarkSquad()
    {
        addCustom("Tyranitar", 40, 12, 13, 15, "Bite", "Crunch");

        addCustom("Gengar", 50, 14, 14, 14, "Shadow Claw", "Shadow Ball, Shadow Punch");
        addCustom("Gengar", 40, 15, 14, 15, "Shadow Claw", "Shadow Ball");

        addCustom("Umbreon", 50, 14, 15, 15, "Snarl", "Last Resort, Foul Play");
        addCustom("Umbreon", 20, 14, 15, 14, "Snarl", "Psychic");

        addCustom("Giratina (Origin Forme)", 40, 15, 14, 13, "Shadow Claw", "Shadow Ball");

        addCustom("Darkrai", 40, 15, 15, 15, "Snarl", "Shadow Ball");

        addCustom("Hydreigon", 50, 15, 15, 12, "Bite", "Brutal Swing");
        addCustom("Hydreigon", 50, 14, 15, 12, "Bite", "Brutal Swing");

        addCustom("Deoxys (Attack Forme)", 34, 13, 15, 14);
    }

    protected void fightSquad()
    {
        addCustom("Lucario", 35, 13, 14, 13, "Counter", "Aura Sphere, Power-Up Punch");

        addCustom("Machamp", 40, 15, 15, 15, "Counter", "Payback, Dynamic Punch");
        addCustom("Machamp", 40, 14, 15, 15, "Counter", "Dynamic Punch");
        addCustom("Machamp", 40, 12, 15, 15, "Counter", "Dynamic Punch");
        addCustom("Machamp", 39, 12, 14, 14, "Counter", "Dynamic Punch");
        addCustom("Machamp", 35, 15, 14, 13, "Counter", "Dynamic Punch");
        addCustom("Machamp", 35, 14, 13, 14, "Counter", "Dynamic Punch");
        addCustom("Machamp", 28, 15, 15, 15, "Counter", "Payback, Dynamic Punch");

        addCustom("Conkeldurr", 30, 15, 12, 15, "Counter", "Dynamic Punch");

    }

    protected void metalPoisonSquad()
    {
        addCustom("Metagross", 40, 13, 12, 10, "Bullet Punch", "Meteor Mash, Psychic");
        addCustom("Metagross", 33, 14, 15, 10, "Bullet Punch", "Meteor Mash");
        addCustom("Metagross", 31, 15, 14, 13, "Bullet Punch", "Meteor Mash");
        addCustom("Metagross", 30, 15, 14, 14, "Bullet Punch", "Meteor Mash");
        addCustom("Metagross", 30, 13, 12, 14, "Bullet Punch", "Meteor Mash");
        addCustom("Metagross", 29, 14, 7, 13, "Bullet Punch", "Meteor Mash");
        addCustom("Metagross", 29, 15, 10, 7, "Bullet Punch", "Meteor Mash");
        addCustom("Metagross", 28, 13, 15, 7, "Bullet Punch", "Meteor Mash");
        addCustom("Metagross", 28, 13, 7, 10, "Bullet Punch", "Meteor Mash");
        addCustom("Metagross", 28, 5, 11, 15, "Bullet Punch", "Meteor Mash");

    }

    protected void flightSquad()
    {
        addCustom("Moltres", 30, 10, 15, 15, "Wing Attack", "Sky Attack");
        addCustom("Moltres", 30, 12, 15, 11, "Wing Attack", "Sky Attack");

        addCustom("Rayquaza", 35, 13, 15, 13, "Air Slash", "Hurricane");
        addCustom("Rayquaza", 30, 15, 15, 15, "Air Slash", "Aerial Ace");
        addCustom("Rayquaza", 25, 11, 11, 15, "Air Slash", "Aerial Ace");
        addCustom("Rayquaza", 25, 13, 11, 11, "Air Slash", "Aerial Ace");
        addCustom("Rayquaza", 20, 13, 13, 15, "Air Slash", "Hurricane");
        addCustom("Rayquaza", 20, 15, 12, 13, "Air Slash", "Hurricane");

        addCustom("Honchkrow", 35, 15, 12, 12, "Peck", "Sky Attack");
        addCustom("Honchkrow", 32, 15, 13, 10, "Peck", "Sky Attack");
        addCustom("Honchkrow", 29, 11, 15, 13, "Peck", "Sky Attack");
        addCustom("Honchkrow", 28, 13, 14, 11, "Peck", "Sky Attack");
        addCustom("Honchkrow", 29, 7, 12, 5, "Peck", "Sky Attack");
        addCustom("Honchkrow", 27, 12, 12, 12, "Peck", "Sky Attack");

    }
}
