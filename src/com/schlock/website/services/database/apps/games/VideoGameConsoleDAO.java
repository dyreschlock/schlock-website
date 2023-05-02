package com.schlock.website.services.database.apps.games;

import com.schlock.website.entities.apps.games.VideoGameConsole;
import com.schlock.website.services.database.BaseDAO;

import java.util.List;

public interface VideoGameConsoleDAO extends BaseDAO<VideoGameConsole>
{
    VideoGameConsole getByCode(String code);

    List<VideoGameConsole> getByCompany(String company);
}
