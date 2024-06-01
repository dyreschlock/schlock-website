package com.schlock.website.services.apps.ps2.impl;

import com.schlock.website.entities.apps.ps2.PlaystationGame;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.ps2.PlaystationService;
import com.schlock.website.services.database.apps.ps2.PlaystationGameDAO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
            if (game.isHaveCfg())
            {
                File cfgFile = new File(CFG_LOCATION + game.getGameId() + ".cfg");
                if (cfgFile.exists())
                {
                    updateConfigProperties(game, cfgFile);
                }
            }
        }
    }

    private void updateConfigProperties(PlaystationGame game, File configFile) throws Exception
    {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");  // 10-22-2001
        BufferedReader reader = new BufferedReader(new FileReader(configFile));

        String in;
        while ((in = reader.readLine()) != null)
        {
            if (in.contains(DELIM))
            {
                int i = in.indexOf(DELIM);

                String param = in.substring(0, i);
                String data = in.substring(i + 1);

                if (TITLE.equals(param) && game.getTitle() == null)
                {
                    game.setTitle(data);
                }
                else if (GENRE.equals(param) && game.getGenre() == null)
                {
                    game.setGenre(data);
                }
                else if (DEVELOPER.equals(param) && game.getDeveloper() == null)
                {
                    game.setDeveloper(data);
                }
                else if (PUBLISHER.equals(param) && game.getPublisher() == null)
                {
                    game.setPublisher(data);
                }
                else if (RELEASE_DATE.equals(param) && game.getReleaseDate() == null)
                {
                    Date date = format.parse(data);
                    game.setReleaseDate(date);
                }
            }
        }
    }

    public void writeConfigFiles() throws Exception
    {

    }

    public void copyConfigFilesToDrive(String driveName) throws Exception
    {

    }
}
