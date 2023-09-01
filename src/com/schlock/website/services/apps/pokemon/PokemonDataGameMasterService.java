package com.schlock.website.services.apps.pokemon;

import java.util.List;

public interface PokemonDataGameMasterService
{
    List<String> reportDifferences();

    void updateDatabase();
}
