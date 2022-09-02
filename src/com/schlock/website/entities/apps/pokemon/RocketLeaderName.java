package com.schlock.website.entities.apps.pokemon;

import java.util.ArrayList;
import java.util.List;

public enum RocketLeaderName
{
    ARLO(
            "Bagon", "Blastoise", "Charizard", "Steelix", "Scizor", "Dragonite", "Salamence"
    ),

    CLIFF(
            "Teddiursa", "Electivire", "Omastar", "Marowak", "Tyranitar", "Swampert", "Torterra"
    ),

    SIERRA(
            "Poliwag", "Exeggutor", "Lapras", "Sharpedo", "Houndoom", "Swampert", "Shiftry"
    ),

//    GIOVANNI(
//            "Persian", "Kingler", "Nidoking", "Rhyperior", "Lugia"
//    ),

    EXTRA(
            "Charizard", "Blastoise", "Venusaur", "Snorlax", "Gyarados", "Lapras", "Dragonite", "Poliwrath",
            "Gardevoir", "Gliscor", "Garchomp", "Salamence"
    );

    private final List<String> pokemon = new ArrayList<String>();

    RocketLeaderName(String... pokemonNames)
    {
        for (String name : pokemonNames)
        {
            pokemon.add(name);
        }
    }

    public List<String> pokemonNames()
    {
        return pokemon;
    }
}
