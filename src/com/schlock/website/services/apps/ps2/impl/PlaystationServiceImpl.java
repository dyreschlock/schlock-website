package com.schlock.website.services.apps.ps2.impl;

import com.schlock.website.entities.apps.ps2.PlaystationGame;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.ps2.PlaystationService;
import com.schlock.website.services.database.apps.ps2.PlaystationGameDAO;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class PlaystationServiceImpl implements PlaystationService
{
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
        createEntriesFromLocation(driveName, PlaystationGame.PS2_FOLDER, PlaystationGame.PS2_PLATFORM);
        createEntriesFromLocation(driveName, PlaystationGame.PS1_FOLDER, PlaystationGame.PS1_PLATFORM);
    }

    private void createEntriesFromLocation(String driveName, String gameFolder, String platform)
    {
        final String DRIVE_PATH = context.playstationDriveDirectory() + driveName;

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

            game.setDrive(driveName);

            gameDAO.save(game);
        }
    }

    public void verifyGamesOnDrive(String driveName)
    {
        final String DRIVE_PATH = context.playstationDriveDirectory() + driveName;

        List<PlaystationGame> games = gameDAO.getGamesOnDrive(driveName);
        for(PlaystationGame game : games)
        {
            String filepath = DRIVE_PATH + "/" + game.getGameRelativeFilepath();
            if (!new File(filepath).exists())
            {
                game.setDrive(null);

                gameDAO.save(game);
            }
        }
    }



    private static final String TITLE = "Title";
    private static final String GENRE = "Genre";
    private static final String RELEASE_DATE = "Release";
    private static final String DEVELOPER = "Developer";
    private static final String PUBLISHER = "Publisher";

    private static final String DELIM = "=";

    public void readConfigFiles() throws Exception
    {
        final String CFG_LOCATION = context.playstationDataDirectory() + PlaystationGame.CFG_FOLDER + "/";

        for(PlaystationGame game : gameDAO.getAll())
        {
            File cfgFile = new File(CFG_LOCATION + game.getGameId() + ".cfg");
            if (cfgFile.exists())
            {
                loadPropertiesFromCFG(game, cfgFile);
            }
        }
    }

    private void loadPropertiesFromCFG(PlaystationGame game, File configFile) throws Exception
    {
        InputStream in = new FileInputStream(configFile);

        Properties configuration = new Properties();
        configuration.load(in);

        String title = configuration.getProperty(TITLE);
        if (game.getTitle() == null && title != null)
        {
            game.setTitle(title);
        }

        String genre = configuration.getProperty(GENRE);
        if (game.getGenre() == null && genre != null)
        {
            game.setGenre(genre);
        }

        String developer = configuration.getProperty(DEVELOPER);
        if (game.getDeveloper() == null && developer != null)
        {
            game.setDeveloper(developer);
        }

        String publisher = configuration.getProperty(PUBLISHER);
        if (game.getPublisher() == null && publisher != null)
        {
            game.setPublisher(publisher);
        }

        String dateText = configuration.getProperty(RELEASE_DATE);
        if (game.getReleaseDate() == null && dateText != null)
        {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");  // 10-22-2001

            try
            {
                Date date = format.parse(dateText);
                game.setReleaseDate(date);
            }
            catch(Exception e)
            {
            }
        }
    }

    public void writeConfigFiles() throws Exception
    {

    }

    public void copyConfigFilesToDrive(String driveName) throws Exception
    {
        final String DATA_PATH = context.playstationDataDirectory();
        final String DRIVE_PATH = context.playstationDriveDirectory() + driveName + "/";

        List<PlaystationGame> games = gameDAO.getGamesOnDrive(driveName);
        for (PlaystationGame game : games)
        {
            File sourceFile = new File(DATA_PATH + game.getCfgRelativeFilepath());
            File destinationFile = new File(DRIVE_PATH + game.getCfgRelativeFilepath());

            if (sourceFile.exists())
            {
                if (destinationFile.exists())
                {
                    destinationFile.delete();
                }

                copyFile(sourceFile, destinationFile);
            }
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
