package com.schlock.website.components.old.v6;

import com.schlock.website.components.old.AbstractOldPreviousNext;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.services.blog.PostArchiveManagement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.services.PageRenderLinkSource;

import javax.inject.Inject;

public class Version6PreviousNext extends AbstractOldPreviousNext
{
    @Parameter(required = true)
    private String page;


    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private PostArchiveManagement archiveManagement;




    public String getMonthYearTitle()
    {
        return archiveManagement.getIterationTitle(page);
    }

    public boolean isHasNext()
    {
        if (page != null)
        {
            return getNextMonthYearIteration() != null;
        }
        return super.isHasNext();
    }

    public String getNextPostLink()
    {
        if (page != null)
        {
            return linkSource.createPageRenderLinkWithContext(getVersion().indexClass(), getNextMonthYearIteration()).toURI();
        }
        return super.getNextPostLink();
    }

    public String getNextPostTitle()
    {
        if (page != null)
        {
            return archiveManagement.getIterationTitle(getNextMonthYearIteration());
        }
        return super.getNextPostTitle();
    }

    private String getNextMonthYearIteration()
    {
        return archiveManagement.getNextYearlyMontlyIteration(page, null);
    }


    public boolean isHasPrevious()
    {
        if (page != null)
        {
            return getPreviousMonthYearIteration() != null;
        }
        return super.isHasPrevious();
    }

    public String getPreviousPostLink()
    {
        if (page != null)
        {
            return linkSource.createPageRenderLinkWithContext(getVersion().indexClass(), getPreviousMonthYearIteration()).toURI();
        }
        return super.getPreviousPostLink();
    }

    public String getPreviousPostTitle()
    {
        if (page != null)
        {
            return archiveManagement.getIterationTitle(getPreviousMonthYearIteration());
        }
        return super.getPreviousPostTitle();
    }

    private String getPreviousMonthYearIteration()
    {
        return archiveManagement.getPreviousYearlyMonthlyInteration(page, null);
    }


    protected SiteVersion getVersion()
    {
        return SiteVersion.V6;
    }
}
