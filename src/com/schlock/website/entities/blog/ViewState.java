package com.schlock.website.entities.blog;

public class ViewState
{
    private Category currentCategory;

    private boolean showUnpublished = false;


    public boolean isHasCurrentCategory()
    {
        return currentCategory != null;
    }

    public Category getCurrentCategory()
    {
        return currentCategory;
    }

    public void setCurrentCategory(Category currentCategory)
    {
        this.currentCategory = currentCategory;
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
