package com.schlock.website.entities.apps.notfibbage;

import com.schlock.website.entities.Persisted;

public class NotFibbageCategory extends Persisted
{
    private String title;

    public NotFibbageCategory()
    {
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}
