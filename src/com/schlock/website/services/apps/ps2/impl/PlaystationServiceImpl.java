package com.schlock.website.services.apps.ps2.impl;

import com.schlock.website.entities.apps.ps2.PlaystationGame;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.ps2.PlaystationPropertyService;
import com.schlock.website.services.apps.ps2.PlaystationService;
import com.schlock.website.services.database.apps.ps2.PlaystationGameDAO;

import java.io.*;
import java.util.Properties;

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

        createEntriesFromLocation(PlaystationGame.PS2_FOLDER, PlaystationGame.PS2_PLATFORM);
        createEntriesFromLocation(PlaystationGame.PS1_FOLDER, PlaystationGame.PS1_PLATFORM);
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

    private void createEntriesFromLocation(String gameFolder, String platform)
    {
        final String DRIVE_PATH = context.playstationDriveDirectory();

        String location = DRIVE_PATH + "/" + gameFolder;

        FilenameFilter filenameFilter = new FilenameFilter()
        {
            public boolean accept(File dir, String name)
            {

                boolean filetypeOk =
                        name.toLowerCase().endsWith(PlaystationGame.PS2_FILETYPE)
                                || name.toLowerCase().endsWith(PlaystationGame.PS1_FILETYPE);

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

            game.updateGameName(gameFile.getName());

            String artFilepath = DRIVE_PATH + "/" + game.getArtRelativeFilepath();
            String cfgFilepath = DRIVE_PATH + "/" + game.getCfgRelativeFilepath();

            boolean art = new File(artFilepath).exists();
            boolean cfg = new File(cfgFilepath).exists();

            game.setHaveArt(art);
            game.setHaveCfg(cfg);

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

            configuration.store(out, "CFG for Game: " + game.getGameId());
        }
    }


    public void writeArtFilesToLocal() throws Exception
    {
        final String DATA_PATH = context.playstationDataDirectory();
        final String LOCAL_PATH = context.playstationLocalDirectory();

        for(PlaystationGame game : gameDAO.getAll())
        {
            File source = new File(DATA_PATH + game.getArtRelativeFilepath());
            File destination = new File(LOCAL_PATH + game.getArtRelativeFilepath());

            if (!destination.exists() && source.exists())
            {
                copyFile(source, destination);
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
}
