package com.schlock.website.services.database.apps.ps2;

import com.schlock.website.entities.apps.ps2.DreamcastGame;
import com.schlock.website.services.database.BaseDAO;

import java.util.List;

public interface DreamcastGameDAO extends BaseDAO<DreamcastGame>
{
    List<DreamcastGame> getAllAvailable();

    DreamcastGame getBySerialNumber(String serial);
}
