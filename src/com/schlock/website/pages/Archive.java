package com.schlock.website.pages;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.ViewState;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Archive
{
    private static final String ITERATION_DELIM = "-";

    @Inject
    private Messages messages;

    @Inject
    private PageRenderLinkSource linkSource;

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

    public Post getMostRecent()
    {
        int LIMIT = 1;
        List<Post> posts = postManagement.getTopPostsForYearMonth(LIMIT, year, month, Collections.EMPTY_LIST);
        return posts.get(0);
    }

    public List<String> getIterations()
    {
        List<String> iterations = new ArrayList<String>();

        if (year != null && month != null)
        {
            iterations.add(createIteration(year, month));
        }
        else if (year != null)
        {
            boolean unpublished = viewState.isShowUnpublished();
            List<Integer> months = postDAO.getMonths(year, unpublished);

            for (Integer m : months)
            {
                iterations.add(createIteration(year, m));
            }
        }
        else
        {
            boolean unpublished = viewState.isShowUnpublished();
            List<Integer> years = postDAO.getAllYears(unpublished);

            for(Integer y : years)
            {
                iterations.add(createIteration(y, null));
            }
        }

        return iterations;
    }

    public boolean isPageIteration()
    {
        Integer y = parseYear(currentIteration);
        Integer m = parseMonth(currentIteration);

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
        Integer year = parseYear(currentIteration);
        Integer month = parseMonth(currentIteration);

        return title(year, month);
    }

    public boolean isSubiteration()
    {
        return year != null;
    }

    Object onSelectIteration(String iteration)
    {
        Integer year = parseYear(iteration);
        Integer month = parseMonth(iteration);

        if (year != null && month != null)
        {
            return linkSource.createPageRenderLinkWithContext(Archive.class, year, month);
        }
        if (year != null)
        {
            return linkSource.createPageRenderLinkWithContext(Archive.class, year);
        }
        return onSelectIteration();
    }

    Object onSelectIteration()
    {
        return linkSource.createPageRenderLinkWithContext(Archive.class);
    }

    public String getParentIteration()
    {
        if (month != null)
        {
            return createIteration(year, null);
        }
        return createIteration(null, null);
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



    private String createIteration(Integer year, Integer month)
    {
        if (year != null)
        {
            String iteration = Integer.toString(year);
            if (month != null)
            {
                iteration += ITERATION_DELIM + month;
            }
            return iteration;
        }
        return null;
    }

    private Integer parseYear(String iteration)
    {
        String[] parts = StringUtils.split(iteration, ITERATION_DELIM);

        if (parts.length > 0)
        {
            String year = parts[0];
            return Integer.parseInt(year);
        }
        return null;
    }

    private Integer parseMonth(String iteration)
    {
        String[] parts = StringUtils.split(iteration, ITERATION_DELIM);
        if (parts.length > 1)
        {
            String month = parts[1];
            return Integer.parseInt(month);
        }
        return null;
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
