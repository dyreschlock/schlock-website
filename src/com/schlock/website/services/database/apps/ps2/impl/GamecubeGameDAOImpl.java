package com.schlock.website.services.database.apps.ps2.impl;

import com.schlock.website.entities.apps.ps2.GamecubeGame;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.apps.ps2.GamecubeGameDAO;
import org.hibernate.classic.Session;

import java.util.Collections;
import java.util.List;

public class GamecubeGameDAOImpl extends BaseDAOImpl<GamecubeGame> implements GamecubeGameDAO
{
    public GamecubeGameDAOImpl(Session session)
    {
        super(GamecubeGame.class, session);
    }

    public List<GamecubeGame> getAllAvailable()
    {
        return Collections.emptyList();
    }

    public GamecubeGame getBySerialNumber(String serial)
    {
        return null;
    }
}
