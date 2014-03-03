package com.schlock.website.entities.blog;

import com.schlock.website.services.database.blog.CategoryDAO;

public class ViewState
{
    private static final int VIEWABLE_POST_COUNTS = 20;

    private Long expandedCategoryId;
    private Long currentCategoryId;

    private boolean showUnpublished = false;

    private int viewableIteration = 1;

    private Integer archiveYear;
    private Integer archiveMonth;

    public void reset()
    {
        expandedCategoryId = null;
        currentCategoryId = null;
        showUnpublished = false;

        viewableIteration = 1;

        archiveYear = null;
        archiveMonth = null;
    }

    public void incrementViewableIteration()
    {
        viewableIteration++;
    }

    public int getViewingPostCount()
    {
        return viewableIteration * VIEWABLE_POST_COUNTS;
    }



    public Long getExpandedCategoryId()
    {
        return expandedCategoryId;
    }

    public void setExpandedCategoryId(Long expandedCategoryId)
    {
        this.expandedCategoryId = expandedCategoryId;
    }

    public boolean isHasCurrentCategory()
    {
        return currentCategoryId != null;
    }

    public Category getCurrentCategory(CategoryDAO categoryDAO)
    {
        if (isHasCurrentCategory())
        {
            return categoryDAO.getById(currentCategoryId);
        }
        return null;
    }

    public Long getCurrentCategoryId()
    {
        return currentCategoryId;
    }

    public void setCurrentCategoryId(Long currentCategoryId)
    {
        this.currentCategoryId = currentCategoryId;
    }

    public boolean isShowUnpublished()
    {
        return showUnpublished;
    }

    public void setShowUnpublished(boolean showUnpublished)
    {
        this.showUnpublished = showUnpublished;
    }

    public boolean isHasArchiveYear()
    {
        return archiveYear != null;
    }

    public Integer getArchiveYear()
    {
        return archiveYear;
    }

    public void setArchiveYear(Integer archiveYear)
    {
        this.archiveMonth = null;
        this.archiveYear = archiveYear;
    }

    public boolean isHasArchiveMonth()
    {
        return archiveMonth != null;
    }

    public Integer getArchiveMonth()
    {
        return archiveMonth;
    }

    public void setArchiveMonth(Integer archiveMonth)
    {
        this.archiveMonth = archiveMonth;
    }
}
