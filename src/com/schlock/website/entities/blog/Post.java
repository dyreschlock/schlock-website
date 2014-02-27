package com.schlock.website.entities.blog;

import com.schlock.website.entities.Persisted;

import java.util.Date;
import java.util.Set;

public abstract class Post extends Persisted
{
    private String uuid;
    private String title;

    private Date created;
    private Date createdGMT;

    private boolean published;
    private boolean pinned;
    private boolean page;

    private Set<Category> categories;

    public Post(String uuid)
    {
        this.uuid = uuid;
    }

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

    public Date getCreatedGMT()
    {
        return createdGMT;
    }

    public void setCreatedGMT(Date createdGMT)
    {
        this.createdGMT = createdGMT;
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
