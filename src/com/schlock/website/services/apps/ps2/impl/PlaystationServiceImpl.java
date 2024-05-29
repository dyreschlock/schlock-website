package com.schlock.website.services.apps.ps2.impl;

import com.schlock.website.entities.apps.ps2.PlaystationGame;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.ps2.PlaystationService;
import com.schlock.website.services.database.apps.ps2.PlaystationGameDAO;

import java.io.File;
import java.io.FilenameFilter;

public class PlaystationServiceImpl implements PlaystationService
{
    private static final String PS2_FOLDER = "DVD";
    private static final String PS1_FOLDER = "POPS";

    private static final String PS2_PLATFORM = "PS2";
    private static final String PS1_PLATFORM = "PS1";

    private static final String ART_FOLDER = "ART";
    private static final String CFG_FOLDER = "CFG";

    private final PlaystationGameDAO gameDAO;

    private final DeploymentContext context;

    public PlaystationServiceImpl(PlaystationGameDAO gameDAO,
                                     DeploymentContext context)
    {
        this.gameDAO = gameDAO;
        this.context = context;
    }

    public void createEntriesForGames(String driveName) throws Exception
    {
        final String DRIVE_PATH = context.playstationDriveDirectory() + driveName;

        createEntriesFromLocation(DRIVE_PATH, PS2_FOLDER, PS2_PLATFORM);
        createEntriesFromLocation(DRIVE_PATH, PS1_FOLDER, PS1_PLATFORM);
    }

    private void createEntriesFromLocation(String drivePath, String gameFolder, String platform)
    {
        String location = drivePath + "/" + gameFolder;

        FilenameFilter filenameFilter = new FilenameFilter()
        {
            public boolean accept(File dir, String name)
            {
                final String ISO = ".iso";
                final String VCD = ".vcd";

                boolean filetypeOk = name.toLowerCase().endsWith(ISO) || name.toLowerCase().endsWith(VCD);

                return !name.startsWith(".") && filetypeOk;
            }
        };

        for(File gameFile : new File(location).listFiles(filenameFilter))
        {
            PlaystationGame gameData = PlaystationGame.create(gameFile, platform);

            PlaystationGame game = gameDAO.getByGameId(gameData.getGameId());
            if (game == null)
            {
                game = gameData;
            }

            boolean art = checkForFile(drivePath, ART_FOLDER, game);
            boolean cfg = checkForFile(drivePath, CFG_FOLDER, game);

            game.setHaveArt(art);
            game.setHaveCfg(cfg);

            String driveName = location.split("/")[2];
            game.setLocation(driveName);

            gameDAO.save(game);
        }
    }

    private boolean checkForFile(String drivePath, String folderName, final PlaystationGame game)
    {
        String folderPath = drivePath + "/" + folderName;

        FilenameFilter filter = new FilenameFilter()
        {
            public boolean accept(File dir, String name)
            {
                return name.startsWith(game.getGameId());
            }
        };
        return new File(folderPath).listFiles(filter).length != 0;
    }
}
