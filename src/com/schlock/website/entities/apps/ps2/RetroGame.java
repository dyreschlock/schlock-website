package com.schlock.website.entities.apps.ps2;

import com.schlock.website.entities.Persisted;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class RetroGame extends Persisted
{
    private String title;

    private String developer;
    private String publisher;
    private Date releaseDate;
    private String genre;

    public String getReleaseDateText()
    {
        if (getReleaseDate() != null)
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.format(getReleaseDate());
        }
        return "";
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
}
