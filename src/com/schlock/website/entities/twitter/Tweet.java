package com.schlock.website.entities.twitter;

import com.schlock.website.entities.Persisted;

import java.util.List;

public class Tweet extends Persisted
{
    private String tweetId;

    private String bodyText;

    private Tweet parent;

    private List<TweetImage> images;



    public String getTweetId()
    {
        return tweetId;
    }

    public void setTweetId(String tweetId)
    {
        this.tweetId = tweetId;
    }

    public String getBodyText()
    {
        return bodyText;
    }

    public void setBodyText(String bodyText)
    {
        this.bodyText = bodyText;
    }
}
