package com.schlock.website.entities.apps.pokemon;

import java.util.ArrayList;
import java.util.List;

public enum RocketLeaderName
{
    ARLO(
            "Nidoran\u2642", "Crobat", "Steelix", "Cradily", "Charizard", "Scizor", "Armaldo"
//            "Mawile", "Salamence", "Staraptor", "Charizard", "Dragonite", "Scizor", "Granbull"
//            "Bagon", "Blastoise", "Charizard", "Steelix", "Scizor", "Dragonite", "Salamence"
    ),

    CLIFF(
            "Magnemite", "Venusaur", "Omastar", "Pinsir", "Sharpedo", "Tyranitar", "Camerupt"
//            "Machop", "Pinsir", "Crobat", "Amoonguss", "Tyranitar", "Aerodactyl", "Camerupt"
//            "Teddiursa", "Electivire", "Omastar", "Marowak", "Tyranitar", "Swampert", "Torterra"
    ),

    SIERRA(
            "Beldum", "Sharpedo", "Lapras", "Flygon", "Houndoom", "Shiftry", "Alakazam"
//            "Sableye", "Flygon", "Honchkrow", "Cacturne", "Cradily", "Houndoom", "Snorlax"
//            "Poliwag", "Exeggutor", "Lapras", "Sharpedo", "Houndoom", "Swampert", "Shiftry"
    ),

    GIOVANNI(
            "Persian", "Honchkrow", "Steelix", "Rhyperior", "Registeel"
//            "Persian", "Kingler", "Nidoking", "Rhyperior", "Lugia"
    ),

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
