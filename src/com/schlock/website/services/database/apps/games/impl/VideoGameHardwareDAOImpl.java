package com.schlock.website.services.database.apps.games.impl;

import com.schlock.website.entities.apps.games.VideoGameHardware;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.apps.games.VideoGameHardwareDAO;
import org.hibernate.Session;

public class VideoGameHardwareDAOImpl extends BaseDAOImpl<VideoGameHardware> implements VideoGameHardwareDAO
{
    public VideoGameHardwareDAOImpl(Session session)
    {
        super(VideoGameHardware.class, session);
    }
}
