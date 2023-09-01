package com.schlock.website.services.apps.pokemon;

import java.util.List;

public interface PokemonDataExternalReadingService
{
    List<String> reportDifferences();

    void updateDatabase();
}
