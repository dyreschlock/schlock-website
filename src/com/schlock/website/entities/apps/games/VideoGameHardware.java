package com.schlock.website.entities.apps.games;

import com.schlock.website.entities.Persisted;

import java.util.Date;

public class VideoGameHardware extends Persisted
{
    private String title;

    private String developer;
    private String manufacturer;

    private Date releaseDate;

    private Region region;
    private HardwareType hardwareType;

    private boolean sold;

    private String postUUID;

    public VideoGameHardware()
    {
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDeveloper()
    {
        return developer;
    }

    public void setDeveloper(String developer)
    {
        this.developer = developer;
    }

    public String getManufacturer()
    {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer)
    {
        this.manufacturer = manufacturer;
    }

    public Date getReleaseDate()
    {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate)
    {
        this.releaseDate = releaseDate;
    }

    public Region getRegion()
    {
        return region;
    }

    public void setRegion(Region region)
    {
        this.region = region;
    }

    public HardwareType getHardwareType()
    {
        return hardwareType;
    }

    public void setHardwareType(HardwareType hardwareType)
    {
        this.hardwareType = hardwareType;
    }

    public boolean isSold()
    {
        return sold;
    }

    public void setSold(boolean sold)
    {
        this.sold = sold;
    }

    public String getPostUUID()
    {
        return postUUID;
    }

    public void setPostUUID(String postUUID)
    {
        this.postUUID = postUUID;
    }
}
