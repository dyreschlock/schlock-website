package com.schlock.website.services.apps.pocket;

import com.schlock.website.entities.apps.pocket.PocketGame;

import java.util.List;

public interface PocketImageService
{
    String generateImageHTMLFromGames(List<PocketGame> games);
}
