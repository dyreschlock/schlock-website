package com.schlock.website.services.apps.ps2.impl;

import com.schlock.website.entities.apps.ps2.GamecubeGame;
import com.schlock.website.entities.apps.ps2.PlaystationPlatform;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.ps2.GamecubeService;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.database.apps.ps2.GamecubeGameDAO;
import com.schlock.website.services.database.apps.ps2.RetroGameDAO;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class GamecubeServiceImpl extends AbstractRetroConsoleServiceImpl<GamecubeGame> implements GamecubeService
{
    private final GamecubeGameDAO gameDAO;

    public GamecubeServiceImpl(GamecubeGameDAO gameDAO,
                                RetroGameDAO retroGameDAO,
                                ImageManagement imageManagement,
                                DeploymentContext context)
    {
        super(retroGameDAO, imageManagement, context);

        this.gameDAO = gameDAO;
    }

    public void updateGameInventory()
    {

    }

    protected List<GamecubeGame> getAllGamesWithNoArt()
    {
        return Collections.emptyList();
    }

    protected File getBoxartBaseFile(GamecubeGame game)
    {
        return null;
    }

    protected String getBoxartRepoNam()
    {
        return PlaystationPlatform.GC.boxartRepoName();
    }
}
