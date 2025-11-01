package com.schlock.website.services.apps.ps2.impl;

import com.schlock.website.entities.apps.ps2.DreamcastGame;
import com.schlock.website.entities.apps.ps2.PlaystationPlatform;
import com.schlock.website.entities.apps.ps2.RetroGame;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.ps2.DreamcastService;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.database.apps.ps2.DreamcastGameDAO;
import com.schlock.website.services.database.apps.ps2.RetroGameDAO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class DreamcastServiceImpl extends AbstractRetroConsoleServiceImpl<DreamcastGame> implements DreamcastService
{
    private static final String NAME_FILENAME = "name.txt";
    private static final String SERIAL_FILENAME = "serial.txt";

    private final DreamcastGameDAO gameDAO;

    public DreamcastServiceImpl(DreamcastGameDAO gameDAO,
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


    protected List<String> getSaveFileLocalDirectories()
    {
        return Arrays.asList();
    }

    protected boolean isValidGameFolder(String folderName)
    {
        return false;
    }

    protected RetroGame getGameByFolderName(String folderName)
    {
        return null;
    }

    protected List getAllGamesWithNoArt()
    {
        return gameDAO.getAllWithNoArt();
    }

    protected File getBoxartBaseFile(DreamcastGame game)
    {
        final String ART_DATA_PATH = context.dreamcastDataDirectory() + "boxart/";

        return new File(ART_DATA_PATH + game.getBoxartFilename());
    }

    protected String getBoxartRepoNam()
    {
        return PlaystationPlatform.DC.boxartRepoName();
    }
}
