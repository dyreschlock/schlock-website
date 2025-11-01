package com.schlock.website.services.apps.ps2.impl;

import com.schlock.website.entities.apps.ps2.GamecubeGame;
import com.schlock.website.entities.apps.ps2.PlaystationPlatform;
import com.schlock.website.entities.apps.ps2.RetroGame;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.ps2.GamecubeService;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.database.apps.ps2.GamecubeGameDAO;
import com.schlock.website.services.database.apps.ps2.RetroGameDAO;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

public class GamecubeServiceImpl extends AbstractRetroConsoleServiceImpl<GamecubeGame> implements GamecubeService
{
    private final String FILE_TYPE = "." + PlaystationPlatform.GC.fileType();

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
        verifyGameInventory();

        createAndUpdateEntries();
    }

    private void verifyGameInventory()
    {
        final String DRIVE_PATH = context.gamecubeDriveDirectory();

        for(GamecubeGame game : gameDAO.getAllAvailable())
        {
            String location = DRIVE_PATH + game.getGameName() + FILE_TYPE;
            File gameFile = new File(location);
            if (!gameFile.exists())
            {
                game.setDrive(null);
                game.setAvailable(false);

                gameDAO.save(game);
            }
        }
    }

    private void createAndUpdateEntries()
    {
        final String DRIVE_PATH = context.gamecubeDriveDirectory();

        FileFilter filter = new FileFilter()
        {
            @Override
            public boolean accept(File pathname)
            {
                boolean coorectFileType = pathname.getName().endsWith(FILE_TYPE);
                boolean notDot = !pathname.getName().startsWith(".");

                return notDot && coorectFileType;
            }
        };

        File gameDrive = new File(DRIVE_PATH);
        if (gameDrive.exists())
        {
            String driveName = gameDrive.getName();

            for(File game : gameDrive.listFiles(filter))
            {
                String filename = game.getName();
                processGame(filename, driveName);
            }
        }
    }

    private void processGame(String name, String driveName)
    {
        String title = name.substring(0, name.lastIndexOf(FILE_TYPE));

        GamecubeGame game = gameDAO.getByGameName(title);
        if (game != null)
        {
            game.setDrive(driveName);
            game.setAvailable(true);
        }
        else
        {
            game = GamecubeGame.create(driveName, title, null);
        }
        gameDAO.save(game);
    }


    protected List<String> getSaveFileLocalDirectories()
    {
        final String LOCAL_PATH = context.memcardSavesLocalDirectory();
        return Arrays.asList(LOCAL_PATH + "/" + GamecubeGame.SAVE_FOLDER);
    }

    protected boolean isValidGameFolder(String folderName)
    {
        return GamecubeGame.isSerialNumberMemcardFormat(folderName);
    }

    protected RetroGame getGameByFolderName(String folderName)
    {
        String gameId = GamecubeGame.getSerialNumberStandardFormat(folderName);
        return gameDAO.getBySerialNumber(gameId);
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
