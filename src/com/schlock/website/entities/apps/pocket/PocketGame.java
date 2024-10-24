package com.schlock.website.entities.apps.pocket;

import org.apache.commons.lang.StringUtils;

import java.util.List;

public class PocketGame extends ImagedGame
{
    private String gameName;
    private String developer;
    private String publisher;
    private String releaseDate;
    private String genre;
    private String coreName;
    private String core;
    private String platform;
    private String fileHash;

    private List<Device> devices;

    private String year;
    private String genreId;


    public String getUniqueName()
    {
        return gameName + " (" + coreName + ")";
    }

    public String getYear()
    {
        if (year == null && StringUtils.isNotBlank(releaseDate))
        {
            year = releaseDate.substring(0, 4);
        }
        return year;
    }

    public String getGenreId()
    {
        if (StringUtils.isBlank(genreId))
        {
            genreId = genre.toLowerCase().replaceAll(" ", "");
        }
        return genreId;
    }

    public String getCore()
    {
        if (PocketCore.ATARI_7800.equalsIgnoreCase(core))
        {
            return PocketCore.ATARI_7800_FIXED;
        }
        if (PocketCore.ATARI_2600.equalsIgnoreCase(core))
        {
            return PocketCore.ATARI_2600_FIXED;
        }
        if (PocketCore.PC_486.equalsIgnoreCase(core))
        {
            return PocketCore.PC_486_FIXED;
        }
        return core;
    }

    public boolean isOnMister()
    {
        for(Device device : devices)
        {
            if (device == Device.MISTER)
            {
                return true;
            }
        }
        return false;
    }

    public String getGameName()
    {
        return gameName;
    }

    public void setGameName(String gameName)
    {
        this.gameName = gameName;
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

    public String getReleaseDate()
    {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate)
    {
        this.releaseDate = releaseDate;
    }

    public String getGenre()
    {
        return genre;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    public String getCoreName()
    {
        return coreName;
    }

    public void setCoreName(String coreName)
    {
        this.coreName = coreName;
    }

    public void setCore(String core)
    {
        this.core = core;
    }

    public String getPlatform()
    {
        return platform;
    }

    public void setPlatform(String platform)
    {
        this.platform = platform;
    }

    public String getFileHash()
    {
        return fileHash;
    }

    public void setFileHash(String fileHash)
    {
        this.fileHash = fileHash;
    }

    public List<Device> getDevices()
    {
        return devices;
    }

    public void setDevices(List<Device> devices)
    {
        this.devices = devices;
    }
}
