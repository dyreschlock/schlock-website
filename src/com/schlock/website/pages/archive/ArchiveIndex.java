package com.schlock.website.pages.archive;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.ViewState;
import com.schlock.website.services.blog.PostArchiveManagement;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArchiveIndex
{
    @Inject
    private Messages messages;

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private PostArchiveManagement archiveManagement;

    @Inject
    private PostManagement postManagement;

    @Inject
    private PostDAO postDAO;


    @SessionState
    private ViewState viewState;


    private Integer year;
    private Integer month;

    @Property
    private String currentIteration;

    @Property
    private Post currentPost;


    Object onActivate()
    {
        year = null;
        month = null;

        return true;
    }

    Object onActivate(String yearParam)
    {
        year = getYearFromParam(yearParam);
        month = null;

        return true;
    }

    Object onActivate(String yearParam, String monthParam)
    {
        year = getYearFromParam(yearParam);
        month = getMonthFromParam(year, monthParam);

        return true;
    }

    private Integer getYearFromParam(String param)
    {
        Integer possibleYear = null;
        try
        {
            possibleYear = Integer.valueOf(param);
        }
        catch (NumberFormatException e)
        {
        }

        boolean unpublished = viewState.isShowUnpublished();
        List<Integer> years = postDAO.getAllYears(unpublished);
        if (years.contains(possibleYear))
        {
            return possibleYear;
        }
        return null;
    }

    private Integer getMonthFromParam(Integer year, String param)
    {
        Integer possibleMonth = null;
        try
        {
            possibleMonth = Integer.valueOf(param);
        }
        catch (NumberFormatException e)
        {
        }

        boolean unpublished = viewState.isShowUnpublished();
        List<Integer> months = postDAO.getMonths(year, unpublished);
        if (months.contains(possibleMonth))
        {
            return possibleMonth;
        }
        return null;
    }

    public List<String> getIterations()
    {
        return archiveManagement.getYearlyMonthlyIterations(year, month);
    }

    public boolean isPageIteration()
    {
        Integer y = archiveManagement.parseYear(currentIteration);
        Integer m = archiveManagement.parseMonth(currentIteration);

        if (year == null)
        {
            if (y == null)
            {
                return true;
            }
            return false;
        }
        if (month == null)
        {
            if (m == null)
            {
                return true;
            }
            return false;
        }
        return year.equals(y) && month.equals(m);
    }

    public String getIterationTitle()
    {
        Integer year = archiveManagement.parseYear(currentIteration);
        Integer month = archiveManagement.parseMonth(currentIteration);

        return title(year, month);
    }

    Object onSelectIteration(String iteration)
    {
        Integer year = archiveManagement.parseYear(iteration);
        Integer month = archiveManagement.parseMonth(iteration);

        if (year != null && month != null)
        {
            return linkSource.createPageRenderLinkWithContext(ArchiveIndex.class, year, month);
        }
        if (year != null)
        {
            return linkSource.createPageRenderLinkWithContext(ArchiveIndex.class, year);
        }
        return onSelectIteration();
    }

    public boolean isSubiteration()
    {
        return year != null;
    }

    Object onSelectIteration()
    {
        return linkSource.createPageRenderLinkWithContext(ArchiveIndex.class);
    }


    public Post getMostRecent()
    {
        int LIMIT = 1;
        List<Post> posts = postManagement.getTopPostsForYearMonth(LIMIT, year, month, Collections.EMPTY_LIST);
        return posts.get(0);
    }

    public List<Post> getPosts()
    {
        return archiveManagement.getPosts(currentIteration);
    }

    public List<Post> getPreviewPosts()
    {
        List<Long> exclude = Arrays.asList(getMostRecent().getId());

        return archiveManagement.getPreviewPosts(currentIteration, exclude);
    }


    public String getParentIteration()
    {
        return archiveManagement.getParentIteration(year, month);
    }

    public String getReturnToPrevious()
    {
        if (month != null)
        {
            return messages.format("return", year);
        }
        if (year != null)
        {
            String def = messages.get("page-title");
            return messages.format("return", def);
        }
        return "";
    }


    private String title(Integer year, Integer month)
    {
        String title = "";
        if (year != null)
        {
            if (month != null)
            {
                String m = messages.get(Integer.toString(month));
                title += m + " ";
            }
            title += year;
        }
        return title;
    }

    public String getPageTitle()
    {
        String title = "";
        if (year != null)
        {
            title = title(year, month);
        }
        else
        {
            title = messages.get("page-title");
        }
        return title;
    }
}
