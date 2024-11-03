package com.schlock.website.components.courses;

import com.schlock.website.entities.blog.Page;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Parameter;

public abstract class CoursePageDisplay
{
    @Parameter(required = true)
    private Page page;

    protected abstract String getPageUuid();

    public boolean isDisplayContent()
    {
        String pageUuid = getPageUuid();
        String selectedUuid = (page != null) ? page.getUuid() : null;

        return StringUtils.equalsIgnoreCase(pageUuid, selectedUuid);
    }



    public Page getPage()
    {
        return page;
    }

    public void setPage(Page page)
    {
        this.page = page;
    }
}
