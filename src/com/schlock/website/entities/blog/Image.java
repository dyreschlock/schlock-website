package com.schlock.website.entities.blog;

import com.schlock.website.entities.Persisted;
import org.apache.commons.lang.StringUtils;

public class Image extends Persisted
{
    public static final String GOOGLE_DRIVE_IMAGE_LINK = "https://drive.google.com/uc?id=";

    public static final String WEBP_FOLDER_NAME = "webp";
    public static final String WEBP_FILE_EXT = ".webp";

    private String directory;
    private String galleryName;
    private String imageName;

    private String webpGoogleId;
    private String webpDirectLink;

    private String commentText;

    private Image parent; // full version of the thumbnail


    public String getImageLink()
    {
        if(webpDirectLink != null && !webpDirectLink.isEmpty())
        {
            return webpDirectLink;
        }
        if (webpGoogleId != null && !webpGoogleId.isEmpty())
        {
            return GOOGLE_DRIVE_IMAGE_LINK + webpGoogleId;
        }
        if (StringUtils.isBlank(galleryName))
        {
            return "/" + WEBP_FOLDER_NAME + "/" + directory + "/" + getWebpFilename();
        }
        return "/" + WEBP_FOLDER_NAME + "/" + directory + "/" + galleryName + "/" + getWebpFilename();
    }

    private String getLocalPath()
    {
        String path = directory + "/";
        if (StringUtils.isNotBlank(galleryName))
        {
            path += galleryName + "/";
        }
        return path;
    }

    public String getImagePath()
    {
        return getLocalPath() + imageName;
    }

    public String getWebpFilename()
    {
        String name = imageName.substring(0, imageName.lastIndexOf("."));
        return name + WEBP_FILE_EXT;
    }

    public String getWebpFilepath()
    {
        return getLocalPath() + getWebpFilename();
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

    public String getWebpGoogleId()
    {
        return webpGoogleId;
    }

    public void setWebpGoogleId(String webpGoogleId)
    {
        this.webpGoogleId = webpGoogleId;
    }

    public String getWebpDirectLink()
    {
        return webpDirectLink;
    }

    public void setWebpDirectLink(String webpDirectLink)
    {
        this.webpDirectLink = webpDirectLink;
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
