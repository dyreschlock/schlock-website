package com.schlock.website.services.apps.pocket;

import com.schlock.website.entities.apps.pocket.PocketCore;
import com.schlock.website.entities.apps.pocket.PocketGame;

import java.util.List;

public interface PocketDataService
{
    List<PocketGame> getGames();

    List<PocketCore> getCores();
}
