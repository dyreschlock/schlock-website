package com.schlock.website.entities.blog;

import com.schlock.website.entities.Persisted;
import com.schlock.website.services.DeploymentContext;
import org.apache.commons.lang.StringUtils;

import java.util.zip.CRC32;

public class Image extends Persisted
{
    public static final String GITHUB_IMAGE_LINK = "https://raw.githubusercontent.com/dyreschlock/dyreschlock.github.photos/master";

    public static final String WEBP_FOLDER_NAME = "webp";
    public static final String WEBP_FILE_EXT = ".webp";

    public static final String SVG_FILE_EXT = ".svg";


    public static final int FAVORITE = 2;
    public static final int NOT_FAVORITE = 0;

    private String directory;
    private String galleryName;
    private String imageName;

    private String commentText;

    private Image parent; // full version of the thumbnail

    private int favorite;
    private String filenameHash;

    private String postUuid;

    public String getImageLink()
    {
        if (StringUtils.isBlank(galleryName))
        {
            return GITHUB_IMAGE_LINK + "/" + directory + "/" + getWebpFilename();
        }
        return GITHUB_IMAGE_LINK + "/" + directory + "/" + galleryName + "/" + getWebpFilename();
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
        if (imageName.toLowerCase().endsWith(SVG_FILE_EXT))
        {
            return imageName;
        }
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

    public boolean isSameImage(String imageName)
    {
        try
        {
            return (StringUtils.equalsIgnoreCase(this.imageName, imageName)) ||
                    (this.parent != null && StringUtils.equalsIgnoreCase(this.parent.getImageName(), imageName));
        }
        catch(Exception e)
        {
            throw new RuntimeException("Broken images? " + this.imageName + " or " + imageName, e);
        }
    }

    public void updateFilenameHash()
    {
        String uniqueId = directory + galleryName + imageName;

        CRC32 hash = new CRC32();
        hash.update(uniqueId.getBytes());

        filenameHash = Long.toHexString(hash.getValue());
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

    public int getFavorite()
    {
        return favorite;
    }

    public void setFavorite(int favorite)
    {
        this.favorite = favorite;
    }

    public String getFilenameHash()
    {
        return filenameHash;
    }

    public void setFilenameHash(String filenameHash)
    {
        this.filenameHash = filenameHash;
    }

    public String getPostUuid()
    {
        return postUuid;
    }

    public void setPostUuid(String postUuid)
    {
        this.postUuid = postUuid;
    }

    public static Image create(String directory, String galleryName, String imageName)
    {
        final String PHOTO_EX_DIR = DeploymentContext.PHOTO_EX_DIR.substring(0, DeploymentContext.PHOTO_EX_DIR.length() - 1);

        Image image = new Image();
        image.favorite = NOT_FAVORITE;
        if(PHOTO_EX_DIR.equals(directory))
        {
            image.favorite = FAVORITE;
        }

        image.directory = directory;
        image.galleryName = galleryName;
        image.imageName = imageName;
        image.updateFilenameHash();

        return image;
    }
}
