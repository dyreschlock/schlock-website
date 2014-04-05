package com.schlock.website.entities.blog;

import com.schlock.website.entities.Persisted;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

public class Post extends Persisted
{
    public static final String ABOUT_ME_UUID = "about-me";
    public static final String ALT_MATERIALS_UUID = "alt-materials";

    public static final String SIX_UUID_PREFIX = "6nen";
    public static final String FIVE_UUID_PREFIX = "5nen";
    public static final String THREE_FOUR_UUID_PREFIX = "34nen";
    public static final String ONE_TWO_UUID_PREFIX = "12nen";
    public static final String SPECIAL_UUID_PREFIX = "special";


    private static final String DATE_FORMAT = "EEEEE  MMMMM d, yyyy";
    private static final String HTML_TAG_FORMAT = "yyyy-MM-dd'T'hh:mm";
    private static final String DAY_FORMAT = "MMMMM d";


    private String wpid; //legacy Wordpress id
    private String mtid; //legacy MoveableType id

    private String uuid;
    private String title;

    private Date created;

    private boolean published;
    private boolean pinned;
    private boolean page;

    private boolean showGallery;
    private String galleryName;
    private String coverImage;

    private String bodyText;
    private String bodyHTML;

    private Set<Category> categories;

    private String lessonPlanLink;
    private String flashCardsLink;


    protected Post()
    {
    }

    public Post(String uuid)
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

    public String getCreatedDateFormat()
    {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(created);
    }

    public String getCreatedTagFormat()
    {
        SimpleDateFormat format = new SimpleDateFormat(HTML_TAG_FORMAT);
        return format.format(created);
    }

    public String getCreatedDayFormat()
    {
        SimpleDateFormat format = new SimpleDateFormat(DAY_FORMAT);
        return format.format(created);
    }

    public boolean isHasGallery()
    {
        return StringUtils.isNotBlank(galleryName) && showGallery;
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

    public String getLessonPlanLink()
    {
        return lessonPlanLink;
    }

    public void setLessonPlanLink(String lessonPlanLink)
    {
        this.lessonPlanLink = lessonPlanLink;
    }

    public String getFlashCardsLink()
    {
        return flashCardsLink;
    }

    public void setFlashCardsLink(String flashCardsLink)
    {
        this.flashCardsLink = flashCardsLink;
    }
}
