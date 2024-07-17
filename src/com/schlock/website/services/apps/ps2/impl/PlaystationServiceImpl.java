package com.schlock.website.services.apps.ps2.impl;

import com.schlock.website.entities.apps.ps2.PlaystationGame;
import com.schlock.website.entities.apps.ps2.PlaystationPlatform;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.ps2.PlaystationPropertyService;
import com.schlock.website.services.apps.ps2.PlaystationService;
import com.schlock.website.services.database.apps.ps2.PlaystationGameDAO;

import java.io.*;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.TreeSet;

public class PlaystationServiceImpl implements PlaystationService
{
    private final PlaystationPropertyService propService;
    private final PlaystationGameDAO gameDAO;

    private final DeploymentContext context;

    public PlaystationServiceImpl(PlaystationPropertyService propService,
                                    PlaystationGameDAO gameDAO,
                                    DeploymentContext context)
    {
        this.propService = propService;
        this.gameDAO = gameDAO;
        this.context = context;
    }

    public void updateGameInventory()
    {
        verifyGameInventory();

        for(PlaystationPlatform platform : PlaystationPlatform.values())
        {
            createEntriesFromLocation(platform);
        }
    }

    public void verifyGameInventory()
    {
        final String DRIVE_PATH = context.playstationDriveDirectory();

        for(PlaystationGame game : gameDAO.getAll())
        {
            String filepath = DRIVE_PATH + game.getGameRelativeFilepath();
            if (!new File(filepath).exists())
            {
                game.setDrive(null);

                gameDAO.save(game);
            }
        }
    }

    private void createEntriesFromLocation(final PlaystationPlatform platform)
    {
        final String DRIVE_PATH = context.playstationDriveDirectory();

        String location = DRIVE_PATH + "/" + platform.folder();

        FilenameFilter filenameFilter = new FilenameFilter()
        {
            public boolean accept(File dir, String name)
            {

                boolean filetypeOk =
                        name.toLowerCase().endsWith(platform.fileType());

                return !name.startsWith(".") && filetypeOk;
            }
        };

        for(File gameFile : new File(location).listFiles(filenameFilter))
        {
            PlaystationGame gameData = PlaystationGame.create(gameFile, platform);

            PlaystationGame game = gameDAO.getByGameIdAndFilename(gameData.getGameId(), gameData.getGameName());
            if (game == null)
            {
                game = gameData;
            }

            game.updateGameName(gameFile.getName());

            String driveName = location.split("/")[2];
            game.setDrive(driveName);

            gameDAO.save(game);
        }
    }



    public void readConfigFiles() throws Exception
    {
        final String DATA_DIR = context.playstationDataDirectory();

        for(PlaystationGame game : gameDAO.getAll())
        {
            File configFile = new File(DATA_DIR + game.getCfgRelativeFilepath());
            if (configFile.exists())
            {
                InputStream in = new FileInputStream(configFile);

                Properties configuration = new Properties();
                configuration.load(in);

                propService.loadPropertiesFromCFG(game, configuration);
            }
        }
    }

    public void writeConfigFiles() throws Exception
    {
        final String DATA_DIR = context.playstationDataDirectory();
        final String DEST_DIR = context.playstationLocalDirectory();

        for(PlaystationGame game : gameDAO.getAll())
        {
            Properties configuration = new Properties();

            File baseConfigFile = new File(DATA_DIR + game.getCfgRelativeFilepath());
            if (baseConfigFile.exists())
            {
                InputStream in = new FileInputStream(baseConfigFile);
                configuration.load(in);
            }

            propService.writePropertiesFromGame(game, configuration);


            File outputConfigFile = new File(DEST_DIR + game.getCfgRelativeFilepath());
            OutputStream out = new FileOutputStream(outputConfigFile);

            Properties tmp = new Properties()
            {
                public synchronized Enumeration<Object> keys()
                {
                    return Collections.enumeration(new TreeSet<Object>(super.keySet()));
                }
            };
            tmp.putAll(configuration);
            tmp.store(out, "CFG for Game: " + game.getGameId());
        }
    }


