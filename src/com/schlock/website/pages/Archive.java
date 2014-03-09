package com.schlock.website.pages;

import com.schlock.website.entities.blog.Category;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.ViewState;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Archive
{
    private static final Integer CATEGORY_COLUMN_COUNT = 4;

    @Inject
    private Messages messages;

    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    private PostDAO postDAO;


    @SessionState
    private ViewState viewState;


    @InjectComponent
    private Zone archiveZone;


    @Property
    private Category currentCategory;

    @Property
    private Category currentSubcategory;

    @Property
    private Integer currentIndex;

    @Property
    private Integer currentYear;

    @Property
    private Integer currentMonth;

    @Property
    private Post currentPost;


    public String getPublishedText()
    {
        String text = messages.get("published");

        Long c = 0l;
        for (Object[] count : getPublishedCounts())
        {
            Boolean published = (Boolean) count[0];
            if(published)
            {
                c = (Long) count[1];
            }
        }

        String count = Long.toString(c);
        return text + " (" + count + ")";
    }

    public String getUnpublishedText()
    {
        String text = messages.get("unpublished");

        Long c = 0l;
        for (Object[] count : getPublishedCounts())
        {
            c += (Long) count[1];
        }

        String count = Long.toString(c);
        return text + " (" + count + ")";
    }

    Object onPublished()
    {
        viewState.reset();

        return archiveZone;
    }

    Object onUnpublished()
    {
        viewState.reset();
        viewState.setShowUnpublished(true);

        return archiveZone;
    }



    public List<Category> getTopCategories()
    {
        List<Category> categories = new ArrayList<Category>();
        for (Category c : getCategories())
        {
            if (c.isTopCategory())
            {
                categories.add(c);
            }
        }
        return categories;
    }

    public List<Category> getSubcategories()
    {
        List<Category> categories = new ArrayList<Category>();
        for (Category c : getCategories())
        {
            if (!c.isTopCategory() &&
                    c.getParent().getId().equals(currentCategory.getId()))
            {
                categories.add(c);
            }
        }
        return categories;
    }

    public boolean isCategoryHasPosts()
    {
        String count = getCategoryCount(currentCategory);
        return count != null;
    }

    public boolean isSubcategoryHasPosts()
    {
        String count = getCategoryCount(currentSubcategory);
        return count != null;
    }

    public String getCategoryTitle()
    {
        String name = currentCategory.getName();
        String count = getCategoryCount(currentCategory);

        if (StringUtils.isNotBlank(count))
        {
            return name + " (" + count + ")";
        }
        return name;
    }

    public String getSubcategoryTitle()
    {
        String name = currentSubcategory.getName();
        String count = getCategoryCount(currentSubcategory);

        if (StringUtils.isNotBlank(count))
        {
            return "." + name + " (" + count + ")";
        }
        return "." + name;
    }

    private String getCategoryCount(Category category)
    {
        for (Object[] counts : getCategoriesCounts())
        {
            Long cid = (Long) counts[0];
            if (category.getId().equals(cid))
            {
                Long count = (Long) counts[1];
                return Long.toString(count);
            }
        }
        return null;
    }

    public String getSelectedCategory()
    {
        Long selectedId = viewState.getCurrentCategoryId();
        if (selectedId != null &&
                selectedId.equals(currentCategory.getId()))
        {
            return "selected";
        }
        return "";
    }

    public String getSelectedSubcategory()
    {
        Long selectedId = viewState.getCurrentCategoryId();
        if(selectedId != null &&
                selectedId.equals(currentSubcategory.getId()))
        {
            return "selected";
        }
        return "";
    }

    Object onSelectCategory(Long categoryId)
    {
        viewState.reset();
        viewState.setCurrentCategoryId(categoryId);

        return archiveZone;
    }




    public List<Integer> getYears()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int oldestYear = getOldestYear();

        List<Integer> years = new ArrayList<Integer>();
        while (year >= oldestYear)
        {
            years.add(year);
            year--;
        }
        return years;
    }

    private int getOldestYear()
    {
        List<Object[]> yearMonth = getYearsMonthsCounts();
        Object[] oldest = yearMonth.get(yearMonth.size() - 1);

        return (Integer) oldest[0];
    }

    public List<Integer> getMonths()
    {
        return Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
    }

    public boolean isYearHasPosts()
    {
        String count = getYearCount(currentYear);
        return count != null;
    }

    public String getCurrentYearText()
    {
        String name = Integer.toString(currentYear);
        String count = getYearCount(currentYear);

        if (StringUtils.isNotBlank(count))
        {
            return name + " (" + count + ")";
        }
        return name;
    }

    private String getYearCount(Integer year)
    {
        Integer overallCount = 0;
        for (Object[] count : getYearsMonthsCounts())
        {
            Integer y = (Integer) count[0];
            if (year.equals(y))
            {
                Long c = (Long) count[2];
                overallCount += c.intValue();
            }
        }
        return Integer.toString(overallCount);
    }

    public boolean isMonthHasPosts()
    {
        String count = getYearMonthCount(currentYear, currentMonth);
        return count != null;
    }

    public String getCurrentMonthText()
    {
        String name = messages.get(currentMonth.toString());
        String count = getYearMonthCount(currentYear, currentMonth);

        if (StringUtils.isNotBlank(count))
        {
            return name + " (" + count + ")";
        }
        return name;
    }

    private String getYearMonthCount(Integer year, Integer month)
    {
        for (Object[] count : getYearsMonthsCounts())
        {
            Integer y = (Integer) count[0];
            Integer m = (Integer) count[1];

            if (year.equals(y) && month.equals(m))
            {
                Long c = (Long) count[2];
                return Long.toString(c);
            }
        }
        return null;
    }

    public String getSelectedYearMonth()
    {
        Integer year = viewState.getArchiveYear();
        Integer month = viewState.getArchiveMonth();

        if (year != null && year.equals(currentYear))
        {
            if (month != null && month.equals(currentMonth))
            {
                return "selected";
            }
            if (month == null && currentMonth == null)
            {
                return "selected";
            }
        }
        return "";
    }

    Object onSelectYearMonth(Integer year, Integer month)
    {
        viewState.reset();
        viewState.setArchiveYear(year);
        viewState.setArchiveMonth(month);

        return archiveZone;
    }



    public boolean isCreateNewLine()
    {
        int numberOfPosts = getPosts().size();

        boolean endofrow = (currentIndex + 1) % 2 == 0;
        boolean lastOne = (currentIndex + 1) == numberOfPosts;

        return endofrow || lastOne;
    }




    private List<Object[]> cachedPublishedCounts;

    private List<Object[]> getPublishedCounts()
    {
        if (cachedPublishedCounts == null)
        {
            cachedPublishedCounts = postDAO.getPublishedPostCounts();
        }
        return cachedPublishedCounts;
    }

    private List<Object[]> cachedYearsMonthsCounts;

    private List<Object[]> getYearsMonthsCounts()
    {
        if (cachedYearsMonthsCounts == null)
        {
            boolean unpublished = viewState.isShowUnpublished();

            cachedYearsMonthsCounts = postDAO.getYearsMonthPostCounts(unpublished);
        }
        return cachedYearsMonthsCounts;
    }


    private List<Object[]> cachedCategoriesCounts;

    private List<Object[]> getCategoriesCounts()
    {
        if(cachedCategoriesCounts == null)
        {
            boolean unpublished = viewState.isShowUnpublished();

            cachedCategoriesCounts = postDAO.getCategoryPostCounts(unpublished);
        }
        return cachedCategoriesCounts;
    }


    private List<Category> cachedCategories;

    private List<Category> getCategories()
    {
        if (cachedCategories == null)
        {
            cachedCategories = categoryDAO.getAllInOrder();
        }
        return cachedCategories;
    }


    private List<Post> cachedPosts;

    public List<Post> getPosts()
    {
        if (cachedPosts == null)
        {
            Integer postCount = viewState.getViewingPostCount();
            Integer year = viewState.getArchiveYear();
            Integer month = viewState.getArchiveMonth();
            Boolean unpublished = viewState.isShowUnpublished();
            Long categoryId = viewState.getCurrentCategoryId();

            cachedPosts = postDAO.getRecentPostsByYearMonth(postCount, year, month, unpublished, categoryId);
        }
        return cachedPosts;
    }
}
