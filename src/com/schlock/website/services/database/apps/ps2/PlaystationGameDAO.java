package com.schlock.website.services.database.apps.ps2;

import com.schlock.website.entities.apps.ps2.PlaystationGame;
import com.schlock.website.services.database.BaseDAO;

public interface PlaystationGameDAO extends BaseDAO<PlaystationGame>
{
    PlaystationGame getByGameId(String gameId);
}
