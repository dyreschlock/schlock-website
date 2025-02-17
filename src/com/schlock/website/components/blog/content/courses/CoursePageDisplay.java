package com.schlock.website.components.blog.content.courses;

import com.schlock.website.entities.blog.Page;
import com.schlock.website.services.blog.PostContentsManagement;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class CoursePageDisplay
{
    @Parameter(required = true)
    private Page page;

    @Inject
    private PostContentsManagement contentsManagement;


    public boolean isDisplayContent()
    {
        String html = getHtmlContents();
        return StringUtils.isNotBlank(html);
    }

    public String getHtmlContents()
    {
        return contentsManagement.getHTMLContents(page);
    }
}
