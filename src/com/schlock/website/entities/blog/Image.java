package com.schlock.website.entities.blog;

import com.schlock.website.entities.Persisted;

public class Image extends Persisted
{
    private String imageName;

    private String googleId;

    private String commentText;



    public String getImageName() { return imageName; }

    public void setImageName(String imageName) { this.imageName = imageName; }

    public String getGoogleId()
    {
        return googleId;
    }

    public void setGoogleId(String googleId)
    {
        this.googleId = googleId;
    }

    public String getCommentText() { return commentText; }

    public void setCommentText(String commentText) { this.commentText = commentText; }
}
