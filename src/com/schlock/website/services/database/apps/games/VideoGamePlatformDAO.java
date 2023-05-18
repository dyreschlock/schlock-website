package com.schlock.website.services.database.apps.games;

import com.schlock.website.entities.apps.games.VideoGamePlatform;
import com.schlock.website.services.database.BaseDAO;

import java.util.List;

public interface VideoGamePlatformDAO extends BaseDAO<VideoGamePlatform>
{
    VideoGamePlatform getByCode(String code);

    List<VideoGamePlatform> getByCompany(String company);
}
