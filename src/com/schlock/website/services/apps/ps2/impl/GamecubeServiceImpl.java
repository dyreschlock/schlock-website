package com.schlock.website.services.apps.ps2.impl;

import com.schlock.website.entities.apps.ps2.GamecubeGame;
import com.schlock.website.entities.apps.ps2.PlaystationPlatform;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.ps2.GamecubeService;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.database.apps.ps2.GamecubeGameDAO;
import com.schlock.website.services.database.apps.ps2.RetroGameDAO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

    private final String FAKE_DRIVE = "SORNY";
    private final String GAMES_LIST = "games.txt";
    private final String SERIALS = "wiitdb.txt";

    public void updateGameInventory()
    {
        verifyGameInventory();

        createAndUpdateEntries();
    }

    private void verifyGameInventory()
    {

    }

    private void createAndUpdateEntries()
    {
        final String DATA_LOCATION = context.gamecubeDataDirectory();

        File gamesList = new File(DATA_LOCATION + GAMES_LIST);
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(gamesList));

            String line = in.readLine();
            while(line != null)
            {
                processGame(line);

                line = in.readLine();
            }
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private void processGame(String name)
    {
        String title = name.substring(0, name.indexOf(".zip"));

        GamecubeGame game = gameDAO.getByGameName(title);
        if (game == null)
        {
            game = GamecubeGame.create(FAKE_DRIVE, title, null);
            gameDAO.save(game);
        }
    }


    protected List<GamecubeGame> getAllGamesWithNoArt()
    {
        return gameDAO.getAllWithNoArt();
    }

    protected File getBoxartBaseFile(GamecubeGame game)
    {
        final String ART_DATA_PATH = context.gamecubeDataDirectory() + "boxart/";

        return new File(ART_DATA_PATH + game.getBoxartFilename());
    }

    protected String getBoxartRepoNam()
    {
        return PlaystationPlatform.GC.boxartRepoName();
    }
}
