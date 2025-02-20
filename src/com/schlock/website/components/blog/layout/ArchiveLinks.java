package com.schlock.website.components.blog.layout;

import com.schlock.website.entities.blog.Page;
import com.schlock.website.pages.archive.ArchiveIndex;
import com.schlock.website.pages.keyword.KeywordIndex;
import com.schlock.website.pages.today.TodayIndex;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class ArchiveLinks
{
    @Inject
    private PageRenderLinkSource linkSource;

    @Parameter(required = true)
    private String selected;


    public String getArchiveClass()
    {
        return getSelectedClass(Page.POST_ARCHIVE_UUID);
    }

    public String getTodayClass()
    {
        return getSelectedClass(Page.TODAYS_POSTS_UUID);
    }

    public String getKeywordClass()
    {
        return getSelectedClass(Page.KEYWORD_CLOUD_UUID);
    }

    public String getPageClass()
    {
        return getSelectedClass(Page.PAGE_ARCHIVE_UUID);
    }

    private String getSelectedClass(String page)
    {
        if (page.equals(selected))
        {
            return "selected";
        }
        return "unselected";
    }


    public String getArchiveLink()
    {
        return linkSource.createPageRenderLink(ArchiveIndex.class).toURI();
    }

    public String getTodayLink()
    {
        return linkSource.createPageRenderLink(TodayIndex.class).toURI();
    }

    public String getKeywordLink()
    {
        return linkSource.createPageRenderLink(KeywordIndex.class).toURI();
    }

    public String getPageLink()
    {
        return "";
    }
}
