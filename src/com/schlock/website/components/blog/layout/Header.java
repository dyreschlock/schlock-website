package com.schlock.website.components.blog.layout;

import com.schlock.website.pages.AboutMe;
import com.schlock.website.pages.Archive;
import com.schlock.website.pages.Index;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.blog.PostManagement;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class Header
{
    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private DeploymentContext deploymentContext;

    @Inject
    private PostManagement postManagement;


    Object onHome()
    {
        return linkSource.createPageRenderLinkWithContext(Index.class);
    }

    Object onArchive()
    {
        return Archive.class;
    }

    Object onAboutMe()
    {
        return AboutMe.class;
    }

    public boolean isLocal()
    {
        return deploymentContext.isLocal();
    }

    @CommitAfter
    void onRegeneratePostHTML()
    {
        postManagement.regenerateAllPostHTML();
    }

    Object onCodejamLink()
    {
        return linkSource.createPageRenderLink(com.schlock.website.pages.codejam.may2012.Index.class);
    }
}
