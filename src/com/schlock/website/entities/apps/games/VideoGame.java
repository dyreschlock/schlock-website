package com.schlock.website.entities.apps.games;

import com.schlock.website.entities.Persisted;
import com.schlock.website.entities.apps.ps2.PlaystationGame;

import java.util.Date;

public class VideoGame extends Persisted
{
    private String title;

    private String publisher;
    private String developer;

    private Date releaseDate;

    private Region region;
    private Condition condition;

    private PlaystationGame playstationGame;

    private String postUUID;

    public VideoGame()
    {
    }

    public boolean isHaveSave()
    {
        if (playstationGame != null)
        {
            return playstationGame.isHaveSave();
        }
        return false;
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

    public PlaystationGame getPlaystationGame()
    {
        return playstationGame;
    }

    public void setPlaystationGame(PlaystationGame playstationGame)
    {
        this.playstationGame = playstationGame;
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
