package com.schlock.website.services.apps.ps2.impl;

import com.schlock.website.entities.apps.ps2.DreamcastGame;
import com.schlock.website.entities.apps.ps2.PlaystationPlatform;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.ps2.DreamcastService;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.database.apps.ps2.DreamcastGameDAO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

public class DreamcastServiceImpl implements DreamcastService
{
    private static final String NAME_FILENAME = "name.txt";
    private static final String SERIAL_FILENAME = "serial.txt";

    private static final int CONVERTED_IMAGE_WIDTH = 160;

    private final DreamcastGameDAO gameDAO;
    private final ImageManagement imageManagement;
    private final DeploymentContext context;

    public DreamcastServiceImpl(DreamcastGameDAO gameDAO,
                                ImageManagement imageManagement,
                                DeploymentContext context)
    {
        this.imageManagement = imageManagement;

        this.gameDAO = gameDAO;
        this.context = context;
    }

    public void updateGameInventory()
    {
        verifyGameInventory();

        createAndUpdateEntries();
    }

    private void verifyGameInventory()
    {
        final String DRIVE_PATH = context.dreamcastDriveDirectory();

        for(DreamcastGame game : gameDAO.getAllAvailable())
        {
            boolean remove = true;
            try
            {
                File serial = new File(DRIVE_PATH + game.getSdcardSlot() + "/" + SERIAL_FILENAME);
                if(serial.exists())
                {
                    String serialNumber = FileUtils.readFileToString(serial);
                    remove = !StringUtils.equalsIgnoreCase(serialNumber, game.getSerialNumber());
                }
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }

            if (remove)
            {
                game.setSdcardSlot(null);
                game.setAvailable(false);

                gameDAO.save(game);
            }
        }
    }

    private void createAndUpdateEntries()
    {
        final String DRIVE_PATH = context.dreamcastDriveDirectory();

        int sdcardSlot = 2;
        File gameFolder = new File(DRIVE_PATH + createSlotName(sdcardSlot));
        while(gameFolder.exists())
        {
            try
            {
                processGameInFolder(gameFolder);
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }

            sdcardSlot++;
            gameFolder = new File(DRIVE_PATH + createSlotName(sdcardSlot));
        }
    }

    private String createSlotName(int number)
    {
        String name = Integer.toString(number);
        if (name.length() == 1)
        {
            name = "0" + name;
        }
        return name;
    }

    private void processGameInFolder(File gameFolder) throws Exception
    {
        String filepath = gameFolder.getAbsolutePath();

        File serialFile = new File(filepath + "/" + SERIAL_FILENAME);
        String serialNumber = FileUtils.readFileToString(serialFile);

        File nameFile = new File(filepath + "/" + NAME_FILENAME);
        String name = FileUtils.readFileToString(nameFile);

        String sdcardSlot = gameFolder.getName();

        DreamcastGame game = gameDAO.getBySerialNumber(serialNumber);
        if (game != null)
        {
            game.setGameName(name);
            game.setSdcardSlot(sdcardSlot);
            game.setAvailable(true);
        }
        else
        {
            game = DreamcastGame.create(sdcardSlot, name, serialNumber);
        }
        gameDAO.save(game);
    }

    public void writeArtFilesToLocal()
    {
        final String ART_DATA_PATH = context.dreamcastDataDirectory() + "boxart/";
        final String IMG_FILE_PATH = context.webOutputDirectoryImageFolder() + "%s/%s";

        for(DreamcastGame game : gameDAO.getAllWithNoArt())
        {
            File boxartData = new File(ART_DATA_PATH + game.getBoxartFilename());
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

    private void downloadMissingBoxartImages(File boxartDataFile)
    {
        String sourceUrl = context.gameBoxartSourceUrl();
        String reponame = PlaystationPlatform.DC.boxartRepoName();
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
