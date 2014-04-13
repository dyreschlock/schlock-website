package com.schlock.website.entities.blog;

import com.schlock.website.entities.Persisted;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public abstract class AbstractPost extends Persisted
{
    private String wpid; //legacy Wordpress id
    private String mtid; //legacy MoveableType id

    private String uuid;
    private String title;

    private Date created;

    private boolean published;
    private boolean pinned;

    private boolean showGallery;
    private String galleryName;
    private String coverImage;

    private String bodyText;
    private String bodyHTML;

    private Set<Category> categories;


    public abstract boolean isPage();


    protected AbstractPost()
    {
    }

    public AbstractPost(String uuid)
    {
        this.uuid = uuid;
    }


    public List<Category> getTopCategories()
    {
        List<Category> top = new ArrayList<Category>();

        for (Category category : getCategories())
        {
            if (category.isTopCategory())
            {
                top.add(category);
            }
        }

        Collections.sort(top, new CategoryComparator());
        return top;
    }

    public List<Category> getSubcategories(Category top)
    {
        List<Category> sub = new ArrayList<Category>();

        for (Category category : getCategories())
        {
            if (category.getParent() != null &&
                    category.getParent().getId().equals(top.getId()))
            {
                sub.add(category);
            }
        }

        Collections.sort(sub, new CategoryComparator());
        return sub;
    }

    public boolean isHasGallery()
    {
        return StringUtils.isNotBlank(galleryName) && showGallery;
    }

    public String getLessonPlanLink() { return null; }
    public String getFlashCardsLink() { return null; }



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

    public boolean isShowGallery()
    {
        return showGallery;
    }

    public void setShowGallery(boolean showGallery)
    {
        this.showGallery = showGallery;
    }

    public String getGalleryName()
    {
        return galleryName;
    }

    public void setGalleryName(String galleryName)
    {
        this.galleryName = galleryName;
    }

    public String getCoverImage()
    {
        return coverImage;
    }

    public void setCoverImage(String coverImage)
    {
        this.coverImage = coverImage;
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
