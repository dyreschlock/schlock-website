package com.schlock.website.services.apps.ps2.impl;

import com.schlock.website.entities.apps.ps2.RetroGame;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.ps2.RetroConsoleService;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.database.apps.ps2.RetroGameDAO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public abstract class AbstractRetroConsoleServiceImpl<T extends RetroGame> implements RetroConsoleService
{
    private static final int CONVERTED_IMAGE_WIDTH = 160;

    private final RetroGameDAO gameDAO;

    private final ImageManagement imageManagement;
    protected final DeploymentContext context;

    public AbstractRetroConsoleServiceImpl(RetroGameDAO gameDAO,
                                            ImageManagement imageManagement,
                                            DeploymentContext context)
    {
        this.gameDAO = gameDAO;

        this.imageManagement = imageManagement;
        this.context = context;
    }

    protected abstract List<T> getAllGamesWithNoArt();

    protected abstract File getBoxartBaseFile(T game);

    protected abstract String getBoxartRepoNam();

    public void writeArtFilesToLocal()
    {
        final String IMG_FILE_PATH = context.webOutputDirectoryImageFolder() + "%s/%s";

        for(T game : getAllGamesWithNoArt())
        {
            File boxartData = getBoxartBaseFile(game);
            if(!boxartData.exists())
            {
                downloadMissingBoxartImages(boxartData);
            }

            if(boxartData.exists())
            {
                String imageFolder = game.getPlatform().gameImageFolder();
                String imageFile = game.getCoverImageFilename();

                File boxartImageFile = new File(String.format(IMG_FILE_PATH, imageFolder, imageFile));
                if (!boxartImageFile.exists())
                {
                    try
                    {
                        imageManagement.convertAndCopyImage(boxartData, boxartImageFile, CONVERTED_IMAGE_WIDTH);
                    }
                    catch (IOException e)
                    {
                    }

                    if (boxartImageFile.exists())
                    {
                        game.setHaveArt(true);
                        gameDAO.save(game);
                    }
                }
                else
                {
                    game.setHaveArt(true);
                    gameDAO.save(game);
                }
            }
        }
    }




    public void updateGameSaveFiles()
    {
        updateCurrentGameSaves();
        locateNewGameSaves();
    }

    private void updateCurrentGameSaves()
    {
        for(RetroGame game : gameDAO.getAllWithSave())
        {
            String filepath = getSaveFileLocalPath(game);
            File saveFolder = new File(filepath);

            if (!saveFolder.exists())
            {
                game.setHaveSave(false);
                gameDAO.save(game);
            }
        }
    }

    protected abstract List<String> getSaveFileLocalDirectories();

    protected abstract boolean isValidGameFolder(String folderName);

    protected abstract RetroGame getGameByFolderName(String folderName);

    private void locateNewGameSaves()
    {
        List<String> directories = getSaveFileLocalDirectories();

        FilenameFilter saveFolderFormat = new FilenameFilter()
        {
            public boolean accept(File dir, String name)
            {
                return isValidGameFolder(name);
            }
        };

        for(String directory : directories)
        {
            File saveFolder = new File(directory);
            for(File folder : saveFolder.listFiles(saveFolderFormat))
            {
                updateGameWithSaveFolder(folder);
            }
        }
    }

    private void updateGameWithSaveFolder(File saveFolder)
    {
        RetroGame game = getGameByFolderName(saveFolder.getName());

        if (game != null)
        {
            game.setHaveSave(true);
            gameDAO.save(game);
        }
        else
        {
            System.out.println("Game not found:" + saveFolder.getName());
        }
    }



    public String getSaveFileLink(RetroGame game)
    {
        if (game.isHaveSave())
        {
            final String REPO = context.memcardSavesOnlineDirectory();
            String filepath = game.getSaveFileRelativeFilepath();

            return REPO + filepath;
        }
        return null;
    }

    protected String getSaveFileLocalPath(RetroGame game)
    {
        final String LOCAL = context.memcardSavesLocalDirectory();
        String filepath = game.getSaveFileRelativeFilepath();

        return LOCAL + filepath;
    }

    private void downloadMissingBoxartImages(File boxartDataFile)
    {
        String sourceUrl = context.gameBoxartSourceUrl();
        String reponame = getBoxartRepoNam();
        String imageFilename = URLEncoder.encode(boxartDataFile.getName()).replace("+", "%20");

        String boxartUrl = String.format(sourceUrl, reponame) + imageFilename;

        try
        {
            URL url = new URL(boxartUrl);
            BufferedImage img = ImageIO.read(url);

            ImageIO.write(img, "png", boxartDataFile);

            System.out.println("Successfully downloaded boxart for " + boxartDataFile.getName());
        }
        catch(Exception e)
        {
            System.err.println("Could not find boxart for " + boxartDataFile.getName());
        }
    }
}
