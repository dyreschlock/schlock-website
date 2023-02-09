package com.schlock.website.entities.blog;

import com.schlock.website.entities.Persisted;

public class ImageFolder extends Persisted
{
    private String folderName;
    private String googleId;

    private ImageFolder parent;

    public String getFolderName()
    {
        return folderName;
    }

    public void setFolderName(String folderName)
    {
        this.folderName = folderName;
    }

    public String getGoogleId()
    {
        return googleId;
    }

    public void setGoogleId(String googleId)
    {
        this.googleId = googleId;
    }

    public ImageFolder getParent()
    {
        return parent;
    }

    public void setParent(ImageFolder parent)
    {
        this.parent = parent;
    }
}
