package com.schlock.website.components.old.v4;

import com.schlock.website.components.old.AbstractOldPagedPreviousNext;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Parameter;

import java.util.List;

public class Version4PagedPreviousNext extends AbstractOldPagedPreviousNext
{
    @Parameter(required = true)
    private List<Long> categoryIds;

    protected SiteVersion getVersion()
    {
        return SiteVersion.V4;
    }

    public List<Long> getCategoryIds()
    {
        return categoryIds;
    }

    protected String getLinkContext()
    {
        if (StringUtils.isBlank(getPage()))
        {
            return AbstractOldVersionPage.ARCHIVE_PAGE;
        }
        return super.getLinkContext();
    }

    public boolean isHasPrevious()
    {
        if (isPostBasePage())
        {
            return true;
        }
        if (AbstractOldVersionPage.REVIEWS_PAGE.equals(getPage()) || getPost() != null)
        {
            return false;
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
}
