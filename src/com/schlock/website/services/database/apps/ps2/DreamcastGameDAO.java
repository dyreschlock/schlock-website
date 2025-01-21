package com.schlock.website.services.database.apps.ps2;

import com.schlock.website.entities.apps.ps2.DreamcastGame;
import com.schlock.website.services.database.BaseDAO;

public interface DreamcastGameDAO extends BaseDAO<DreamcastGame>
{
    DreamcastGame getBySerialNumber(String serial);
}
