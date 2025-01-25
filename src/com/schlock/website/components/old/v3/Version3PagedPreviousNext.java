package com.schlock.website.components.old.v3;

import com.schlock.website.components.old.AbstractOldPagedPreviousNext;
import com.schlock.website.entities.old.SiteVersion;
import org.apache.tapestry5.annotations.Parameter;

import java.util.List;

public class Version3PagedPreviousNext extends AbstractOldPagedPreviousNext
{
    @Parameter(required = true)
    private List<Long> categoryIds;



    protected SiteVersion getVersion()
    {
        return SiteVersion.V3;
    }

    public List<Long> getCategoryIds()
    {
        return categoryIds;
    }

    public boolean isHasPrevious()
    {
        if (isPostBasePage())
        {
            return true;
        }
        return super.isHasPrevious();
    }

    public String getPreviousPageLink()
    {
        if (getPost() != null)
        {
            return getPageLink(1);
        }
        return super.getPreviousPageLink();
    }

    protected int getPagePostCount(int pageNumber)
    {
        return super.getPagePostCount(pageNumber);
    }
}
