package com.schlock.website.pages.today;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.blog.TodayArchiveManagement;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.services.PageRenderLinkSource;

import javax.inject.Inject;
import java.util.List;

public class TodayIndex
{
    @Inject
    private DateFormatter dateFormatter;

    @Inject
    private Messages messages;

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private TodayArchiveManagement archiveManagement;

    private String dateString;


    @Property
    private String currentYear;

    @Property
    private AbstractPost currentPost;


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

    public List<String> getYears()
    {
        return archiveManagement.getYears(dateString);
    }

    public List<AbstractPost> getPosts()
    {
        return archiveManagement.getPosts(dateString, currentYear);
    }

    public List<AbstractPost> getPreviewPosts()
    {
        return archiveManagement.getPreviewPosts(dateString, currentYear);
    }


    public String getPageTitle()
    {
        if (isDaySelected())
        {
            String date = dateFormatter.todayPrintFormat(dateString);
            return messages.format("page-title-today", date);
        }
        return messages.get("page-title");
    }

    public String getPageDescription()
    {
        if (isDaySelected())
        {
            String date = dateFormatter.todayPrintFormat(dateString);
            return messages.format("description-today", date);
        }
        return messages.get("description");
    }

    public String getPageUrl()
    {
        String url = linkSource.createPageRenderLink(TodayIndex.class).toURI();

        return url;
    }
}
