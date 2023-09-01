package com.schlock.website.services.apps.pokemon;

import java.util.HashMap;

public interface PokemonDataGamepressService extends PokemonDataExternalReadingService
{
    HashMap<Integer, Double> getCpmData();
}
