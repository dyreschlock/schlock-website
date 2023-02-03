package com.schlock.website.components.blog.layout;

import com.schlock.website.pages.AboutMe;
import com.schlock.website.pages.archive.ArchiveIndex;
import com.schlock.website.pages.Index;
import com.schlock.website.pages.Projects;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.blog.PostManagement;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class Header
{
    @Inject
    private DeploymentContext deploymentContext;

    @Inject
    private PostManagement postManagement;


    public boolean isLocal()
    {
        return deploymentContext.isLocal();
    }

    @CommitAfter
    void onRegeneratePostHTML()
    {
        postManagement.regenerateAllPostHTML();
    }
}
