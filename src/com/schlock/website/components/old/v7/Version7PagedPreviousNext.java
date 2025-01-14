package com.schlock.website.components.old.v7;

import com.schlock.website.components.old.AbstractOldPagedPreviousNext;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;
import com.schlock.website.pages.old.v7.V7Index;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class Version7PagedPreviousNext extends AbstractOldPagedPreviousNext
{
    @Inject
    private PageRenderLinkSource linkSource;

    protected SiteVersion getVersion()
    {
        return SiteVersion.V7;
    }

    public boolean isHasNext()
    {
        if (isPostBasePage())
        {
            return true;
        }
        return super.isHasNext();
    }

    public String getNextPageLink()
    {
        if (isPostBasePage())
        {
            return linkSource.createPageRenderLinkWithContext(V7Index.class, AbstractOldVersionPage.BLOG_PAGE).toURI();
        }
        return super.getNextPageLink();
    }
}
