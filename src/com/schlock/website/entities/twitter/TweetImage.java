package com.schlock.website.entities.twitter;

import com.schlock.website.entities.Persisted;

public class TweetImage extends Persisted
{
    private String imageId;



    public String getImageId()
    {
        return imageId;
    }

    public void setImageId(String imageId)
    {
        this.imageId = imageId;
    }
}
