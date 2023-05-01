package com.schlock.website.entities.apps.games;

import com.schlock.website.entities.Persisted;

import java.util.Date;

public class VideoGame extends Persisted
{
    private String title;

    private String publisher;
    private String developer;

    private Date releaseDate;

    private VideoGameConsole console;

    private Region region;
    private Condition condition;


    public VideoGame()
    {
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getPublisher() { return publisher; }

    public void setPublisher(String publisher) { this.publisher = publisher; }

    public String getDeveloper() { return developer; }

    public void setDeveloper(String developer) { this.developer = developer; }

    public Date getReleaseDate()
    {
        return releaseDate;
    }

    public void setReleaseDate(Date release)
    {
        this.releaseDate = release;
    }

    public VideoGameConsole getConsole() { return console; }

    public void setConsole(VideoGameConsole console) { this.console = console; }

    public Region getRegion() { return region; }

    public void setRegion(Region region) { this.region = region; }

    public Condition getCondition()
    {
        return condition;
    }

    public void setCondition(Condition condition)
    {
        this.condition = condition;
    }
}
