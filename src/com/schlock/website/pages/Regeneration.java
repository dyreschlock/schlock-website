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
    void onExecuteAll()
    {
        onGenerateImageObjects();
        onCreatePostPreviewImages();
        onRegenHTML();
        onRegenGoogle();
    }

    @CommitAfter
    void onGenerateImageObjects()
    {
        imageManagement.generateImageObjects();
    }

    @CommitAfter
    void onCreatePostPreviewImages()
    {
        imageManagement.createPostPreviewImages();
    }

    @CommitAfter
    void onRegenHTML()
    {
        postManagement.regenerateAllPostHTML();
    }

    @CommitAfter
    void onRegenGoogle()
    {
        googleManagement.generateGoogleImageDetails();
    }

    @CommitAfter
    void onGenerateThumbnails()
    {
        String LOCATION = "";

        try
        {
            imageManagement.createThumbnailsForDirectory(LOCATION);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
