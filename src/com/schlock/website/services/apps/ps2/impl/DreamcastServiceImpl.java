package com.schlock.website.services.apps.ps2.impl;

import com.schlock.website.entities.apps.ps2.DreamcastGame;
import com.schlock.website.entities.apps.ps2.PlaystationPlatform;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.ps2.DreamcastService;
import com.schlock.website.services.database.apps.ps2.DreamcastGameDAO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.net.URLEncoder;

public class DreamcastServiceImpl implements DreamcastService
{
    private static final String NAME_FILENAME = "name.txt";
    private static final String SERIAL_FILENAME = "serial.txt";

    private final DreamcastGameDAO gameDAO;
    private final DeploymentContext context;

    public DreamcastServiceImpl(DreamcastGameDAO gameDAO,
                                DeploymentContext context)
    {
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

        for(DreamcastGame game : gameDAO.getAllWithNoArt())
        {
            File boxartData = new File(ART_DATA_PATH + game.getBoxartFilename());
            if(!boxartData.exists())
            {
                downloadMissingBoxartImages(boxartData);
            }

            if(boxartData.exists())
            {


                convertBoxartImagesToThumbnails(game);
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

//
//
//            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
//            FileOutputStream fos = new FileOutputStream(boxartDataFile);
//
//            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
//
//            fos.close();
//            rbc.close();
        }
        catch(Exception e)
        {
            System.err.println("Could not find boxart for " + boxartDataFile.getName());
        }
    }

    private void convertBoxartImagesToThumbnails(DreamcastGame game)
    {

    }
}
