package com.schlock.website.pages;

import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.blog.GoogleManagement;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.blog.PostManagement;
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

    @CommitAfter
    void onGenerateThumbnails()
    {
        String LOCATION = "";

        LOCATION = deploymentContext.webDirectory() + LOCATION;
        try
        {
            imageManagement.createThumbnailsForDirectory(LOCATION);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @CommitAfter
    void onGeneratePostItems()
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
