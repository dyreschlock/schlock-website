package com.schlock.website.entities.apps.ps2;

import java.io.File;

public class PlaystationGame extends RetroGame
{
    public static final String ART_FOLDER = "ART";
    public static final String CFG_FOLDER = "CFG";

    public static final String ART_FILETYPE = "_COV.jpg";
    public static final String CFG_FILETYPE = ".cfg";

    public static final String SAVE_FOLDER = "mcp2";

    private String gameId;

    private String gameName;

    private PlaystationPlatform platform;
    private String drive;

    private String working;

    private boolean subDisc;

    private boolean haveArt;
    private boolean haveSave;

    private PlaystationGameAspect aspect;
    private PlaystationGamePlayers players;
    private PlaystationGameVmode vmode;
    private PlaystationGameScan scan;
    private PlaystationGameParental parental;

    private String notes;
    private String description;

    private String postUUID;

    public PlaystationGame()
    {
    }

    public String getGameRelativeFilepath()
    {
        String folder = platform.folder();
        String fileType = platform.fileType();

        return folder + "/" + gameId + "." + gameName + fileType;
    }

    public String getSaveFileRelativeFilepath()
    {
        String platFolder = platform.name();
        String saveFolder = getGameIdMemcardFormat(getGameId());

        return SAVE_FOLDER + "/" + platFolder + "/" + saveFolder;
    }

    public String getCoverImageFilename()
    {
        return gameId + ART_FILETYPE;
    }

    public String getArtRelativeFilepath()
    {
        return ART_FOLDER + "/" + getCoverImageFilename();
    }

    public String getCfgRelativeFilepath()
    {
        return CFG_FOLDER + "/" + gameId + CFG_FILETYPE;
    }

    public void updateGameName(String currentFilename)
    {
        this.gameName = currentFilename.substring(12, currentFilename.length() - 4);
    }


    public String getGameId()
    {
        return gameId;
    }

    public void setGameId(String gameId)
    {
        this.gameId = gameId;
    }

    public String getGameName()
    {
        return gameName;
    }

    public void setGameName(String gameName)
    {
        this.gameName = gameName;
    }

    public PlaystationPlatform getPlatform()
    {
        return platform;
    }

    public void setPlatform(PlaystationPlatform platform)
    {
        this.platform = platform;
    }

    public String getDrive()
    {
        return drive;
    }

    public void setDrive(String drive)
    {
        this.drive = drive;
    }

    public String getWorking()
    {
        return working;
    }

    public void setWorking(String working)
    {
        this.working = working;
    }

    public boolean isSubDisc()
    {
        return subDisc;
    }

    public void setSubDisc(boolean subDisc)
    {
        this.subDisc = subDisc;
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

    public PlaystationGameAspect getAspect()
    {
        return aspect;
    }

    public void setAspect(PlaystationGameAspect aspect)
    {
        this.aspect = aspect;
    }

    public PlaystationGamePlayers getPlayers()
    {
        return players;
    }

    public void setPlayers(PlaystationGamePlayers players)
    {
        this.players = players;
    }

    public PlaystationGameVmode getVmode()
    {
        return vmode;
    }

    public void setVmode(PlaystationGameVmode vmode)
    {
        this.vmode = vmode;
    }

    public PlaystationGameScan getScan()
    {
        return scan;
    }

    public void setScan(PlaystationGameScan scan)
    {
        this.scan = scan;
    }

    public PlaystationGameParental getParental()
    {
        return parental;
    }

    public void setParental(PlaystationGameParental parental)
    {
        this.parental = parental;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getPostUUID()
    {
        return postUUID;
    }

    public void setPostUUID(String postUUID)
    {
        this.postUUID = postUUID;
    }

    public static PlaystationGame create(File file, PlaystationPlatform platform)
    {
        PlaystationGame game = new PlaystationGame();

        String filename = file.getName();

        game.gameId = filename.substring(0, 11);
        game.gameName = filename.substring(12, filename.length() - 4);

        game.platform = platform;

        game.working = "";
        game.haveArt = false;

        return game;
    }

    public static String getGameIdMemcardFormat(String standardFormat)
    {
        String gid = standardFormat;

        gid = gid.replace("_", "-");
        gid = gid.replace(".", "");

        return gid;
    }

    public static String getGameIdStandardFormat(String memcardFormat)
    {
        String gid = memcardFormat;

        int index = gid.indexOf("-");
        gid = gid.substring(0, index) + "_" + gid.substring(index +1, index +4) + "." + gid.substring(index +4);

        return gid;
    }

    public static boolean isGameIdMemcardFormat(String memcardFormat)
    {
        try
        {
            String id = memcardFormat;

            int index = id.indexOf("-");
            if (index < 0)
            {
                return false;
            }

            id = id.substring(index + 1);

            Integer.parseInt(id);
        }
        catch(Exception e)
        {
            return false;
        }
        return true;
    }
}
