package com.schlock.website.model.blog;

import com.schlock.website.model.Persisted;

import java.util.Date;
import java.util.Set;

public abstract class Post extends Persisted
{
    private String uuid;

    private String title;
    private Date created;

    private boolean published;
    private boolean pinned;
    private boolean page;

    private Set<Category> categories;


    public boolean isPhoto()
    {
        return this instanceof PhotoPost;
    }

    public boolean isText()
    {
        return this instanceof TextPost;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public boolean isPublished()
    {
        return published;
    }

    public void setPublished(boolean published)
    {
        this.published = published;
    }

    public boolean isPinned()
    {
        return pinned;
    }

    public void setPinned(boolean pinned)
    {
        this.pinned = pinned;
    }

    public boolean isPage()
    {
        return page;
    }

    public void setPage(boolean page)
    {
        this.page = page;
    }

    public Set<Category> getCategories()
    {
        return categories;
    }

    public void setCategories(Set<Category> categories)
    {
        this.categories = categories;
    }
}
