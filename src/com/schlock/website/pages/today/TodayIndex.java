package com.schlock.website.pages.today;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.blog.TodayArchiveManagement;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.services.PageRenderLinkSource;

import javax.inject.Inject;
import java.util.Arrays;
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

    @Inject
    private ImageManagement imageManagement;


    private String dateString;


    @Property
    private String currentYear;

    @Property
    private Post currentPost;

    @Property
    private String currentMonth;

    @Property
    private String currentDay;


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
        return archiveManagement.getPreviewPost(dateString);
    }

    public List<String> getYears()
    {
        return archiveManagement.getYears(dateString);
    }

    public List<Post> getPosts()
    {
        return archiveManagement.getPosts(dateString, currentYear);
    }

    public List<Post> getPreviewPosts()
    {
        return archiveManagement.getPreviewPosts(dateString, currentYear);
    }


    public List<String> getMonths()
    {
        return archiveManagement.getAllMonths();
    }

    public String getCurrentMonthTitle()
    {
        return messages.get(currentMonth);
    }

    public List<String> getDays()
    {
        return archiveManagement.getAllDays(currentMonth);
    }

    public String getCurrentDayTitle()
    {
        String day = currentDay;

        if (Arrays.asList("1", "21", "31").contains(day))
        {
            return day + "st";
        }
        if (Arrays.asList("2", "22").contains(day))
        {
            return day + "nd";
        }
        if (Arrays.asList("3", "23").contains(day))
        {
            return day + "rd";
        }
        return day + "th";
    }

    private String getCurrentDateString()
    {
        return currentMonth + "-" + currentDay;
    }

    public String getCurrentDayImageLink()
    {
        String date = getCurrentDateString();
        String uuid = archiveManagement.getPreviewPostUuid(date);

        return imageManagement.getPostPreviewMetadataLink(uuid);
    }

    public String getCurrentDayLink()
    {
        return getReturnLink() + "/" + getCurrentDateString();
    }

    public boolean isHasPosts()
    {
        String date = getCurrentDateString();
        int count = archiveManagement.getPostCount(date);

        return count > 0;
    }

    public String getDayCount()
    {
        String date = getCurrentDateString();
        int count = archiveManagement.getPostCount(date);

        return Integer.toString(count);
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
        if (isDaySelected())
        {
            url += "/" + dateString;
        }
        return url;
    }


    public String getNextDayLink()
    {
        String nextDate = archiveManagement.getNextDayString(dateString);

        String url = getReturnLink() + "/" + nextDate;
        return url;
    }

    public String getNextDayLabel()
    {
        String nextDate = archiveManagement.getNextDayString(dateString);
        String nextText = dateFormatter.todayPrintFormat(nextDate);

        return messages.format("view-posts", nextText);
    }

    public String getPreviousDayLink()
    {
        String previousDate = archiveManagement.getPreviousDayString(dateString);

        String url = getReturnLink() + "/" + previousDate;
        return url;
    }

    public String getPreviousDayLabel()
    {
        String previousDate = archiveManagement.getPreviousDayString(dateString);
        String previousText = dateFormatter.todayPrintFormat(previousDate);

        return messages.format("view-posts", previousText);
    }


    public String getReturnLink()
    {
        String url = linkSource.createPageRenderLink(TodayIndex.class).toURI();
        return url;
    }
}
