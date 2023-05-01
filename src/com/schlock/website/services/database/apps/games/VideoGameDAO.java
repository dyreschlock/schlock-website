package com.schlock.website.services.database.apps.games;

import com.schlock.website.entities.apps.games.Condition;
import com.schlock.website.entities.apps.games.Region;
import com.schlock.website.entities.apps.games.VideoGame;
import com.schlock.website.services.database.BaseDAO;

import java.util.List;

public interface VideoGameDAO extends BaseDAO<VideoGame>
{
    List<VideoGame> getByCondition(Condition condition);

    List<VideoGame> getByRegion(Region region);
}
