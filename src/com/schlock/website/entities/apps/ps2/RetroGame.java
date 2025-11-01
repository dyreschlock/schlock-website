package com.schlock.website.entities.apps.ps2;

import com.schlock.website.entities.Icon;
import com.schlock.website.entities.Persisted;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RetroGame extends Persisted
{
    private String title;

    private String developer;
    private String publisher;
    private Date releaseDate;
    private String genre;

    private PlaystationPlatform platform;

    private String postUUID;

    private boolean available;

    private boolean haveArt;
    private boolean haveSave;

    private boolean subDisc;

    public RetroGame()
    {
    }


    public String getReleaseDateText()
    {
        if (getReleaseDate() != null)
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.format(getReleaseDate());
        }
        return "";
    }

    public String getCoverImageFilename()
    {
        return "";
    }

    public String getSaveFileRelativeFilepath()
    {
        return "";
    }

    public Icon getMemcardIcon()
    {
        return null;
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

    public String getGenre()
    {
        return genre;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    public PlaystationPlatform getPlatform()
    {
        return platform;
    }

    public void setPlatform(PlaystationPlatform platform)
    {
        this.platform = platform;
    }

    public String getPostUUID()
    {
        return postUUID;
    }

    public void setPostUUID(String postUUID)
    {
        this.postUUID = postUUID;
    }

    public boolean isAvailable()
    {
        return available;
    }

    public void setAvailable(boolean available)
    {
        this.available = available;
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

    public boolean isSubDisc()
    {
        return subDisc;
    }

    public void setSubDisc(boolean subDisc)
    {
        this.subDisc = subDisc;
    }
}
