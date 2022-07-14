package com.schlock.website.services.apps.pokemon.impl;

import com.schlock.website.services.apps.pokemon.PokemonRaidCustomCounterSecondService;
import com.schlock.website.services.apps.pokemon.PokemonRaidDataService;

public class PokemonRaidCustomCounterSecondServiceImpl extends AbstractRaidCustomCounterServiceImpl implements PokemonRaidCustomCounterSecondService
{
    public PokemonRaidCustomCounterSecondServiceImpl(PokemonRaidDataService dataService)
    {
        super(dataService);
    }

    protected void megas()
    {
        addCustom("Mega Venusaur", 50, 15, 14, 15, null, "Frenzy Plant, Sludge Bomb");
        addCustom("Mega Venusaur", 50, 15, 15, 14, null, "Frenzy Plant, Sludge Bomb");

        addCustom("Mega Charizard Y", 50, 15, 15, 15, "Fire Spin", "Blast Burn, Dragon Claw");
        addCustom("Mega Charizard X", 15, 15, 15, 14, "Dragon Breath", "Blast Burn, Dragon Claw");

        addCustom("Mega Blastoise", 25, 14, 15, 14, "Water Gun", "Hydro Cannon, Ice Beam");

        addCustom("Mega Beedrill", 40, 11, 12, 7, "Poison Jab", "X-Scissor, Sludge Bomb");

        addCustom("Mega Pidgeot", 50, 15, 15, 14, "Gust", "Brave Bird, Feather Dance");

        addCustom("Mega Slowbro", 40, 15, 13, 15, "Confusion", "Psychic");
    }

    protected void shadows()
    {
        addCustom("Shadow Machamp", 50, 15, 12, 10, "Counter", "Dynamic Punch");

        addCustom("Shadow Mamoswine", 40, 10, 13, 0, "Powder Snow", "Avalanche");

        addCustom("Shadow Metagross", 30, 15, 10, 3, "Bullet Punch", "Meteor Mash");
        addCustom("Shadow Metagross", 25, 1, 4, 2, "Bullet Punch", "Meteor Mash");
        addCustom("Shadow Metagross", 15, 7, 13, 15, "Bullet Punch", "Meteor Mash");

        addCustom("Shadow Weavile", 31, 4, 13, 11, "Ice Shard", "Avalanche, Foul Play");

        addCustom("Shadow Lugia", 15, 10, 10, 13, null, "Aeroblast+");

        addCustom("Shadow Dragonite", 14, 5, 14, 11, "Dragon Breath", "Draco Meteor");

        addCustom("Shadow Venusaur", 15, 13, 11, 7, null, "Frenzy Plant");
    }

    protected void rocketCounters()
    {

    }

    protected void fireSquad()
    {

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

    }

    protected void dragonSquad()
    {

    }

    protected void psychicSquad()
    {

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

        addCustom("Togekiss", 40, 13, 15, 13, "Charm", "Dazzling Gleam, Ancient Power");

        addCustom("Conkeldurr", 30, 15, 14, 14, "Counter", "Dynamic Punch");
    }

    protected void metalPoisonSquad()
    {

    }

    protected void flightSquad()
    {

    }
}
