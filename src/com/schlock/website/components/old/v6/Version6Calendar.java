package com.schlock.website.components.old.v6;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.pages.old.v6.V6Index;
import com.schlock.website.services.blog.PostArchiveManagement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Version6Calendar
{
    @Parameter(required = true)
    private String monthYear;

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private PostArchiveManagement archiveManagement;


    private HashMap<Integer, String> cachedPosts;


    private String getCurrentMonthYear()
    {
        if (monthYear != null && archiveManagement.isIteration(monthYear))
        {
            return monthYear;
        }
        String name = null;
        String recent = archiveManagement.getYearlyMonthlyIterations(name).get(0);
        return recent;
    }

    public String getMonthYearTitle()
    {
        return archiveManagement.getIterationTitle(getCurrentMonthYear());
    }


    public String getTableHTML()
    {
        final int COLUMNS = 7;

        final String BLANK = "&nbsp;";
        final String CELL = "<td align=\"center\"><span class=\"calendarCell\">%s</span></td>";
        final String NEW_LINE = "</tr><tr>";
        final String LINK = "<a href=\"%s\">%s</a>";

        String iter = getCurrentMonthYear();
        int year = archiveManagement.parseYear(iter);
        int month = archiveManagement.parseMonth(iter) -1;

        Calendar date = Calendar.getInstance();
        date.set(year, month, 1);

        initializePostsPerDay();

        int index = date.get(Calendar.DAY_OF_WEEK) -1;

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < index; i++)
        {
            String cell = String.format(CELL, BLANK);
            sb.append(cell);
        }

        while(date.get(Calendar.MONTH) == month)
        {
            if (index % COLUMNS == 0)
            {
                sb.append(NEW_LINE);
                index = 0;
            }

            String content = Integer.toString(date.get(Calendar.DAY_OF_MONTH));
            if (hasPosts(date))
            {
                String link = getPostLink(date);
                content = String.format(LINK, link, content);
            }
            String cell = String.format(CELL, content);
            sb.append(cell);

            date.add(Calendar.DAY_OF_MONTH, 1);
            index++;
        }

        while(index % COLUMNS != 0)
        {
            String cell = String.format(CELL, BLANK);
            sb.append(cell);

            index++;
        }

        return sb.toString();
    }

    private boolean hasPosts(Calendar date)
    {
        return getPostUuid(date) != null;
    }

    private String getPostLink(Calendar date)
    {
        String uuid = getPostUuid(date);
        return linkSource.createPageRenderLinkWithContext(V6Index.class, uuid).toURI();
    }

    private String getPostUuid(Calendar date)
    {
        Integer day = date.get(Calendar.DAY_OF_MONTH);
        String uuid = cachedPosts.get(day);
        return uuid;
    }

    private void initializePostsPerDay()
    {
        cachedPosts = new HashMap<>();

        List<Post> posts = archiveManagement.getPosts(getCurrentMonthYear());
        for(Post post : posts)
        {
            Calendar created = Calendar.getInstance();
            created.setTime(post.getCreated());

            Integer day = created.get(Calendar.DAY_OF_MONTH);

            if (!cachedPosts.containsKey(day))
            {
                cachedPosts.put(day, post.getUuid());
            }
        }
    }
}
