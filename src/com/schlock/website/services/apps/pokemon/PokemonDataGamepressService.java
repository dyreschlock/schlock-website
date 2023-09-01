package com.schlock.website.services.apps.pokemon;

import java.util.HashMap;
import java.util.List;

public interface PokemonDataGamepressService
{
    HashMap<Integer, Double> getCpmData();

    List<String> reportDifferences();

    void updateDatabase();
}
