package com.schlock.website.services.apps.pokemon;

import java.util.HashMap;
import java.util.List;

public interface PokemonDataExternalReadingService
{
    HashMap<Integer, Double> getCpmData();

    List<String> reportDifferences();

    List<String> updateAll();

    List<String> updateMovesAddNew();
    List<String> updateMovesMainStats();
    List<String> updateMovesPvpStats();

    List<String> updatePokemonAddNew();
    List<String> updatePokemonStats();
    List<String> updatePokemonMoves();
}
