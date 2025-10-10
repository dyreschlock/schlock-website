package com.schlock.website.services.database.apps.ps2;

import com.schlock.website.entities.apps.ps2.GamecubeGame;
import com.schlock.website.services.database.BaseDAO;

import java.util.List;

public interface GamecubeGameDAO extends BaseDAO<GamecubeGame>
{
    List<GamecubeGame> getAllAvailable();

    List<GamecubeGame> getAllWithNoArt();

    GamecubeGame getByGameName(String title);
}
