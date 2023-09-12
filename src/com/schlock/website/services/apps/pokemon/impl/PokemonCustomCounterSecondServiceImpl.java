package com.schlock.website.services.apps.pokemon.impl;

import com.schlock.website.services.apps.pokemon.PokemonCustomCounterSecondService;
import com.schlock.website.services.apps.pokemon.PokemonDataService;
import com.schlock.website.services.database.apps.pokemon.PokemonDataDAO;

public class PokemonCustomCounterSecondServiceImpl extends AbstractCustomCounterServiceImpl implements PokemonCustomCounterSecondService
{
    public PokemonCustomCounterSecondServiceImpl(PokemonDataService dataService, PokemonDataDAO pokemonDAO)
    {
        super(dataService, pokemonDAO);
    }

    protected void megas()
    {
        addCustom("Mega Venusaur", 50, 15, 14, 15, null, "Frenzy Plant, Sludge Bomb");
        addCustom("Mega Venusaur", 50, 15, 15, 14, null, "Frenzy Plant, Sludge Bomb");

        addCustom("Mega Charizard Y", 50, 15, 15, 15, "Fire Spin", "Blast Burn, Dragon Claw");
        addCustom("Mega Charizard X", 15, 15, 15, 14, "Dragon Breath", "Blast Burn, Dragon Claw");

        addCustom("Mega Blastoise", 25, 14, 15, 14, "Water Gun", "Hydro Cannon, Ice Beam");

        addCustom("Mega Beedrill", 40, 11, 12, 7, "Poison Jab", "X-Scissor, Sludge Bomb");
        addCustom("Mega Scizor", 45, 15, 15, 14);

        addCustom("Mega Gengar", 50, 15, 15, 14);

        addCustom("Mega Pidgeot", 50, 15, 15, 14, "Gust", "Brave Bird, Feather Dance");

        addCustom("Mega Slowbro", 40, 15, 13, 15, "Confusion", "Psychic");


        addCustom("Mega Sceptile", 40, 15, 15, 13);
        addCustom("Mega Blaziken", 40, 15, 14, 13);
        addCustom("Mega Swampert", 50, 15, 15, 15);
        addCustom("Mega Swampert", 41, 14, 15, 14);
    }

    protected void shadows()
    {
        addCustom("Shadow Machamp", 50, 15, 12, 10, "Counter", "Dynamic Punch");

        addCustom("Shadow Mamoswine", 40, 10, 13, 0, "Powder Snow", "Avalanche");

        addCustom("Shadow Metagross", 40, 15, 10, 3, "Bullet Punch", "Meteor Mash");
        addCustom("Shadow Metagross", 25, 1, 4, 2, "Bullet Punch", "Meteor Mash");
        addCustom("Shadow Metagross", 15, 7, 13, 15, "Bullet Punch", "Meteor Mash");

        addCustom("Shadow Weavile", 31, 4, 13, 11, "Ice Shard", "Avalanche, Foul Play");

        addCustom("Shadow Lugia", 15, 10, 10, 13, null, "Aeroblast+");

        addCustom("Shadow Dragonite", 14, 5, 14, 11, "Dragon Breath", "Draco Meteor");

        addCustom("Shadow Venusaur", 15, 13, 11, 7, null, "Frenzy Plant");
    }

    protected void rocketCounters()
    {
        addCustom("Vikavolt", 40, 13, 15, 15, "Bug Bite", "X-Scissor");
        addCustom("Yanmega", 27, 13, 4, 7, "Bug Bite", "Bug Buzz");

        addCustom("Togekiss", 40, 13, 15, 13, "Charm", "Dazzling Gleam, Ancient Power");

    }

    protected void fireSquad()
    {
        addCustom("Charizard", 20, 15, 15, 13, "Fire Spin", "Blast Burn");

        addCustom("Shadow Typhlosion", 10, 2, 6, 7, "Incinerate", "Blast Burn");

        addCustom("Blaziken", 40, 15, 14, 13, null, "Blast Burn, Blaze Kick");
        addCustom("Blaziken", 40, 14, 15, 15, null, "Blast Burn");
        addCustom("Blaziken", 30, 14, 12, 14, null, "Blast Burn");
        addCustom("Blaziken", 30, 15, 13, 7, null, "Blast Burn");
        addCustom("Blaziken", 30, 13, 11, 12, null, "Blast Burn");
        addCustom("Blaziken", 30, 11, 6, 15, null, "Blast Burn");
        addCustom("Blaziken", 27, 12, 13, 13, null, "Blast Burn");
        addCustom("Blaziken", 26, 0, 12, 13, null, "Blast Burn");

        addCustom("Infernape", 30, 3, 6, 6, "Fire Spin", "Blast Burn");
        addCustom("Infernape", 27, 13, 7, 11, "Fire Spin", "Blast Burn");

        addCustom("Emboar", 50, 14, 15, 14, "Ember", "Blast Burn");
        addCustom("Emboar", 30, 14, 13, 14, "Ember", "Blast Burn");
        addCustom("Emboar", 24, 15, 13, 14, "Ember", "Blast Burn");

        addCustom("Chandelure", 50, 15, 14, 15);
        addCustom("Chandelure", 40, 15, 12, 13);
        addCustom("Chandelure", 29, 14, 14, 13);

        addCustom("Reshiram", 40, 10, 13, 14, "Fire Fang", "Fusion Flare");
    }

