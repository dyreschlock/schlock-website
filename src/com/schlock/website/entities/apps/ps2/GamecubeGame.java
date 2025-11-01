package com.schlock.website.entities.apps.ps2;

import com.schlock.website.entities.Icon;

public class GamecubeGame extends RetroGame
{
    public static final String SAVE_FOLDER = "mcpgc/MemoryCards/";

    private String drive;

    private String gameName;

    private String serialNumber;
    private String boxartFilename;


    public String getCoverImageFilename()
    {
        return serialNumber + ".png";
    }

    public String getSaveFileRelativeFilepath()
    {
        String serial = getSerialNumberMemcardFormat(getSerialNumber());
        return SAVE_FOLDER + serial;
    }

    public Icon getMemcardIcon()
    {
        return Icon.GC_MEM;
    }

    public String getDrive()
    {
        return drive;
    }

    public void setDrive(String drive)
    {
        this.drive = drive;
    }

    public String getGameName()
    {
        return gameName;
    }

    public void setGameName(String gameName)
    {
        this.gameName = gameName;
    }

    public String getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    public String getBoxartFilename()
    {
        return boxartFilename;
    }

    public void setBoxartFilename(String boxartFilename)
    {
        this.boxartFilename = boxartFilename;
    }

    public static GamecubeGame create(String drive, String title, String serialNumber)
    {
        GamecubeGame game = new GamecubeGame();

        game.setTitle(title);

        game.drive = drive;
        game.gameName = title;
        game.serialNumber = serialNumber;

        game.boxartFilename = title + ".png";

        game.setAvailable(true);
        game.setHaveArt(false);
        game.setHaveSave(false);

        game.setPlatform(PlaystationPlatform.GC);

        return game;
    }

    private static final String ZERO_ZERO = "00";

    private static String getSerialNumberMemcardFormat(String standardFormat)
    {
        return standardFormat + ZERO_ZERO;
    }

    public static String getSerialNumberStandardFormat(String memcardFormat)
    {
        return memcardFormat.substring(0, memcardFormat.length() - 2);
    }

    public static boolean isSerialNumberMemcardFormat(String memcardFormat)
    {
        return memcardFormat.endsWith(ZERO_ZERO);
    }
}
