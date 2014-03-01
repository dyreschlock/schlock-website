package com.schlock.website.entities.blog;

import com.schlock.website.services.database.blog.CategoryDAO;

public class ViewState
{
    private Long expandedCategoryId;
    private Long currentCategoryId;

    private boolean showUnpublished = false;


    public void reset()
    {
        expandedCategoryId = null;
        currentCategoryId = null;
        showUnpublished = false;
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
}
