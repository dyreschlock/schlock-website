package com.schlock.website.services.apps.ps2;

import com.schlock.website.entities.apps.ps2.PlaystationGame;

import java.util.List;

public interface PlaystationImageService
{
    String generateImageHTMLFromGames(List<PlaystationGame> games);
}
