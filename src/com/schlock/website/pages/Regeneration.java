package com.schlock.website.pages;

import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.blog.GoogleManagement;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.blog.PostManagement;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.io.IOException;

public class Regeneration
{
    @Inject
    private DeploymentContext deploymentContext;

    @Inject
    private ImageManagement imageManagement;

    @Inject
    private PostManagement postManagement;

    @Inject
    private GoogleManagement googleManagement;

    public boolean isLocal()
    {
        return deploymentContext.isLocal();
    }

    /*
     * No slash at the beginning. Slash at the end.
     */
    private static final String LOCATION = "";


    @CommitAfter
    void onProcessImageDirectory()
    {
        onSanitizeImages();
        onGenerateThumbnails();
    }

    @CommitAfter
    void onSanitizeImages()
    {
        if (StringUtils.isNotBlank(LOCATION))
        {
            String imageLocation = deploymentContext.webDirectory() + LOCATION;
            try
            {
                imageManagement.removeInvalidCharactersFromImageFilenames(imageLocation);
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    @CommitAfter
    void onGenerateThumbnails()
    {
        if (StringUtils.isNotBlank(LOCATION))
        {
            String imageLocation = deploymentContext.webDirectory() + LOCATION;
            try
            {
                imageManagement.createThumbnailsForDirectory(imageLocation);
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    @CommitAfter
    void onRegenPostItems()
    {
        onCreatePostPreviewImages();
        onGenerateImageObjects();
        onRegenHTML();
        onGenerateWebpFiles();
    }

    @CommitAfter
    void onCreatePostPreviewImages()
    {
        imageManagement.createPostPreviewImages();
    }

    @CommitAfter
    void onGenerateImageObjects()
    {
        imageManagement.generateImageObjects();
    }

    @CommitAfter
    void onRegenHTML()
    {
        postManagement.regenerateAllPostHTML();
    }

    @CommitAfter
    void onGenerateWebpFiles()
    {
        try
        {
            imageManagement.generateWebpFilesFromImages();
        }
        catch (Exception e)
        {
            e.printStackTrace();
//            throw new RuntimeException(e);
        }
    }

    @CommitAfter
    void onGenerateGoogleItems()
    {
        onGenerateGoogleImageIds();
        onGenerateImageDirectLinks();
    }

    @CommitAfter
    void onGenerateGoogleImageIds()
    {
        googleManagement.generateGoogleImageIds();
    }

    @CommitAfter
    void onGenerateImageDirectLinks()
    {
        googleManagement.updateImagesWithDirectLinks();
    }
}
