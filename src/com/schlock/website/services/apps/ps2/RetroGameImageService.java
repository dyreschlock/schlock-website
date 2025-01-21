package com.schlock.website.services.apps.ps2;

import com.schlock.website.entities.apps.ps2.RetroGame;

import java.util.List;

public interface RetroGameImageService
{
    String generateImageHTMLFromGames(List<RetroGame> games);
}