    public void writeArtFilesToLocal() throws Exception
    {
        final String DATA_PATH = context.playstationDataDirectory();
        final String LOCAL_PATH = context.playstationLocalDirectory();
        final String WEB_PATH = context.webOutputDirectoryImageFolder() + "ps2/";

        for(PlaystationGame game : gameDAO.getAll())
        {
            File source = new File(DATA_PATH + game.getArtRelativeFilepath());
            if (source.exists())
            {
                File localDest = new File(LOCAL_PATH + game.getArtRelativeFilepath());
                copyFile(source, localDest);

                File webDest = new File(WEB_PATH + game.getCoverImageFilename());
                copyFile(source, webDest);

                game.setHaveArt(true);
                gameDAO.save(game);
            }
        }
    }

    public void copyLocalFilesToDrive() throws Exception
    {
        final String LOCAL_PATH = context.playstationLocalDirectory();
        final String DRIVE_PATH = context.playstationDriveDirectory();

        for (PlaystationGame game : gameDAO.getAll())
        {
            String sourceConfigPath = LOCAL_PATH + game.getCfgRelativeFilepath();
            String destinationConfigPath = DRIVE_PATH + game.getCfgRelativeFilepath();

            copyFile(sourceConfigPath, destinationConfigPath);

            String sourceArtPath = LOCAL_PATH + game.getArtRelativeFilepath();
            String destinationArtPath = DRIVE_PATH + game.getArtRelativeFilepath();

            copyFile(sourceArtPath, destinationArtPath);
        }
    }

    private void copyFile(String sourcePath, String destPath) throws Exception
    {
        File source = new File(sourcePath);
        File destination = new File(destPath);

        if (source.exists())
        {
            if (destination.exists())
            {
                destination.delete();
            }

            copyFile(source, destination);
        }
    }

    private void copyFile(File source, File destination) throws Exception
    {
        InputStream in = new BufferedInputStream(new FileInputStream(source));
        OutputStream out = new BufferedOutputStream(new FileOutputStream(destination));

        byte[] buffer = new byte[1024];
        int lengthRead;
        while((lengthRead = in.read(buffer)) > 0)
        {
            out.write(buffer, 0, lengthRead);
            out.flush();
        }
    }

    public void updateGameSaveFiles()
    {
        updateCurrentGameSaves();
        locateNewGameSaves();
    }

    private void updateCurrentGameSaves()
    {
        final String LOCAL_PATH = context.playstationLocalDirectory();

        for(PlaystationGame game : gameDAO.getAllWithSave())
        {
            String filepath = LOCAL_PATH + game.getSaveFileRelativeFilepath();
            File saveFolder = new File(filepath);

            if (!saveFolder.exists())
            {
                game.setHaveSave(false);
                gameDAO.save(game);
            }
        }
    }

    private void locateNewGameSaves()
    {
        final String MCP_PATH = context.playstationLocalDirectory() + PlaystationGame.SAVE_FOLDER + "/";

        FilenameFilter saveFolderFormat = new FilenameFilter()
        {
            public boolean accept(File dir, String name)
            {
                return PlaystationGame.isGameIdMemcardFormat(name);
            }
        };

        for(PlaystationPlatform platform : PlaystationPlatform.values())
        {
            String filepath = MCP_PATH + platform.name();
            File saveFolder = new File(filepath);
            for(File folder : saveFolder.listFiles(saveFolderFormat))
            {
                updateGameWithSaveFolder(folder);
            }
        }
    }

    private void updateGameWithSaveFolder(File saveFolder)
    {
        String gameId = PlaystationGame.getGameIdStandardFormat(saveFolder.getName());

        PlaystationGame game = gameDAO.getByGameId(gameId);
        if (game != null)
        {
            game.setHaveSave(true);
            gameDAO.save(game);
        }
        else
        {
            System.out.println("Game not found:" + gameId);
        }
    }
}
