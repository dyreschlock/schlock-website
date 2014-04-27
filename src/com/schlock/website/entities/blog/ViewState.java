package com.schlock.website.entities.blog;

public class ViewState
{
    private static final int VIEWABLE_POST_COUNTS = 20;

    private boolean showUnpublished = false;

    private int viewableIteration = 1;

    public void reset()
    {
        showUnpublished = false;

        viewableIteration = 1;
    }

    public void incrementViewableIteration()
    {
        viewableIteration++;
    }

    public int getViewingPostCount()
    {
        return viewableIteration * VIEWABLE_POST_COUNTS;
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
