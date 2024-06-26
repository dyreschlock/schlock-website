package com.schlock.website.entities.apps.ps2;

import com.schlock.website.entities.apps.pocket.ImagedGame;

import java.io.File;
import java.util.Date;

public class PlaystationGame extends ImagedGame
{
    public static final String ART_FOLDER = "ART";
    public static final String CFG_FOLDER = "CFG";

    public static final String ART_FILETYPE = "_COV.jpg";
    public static final String CFG_FILETYPE = ".cfg";

    private String gameId;

    private String gameName;

    private PlaystationPlatform platform;
    private String drive;

    private String working;

    private boolean haveArt;

    private String title;
    private String genre;

    private String developer;
    private String publisher;

    private Date releaseDate;

    private PlaystationGameAspect aspect;
    private PlaystationGamePlayers players;
    private PlaystationGameVmode vmode;
    private PlaystationGameScan scan;
    private PlaystationGameParental parental;

    private String notes;
    private String description;

    public PlaystationGame()
    {
    }

    public String getGameRelativeFilepath()
    {
        String folder = platform.folder();
        String fileType = platform.fileType();

        return folder + "/" + gameId + "." + gameName + fileType;
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

    public boolean isHaveArt()
    {
        return haveArt;
    }

    public void setHaveArt(boolean haveArt)
    {
        this.haveArt = haveArt;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getGenre()
    {
        return genre;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    public String getDeveloper()
    {
        return developer;
    }

    public void setDeveloper(String developer)
    {
        this.developer = developer;
    }

    public String getPublisher()
    {
        return publisher;
    }

    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }

    public Date getReleaseDate()
    {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate)
    {
        this.releaseDate = releaseDate;
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
}