    protected void waterSquad()
    {

    }

    protected void grassSquad()
    {
        addCustom("Venusaur", 50, 13, 12, 12, null, "Frenzy Plant");

    }

    protected void electricSquad()
    {

    }

    protected void iceSquad()
    {

    }

    protected void rockSquad()
    {

    }

    protected void groundSquad()
    {
        addCustom("Garchomp", 50, 14, 14, 15, null, "Earth Power, Outrage");
        addCustom("Garchomp", 50, 15, 12, 13, null, "Earth Power, Outrage");
        addCustom("Garchomp", 40, 15, 15, 14, null, "Earth Power, Outrage");

        addCustom("Excadrill", 40, 15, 15, 15, "Mud Slap", "Drill Run");
        addCustom("Excadrill", 40, 15, 15, 14, "Mud Slap", "Drill Run");
        addCustom("Excadrill", 40, 14, 14, 15, "Mud Slap", "Drill Run");
    }

    protected void dragonSquad()
    {
        addCustom("Dialga", 46, 15, 15, 15);
    }

    protected void psychicSquad()
    {
        addCustom("Mewtwo", 37, 14, 14, 15, null, "Psystrike, Ice Beam");
        addCustom("Mewtwo", 36, 15, 15, 14, null, "Psystrike, Ice Beam");
        addCustom("Mewtwo", 35, 14, 15, 14, null, "Psystrike");
        addCustom("Mewtwo", 35, 14, 14, 15, null, "Psystrike");
        addCustom("Mewtwo", 25, 11, 15, 15, null, "Psystrike");
    }

    protected void ghostDarkSquad()
    {
        addCustom("Gengar", 50, 15, 15, 14, "Shadow Claw", "Shadow Punch, Shadow Ball");
        addCustom("Gengar", 50, 12, 12, 15, "Shadow Claw", "Shadow Ball");
        addCustom("Gengar", 50, 15, 15, 15, "Shadow Claw", "Shadow Ball");
        addCustom("Gengar", 33, 14, 15, 14, "Shadow Claw", "Shadow Ball");
        addCustom("Gengar", 33, 13, 12, 12, "Shadow Claw", "Shadow Ball");
        addCustom("Gengar", 34, 7, 14, 8, "Lick", "Shadow Ball");

        addCustom("Giratina (Origin Forme)", 40, 15, 14, 13, "Shadow Claw", "Shadow Ball");
        addCustom("Giratina (Origin Forme)", 30, 15, 15, 11, "Shadow Claw", "Shadow Ball");
        addCustom("Giratina (Origin Forme)", 25, 10, 13, 14, "Shadow Claw", "Shadow Ball");
        addCustom("Giratina (Origin Forme)", 25, 11, 10, 11, "Shadow Claw", "Shadow Ball");

        addCustom("Hydreigon", 50, 14, 15, 15, "Bite", "Brutal Swing");
        addCustom("Hydreigon", 50, 15, 10, 1, "Bite", "Brutal Swing");
    }

    protected void fightSquad()
    {
        addCustom("Machamp", 40, 15, 14, 14, "Counter", "Payback, Dynamic Punch");
        addCustom("Machamp", 40, 12, 14, 12, "Counter", "Dynamic Punch");
        addCustom("Machamp", 36, 14, 15, 15, "Counter", "Dynamic Punch");
        addCustom("Machamp", 35, 15, 15, 13, "Counter", "Rock Slide, Dynamic Punch");
        addCustom("Machamp", 35, 14, 14, 15, "Counter", "Dynamic Punch");
        addCustom("Machamp", 35, 14, 15, 12, "Counter", "Dynamic Punch");

        addCustom("Lucario", 40, 14, 13, 13, "Counter", "Power-Up Punch, Aura Sphere");
        addCustom("Lucario", 32, 15, 11, 13, "Counter", "Power-Up Punch, Aura Sphere");

        addCustom("Conkeldurr", 30, 15, 14, 14, "Counter", "Dynamic Punch");
    }

    protected void metalPoisonSquad()
    {
        addCustom("Metagross", 40, 15, 15, 13, "Bullet Punch", "Meteor Mash, Psychic");
        addCustom("Metagross", 40, 15, 12, 15, "Bullet Punch", "Meteor Mash, Psychic");

    }

    protected void flightSquad()
    {
    }
}
