package com.schlock.website.entities.blog;

import com.schlock.website.entities.Persisted;
import org.apache.commons.lang.StringUtils;

public class Image extends Persisted
{
    private String directory;
    private String galleryName;
    private String imageName;

    private String googleId;

    private String commentText;

    private Image parent; // full version of the thumbnail


    public static final String GOOGLE_IMAGE_LINK = "https://drive.google.com/uc?id=";

    public String getImageLink()
    {
        if (googleId != null && !googleId.isEmpty())
        {
            return GOOGLE_IMAGE_LINK + googleId;
        }
        if (StringUtils.isBlank(galleryName))
        {
            return "/" + directory + "/" + imageName;
        }
        return "/" + directory + "/" + galleryName + "/" + imageName;
    }


    public boolean isThumbnail()
    {
        return parent != null;
    }


    public String getDirectory()
    {
        return directory;
    }

    public void setDirectory(String directory)
    {
        this.directory = directory;
    }

    public String getGalleryName()
    {
        return galleryName;
    }

    public void setGalleryName(String galleryName)
    {
        this.galleryName = galleryName;
    }

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

    public Image getParent()
    {
        return parent;
    }

    public void setParent(Image parent)
    {
        this.parent = parent;
    }
}
