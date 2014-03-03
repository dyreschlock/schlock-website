package com.schlock.website.pages;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.ViewState;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Archive
{
    @Inject
    private Messages messages;

    @Inject
    private PostDAO postDAO;

    @InjectComponent
    private Zone archiveZone;


    @SessionState
    private ViewState viewState;


    @Property
    private Integer currentYear;

    @Property
    private Integer currentMonth;

    @Property
    private Post currentPost;



    private List<Object[]> cachedAllYearsMonths;

    public List<Object[]> getAllYearsMonths()
    {
        if(cachedAllYearsMonths == null)
        {
            boolean unpublished = viewState.isShowUnpublished();
            Long categoryId = viewState.getCurrentCategoryId();

            cachedAllYearsMonths = postDAO.getRecentYearsMonths(null, null, null, unpublished, categoryId);
        }
        return cachedAllYearsMonths;
    }

    public List<Integer> getPostYears()
    {
        List<Integer> years = getYearsFromList(null, getAllYearsMonths());
        return years;
    }

    Object onSelectYear(int year)
    {
        viewState.setArchiveYear(year);

        return archiveZone;
    }

    public boolean isYearSelected()
    {
        Integer year = viewState.getArchiveYear();
        return currentYear == year;
    }

    public List<Integer> getPostMonths()
    {
        Integer year = viewState.getArchiveYear();
        if(year == null)
        {
            return Collections.emptyList();
        }

        List<Integer> months = getMonthsFromList(year, null, getAllYearsMonths());
        return months;
    }

    Object onSelectMonth(int month)
    {
        viewState.setArchiveMonth(month);

        return archiveZone;
    }

    public boolean isMonthSelected()
    {
        Integer month = viewState.getArchiveMonth();
        return currentMonth == month;
    }

    public String getCurrentMonthString()
    {
        return messages.get(Integer.toString(currentMonth));
    }



    private List<Object[]> cachedRecentYearsMonths;

    public List<Object[]> getRecentYearsMonths()
    {
        if(cachedRecentYearsMonths == null)
        {
            boolean unpublished = viewState.isShowUnpublished();
            Long categoryId = viewState.getCurrentCategoryId();

            Integer year = viewState.getArchiveYear();
            Integer month = viewState.getArchiveMonth();

            int postCount = viewState.getViewingPostCount();

            cachedRecentYearsMonths = postDAO.getRecentYearsMonths(postCount, year, month, unpublished, categoryId);
        }
        return cachedRecentYearsMonths;
    }

    public List<Integer> getCurrentYears()
    {
        Integer selectedYear = viewState.getArchiveYear();
        List<Integer> years = getYearsFromList(selectedYear, getRecentYearsMonths());
        return years;
    }

    public List<Integer> getCurrentMonths()
    {
        Integer selectedMonth = viewState.getArchiveMonth();
        List<Integer> months = getMonthsFromList(currentYear, selectedMonth, getRecentYearsMonths());
        return months;
    }



    public List<Post> getArchivedPosts()
    {
        boolean unpublished = viewState.isShowUnpublished();
        Long categoryId = viewState.getCurrentCategoryId();

        List<Post> posts = postDAO.getRecentPostsByYearMonth(null, currentYear, currentMonth, unpublished, categoryId);
        return posts;
    }

    public boolean isHasPinnedPosts()
    {
        return !getArchivedPinnedPosts().isEmpty();
    }

    private List<Post> cachedArchivedPinnedPosts;

    public List<Post> getArchivedPinnedPosts()
    {
        if(cachedArchivedPinnedPosts == null)
        {
            boolean unpublished = viewState.isShowUnpublished();
            Long categoryId = viewState.getCurrentCategoryId();

            cachedArchivedPinnedPosts = postDAO.getRecentPinnedPostsByYearMonth(null, currentYear, currentMonth, unpublished, categoryId);
        }
        return cachedArchivedPinnedPosts;
    }


    public String getArchivePostsTitle()
    {
        String message = messages.get("archive-posts");
        String month = messages.get(Integer.toString(currentMonth));
        return String.format(message, month, currentYear);
    }

    public String getArchivePinnedPostsTitle()
    {
        String message = messages.get("archive-pinned-posts");
        String month = messages.get(Integer.toString(currentMonth));
        return String.format(message, month, currentYear);
    }



    public String getPageTitle()
    {
        return messages.get("page-title");
    }

    private static List<Integer> getYearsFromList(Integer matchingYear, List<Object[]> yearsMonthsList)
    {
        List<Integer> years = new ArrayList<Integer>();
        for (Object[] yearsMonths : yearsMonthsList)
        {
            Integer year = (Integer) yearsMonths[0];
            if (!years.contains(year) &&
                    (matchingYear == null || ((int) matchingYear) == ((int) year)))
            {
                years.add(year);
            }
        }
        return years;
    }

    private static List<Integer> getMonthsFromList(int selectedYear, Integer matchingMonth, List<Object[]> yearsMonthsList)
    {
        List<Integer> months = new ArrayList<Integer>();
        for (Object[] yearsMonths : yearsMonthsList)
        {
            Integer year = (Integer) yearsMonths[0];
            if (year == selectedYear)
            {
                Integer month = (Integer) yearsMonths[1];
                if (!months.contains(month) &&
                        (matchingMonth == null || ((int) matchingMonth) == ((int) month)))
                {
                    months.add(month);
                }
            }
        }
        return months;
    }
}
