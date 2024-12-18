package com.schlock.website.pages.today;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.services.blog.TodayArchiveManagement;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.services.PageRenderLinkSource;

import javax.inject.Inject;

public class TodayIndex
{
    @Inject
    private Messages messages;

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private TodayArchiveManagement archiveManagement;

    private String dateString;


    Object onActivate()
    {
        dateString = null;

        return true;
    }

    Object onActivate(String param)
    {
        //TODO: verify
        dateString = param;

        return true;
    }

    public boolean isDaySelected()
    {
        return StringUtils.isNotBlank(dateString);
    }


    public AbstractPost getMostRecent()
    {
        return archiveManagement.getMostRecent(dateString);
    }



    public String getPageTitle()
    {
        if (isDaySelected())
        {

        }
        return messages.get("page-title");
    }

    public String getPageDescription()
    {
        return messages.get("description");
    }

    public String getPageUrl()
    {
        String url = linkSource.createPageRenderLink(TodayIndex.class).toURI();

        return url;
    }
}
