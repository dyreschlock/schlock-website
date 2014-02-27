package com.schlock.website.entities.blog;

public class PhotoPost extends Post
{
    private String fileLocation;
    private String text;


    public PhotoPost(String uuid)
    {
        super(uuid);
    }


    public String getFileLocation()
    {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation)
    {
        this.fileLocation = fileLocation;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }
}
