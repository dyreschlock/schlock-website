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
        String serial = getGameIdMemcardFormat(getSerialNumber());
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

    private static String getGameIdMemcardFormat(String gameId)
    {
        return gameId + "00";
    }
}
