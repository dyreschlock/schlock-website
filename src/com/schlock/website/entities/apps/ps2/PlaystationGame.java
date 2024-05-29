package com.schlock.website.entities.apps.ps2;

import com.schlock.website.entities.Persisted;

import java.io.File;

public class PlaystationGame extends Persisted
{
    private String gameId;

    private String gameName;

    private String platform;
    private String location;

    private String working;

    private boolean haveArt;
    private boolean haveCfg;

    public PlaystationGame()
    {
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

    public String getPlatform()
    {
        return platform;
    }

    public void setPlatform(String platform)
    {
        this.platform = platform;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
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

    public boolean isHaveCfg()
    {
        return haveCfg;
    }

    public void setHaveCfg(boolean haveCfg)
    {
        this.haveCfg = haveCfg;
    }

    public static PlaystationGame create(File file, String platform)
    {
        PlaystationGame game = new PlaystationGame();

        String filename = file.getName();

        game.gameId = filename.substring(0, 11);
        game.gameName = filename.substring(12, filename.length() - 4);

        game.platform = platform;

        game.working = "";
        game.haveArt = false;
        game.haveCfg = false;

        return game;
    }
}
