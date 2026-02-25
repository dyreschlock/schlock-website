package com.schlock.website.entities.apps.ps2;

import com.schlock.website.entities.Icon;
import org.apache.commons.lang.StringUtils;

public class DreamcastGame extends RetroGame
{
    public static final String SAVE_FOLDER = "vmu/Dreamcast/";

    private String sdcardSlot;
    private String gameName;

    private String serialNumber;
    private String boxartFilename;


    public String getCoverImageFilename()
    {
        return serialNumber + ".png";
    }

    public String getSaveFileRelativeFilepath()
    {
        String gameId = getGameIdMemcardFormat(getSerialNumber());
        return SAVE_FOLDER + gameId;
    }

    public Icon getMemcardIcon()
    {
        return Icon.DC_MEM;
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


    public static String getGameIdMemcardFormat(String standardFormat)
    {
        String gameId = standardFormat.replace("-", "");
        return gameId;
    }

    public static String getGameIdStandardFormat(String memcardFormat)
    {
        int i = StringUtils.indexOfAny(memcardFormat, "1234567890");
        if (i > 0 && i < memcardFormat.length())
        {
            String first = memcardFormat.substring(0, i);
            String last = memcardFormat.substring(i, memcardFormat.length());

            return first + "-" + last;
        }
        return memcardFormat;
    }

    public static boolean isSerialNumberMemcardFormat(String memcardFormat)
    {
        return memcardFormat.length() == 6 || memcardFormat.length() == 7;
    }
}
