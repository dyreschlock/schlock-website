package com.schlock.website.components.old;

import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.services.blog.PostArchiveManagement;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

public abstract class AbstractOldArchiveList
{
    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private PostArchiveManagement archiveManagement;


    @Property
    private String currentMonthYear;

    abstract protected SiteVersion getVersion();


    public List<String> getMonthYears()
    {
        String name = null;
        return archiveManagement.getYearlyMonthlyIterations(name);
    }

    public String getMonthYearLink()
    {
        return linkSource.createPageRenderLinkWithContext(getVersion().indexClass(), currentMonthYear).toURI();
    }

    public String getMonthYearTitle()
    {
        return archiveManagement.getIterationTitle(currentMonthYear);
    }
}
