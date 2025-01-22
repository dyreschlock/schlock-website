package com.schlock.website.entities.apps.ps2;

public class DreamcastGame extends RetroGame
{
    private String sdcardSlot;
    private String gameName;

    private String serialNumber;
    private String boxartFilename;


    public String getCoverImageFilename()
    {
        return serialNumber + "_" + getId().toString() + ".png";
    }

    public String getSaveFileRelativeFilepath()
    {
        return "";
    }

    public String getSdcardSlot()
    {
        return sdcardSlot;
    }

    public void setSdcardSlot(String sdcardSlot)
    {
        this.sdcardSlot = sdcardSlot;
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

    public static DreamcastGame create(String sdcardSlot, String title, String serialNumber)
    {
        DreamcastGame game = new DreamcastGame();

        game.setTitle(title);

        game.sdcardSlot = sdcardSlot;
        game.gameName = title;

        game.serialNumber = serialNumber;
        game.boxartFilename = title + ".png";

        game.setAvailable(true);

        game.setHaveArt(false);
        game.setHaveSave(false);

        game.setPlatform(PlaystationPlatform.DC);

        return game;
    }
}
