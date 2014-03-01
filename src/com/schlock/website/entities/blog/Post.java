package com.schlock.website.entities.blog;

import com.schlock.website.entities.Persisted;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class Post extends Persisted
{
    private static final String DATE_FORMAT = "EEEEE  MMMMM d, yyyy  hh:mm aaa";

    private String wpid; //legacy Wordpress id
    private String mtid; //legacy MoveableType id

    private String uuid;
    private String title;

    private Date created;
    private Date createdGMT;

    private boolean published;
    private boolean pinned;
    private boolean page;

    private String bodyText;
    private String bodyHTML;

    private Set<Category> categories;


    protected Post()
    {
    }

    public Post(String uuid)
    {
        this.uuid = uuid;
    }


    public String getPrettyCreatedDate()
    {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(created);
    }


    public String getWpid()
    {
        return wpid;
    }

    public void setWpid(String wpid)
    {
        this.wpid = wpid;
    }

    public String getMtid()
    {
        return mtid;
    }

    public void setMtid(String mtid)
    {
        this.mtid = mtid;
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

    public String getBodyText()
    {
        return bodyText;
    }

    public void setBodyText(String bodyText)
    {
        this.bodyText = bodyText;
    }

    public String getBodyHTML()
    {
        return bodyHTML;
    }

    public void setBodyHTML(String bodyHTML)
    {
        this.bodyHTML = bodyHTML;
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
