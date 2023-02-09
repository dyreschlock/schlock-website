package com.schlock.website.components.blog.layout;

import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.blog.GoogleManagement;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.blog.PostManagement;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Header
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
    void onTestGoogle()
    {
        try
        {
            googleManagement.generateIdsForFoldersImages();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @CommitAfter
    void onRegenImages()
    {
        try
        {
            imageManagement.generateImages();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @CommitAfter
    void onRegenHTML()
    {
        postManagement.regenerateAllPostHTML();
    }
}
