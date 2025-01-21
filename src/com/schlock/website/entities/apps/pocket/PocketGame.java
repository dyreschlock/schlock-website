package com.schlock.website.entities.apps.pocket;

import com.schlock.website.entities.apps.ps2.RetroGame;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class PocketGame extends RetroGame
{
    private String coreName;
    private String core;
    private String platformName;
    private String fileHash;

    private List<Device> devices;

    private String year;
    private String genreId;


    public String getUniqueName()
    {
        return getTitle() + " (" + coreName + ")";
    }

    public String getYear()
    {
        if (year == null && StringUtils.isNotBlank(getReleaseDateText()))
        {
            year = getReleaseDateText().substring(0, 4);
        }
        return year;
    }

    public String getGenreId()
    {
        if (StringUtils.isBlank(genreId))
        {
            genreId = getGenre().toLowerCase().replaceAll(" ", "");
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

    public String getCoverImageFilename()
    {
        return "";
    }

    public String getSaveFileRelativeFilepath()
    {
        return "";
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

    public String getPlatformName()
    {
        return platformName;
    }

    public void setPlatformName(String platformName)
    {
        this.platformName = platformName;
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
