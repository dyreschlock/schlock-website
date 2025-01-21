package com.schlock.website.entities.apps.ps2;

public class DreamcastGame extends RetroGame
{
    private String sdcardSlot;
    private String serialNumber;
    private String boxartFilename;

    private boolean haveArt;
    private boolean haveSave;


    public PlaystationPlatform getPlatform()
    {
        return PlaystationPlatform.DC;
    }



    public String getSdcardSlot()
    {
        return sdcardSlot;
    }

    public void setSdcardSlot(String sdcardSlot)
    {
        this.sdcardSlot = sdcardSlot;
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

    public boolean isHaveArt()
    {
        return haveArt;
    }

    public void setHaveArt(boolean haveArt)
    {
        this.haveArt = haveArt;
    }

    public boolean isHaveSave()
    {
        return haveSave;
    }

    public void setHaveSave(boolean haveSave)
    {
        this.haveSave = haveSave;
    }


    public static DreamcastGame create(String sdcardSlot, String title, String serialNumber)
    {
        DreamcastGame game = new DreamcastGame();

        game.setTitle(title);

        game.sdcardSlot = sdcardSlot;
        game.serialNumber = serialNumber;
        game.boxartFilename = title + ".png";

        game.haveArt = false;
        game.haveSave = false;

        return game;
    }
}
