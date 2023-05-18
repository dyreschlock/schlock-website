package com.schlock.website.entities.apps.games;

import com.schlock.website.entities.Persisted;

import java.util.Date;

public class VideoGameHardware extends Persisted
{
    private String title;

    private String publisher;
    private String developer;

    private Date releaseDate;

    private Region region;

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

    public String getPublisher()
    {
        return publisher;
    }

    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }

    public String getDeveloper()
    {
        return developer;
    }

    public void setDeveloper(String developer)
    {
        this.developer = developer;
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

    public String getPostUUID()
    {
        return postUUID;
    }

    public void setPostUUID(String postUUID)
    {
        this.postUUID = postUUID;
    }
}
