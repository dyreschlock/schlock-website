package com.schlock.website.entities.blog;

import com.schlock.website.entities.Persisted;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public abstract class AbstractPost extends Persisted
{
    public static final String SUBTITLES_UUID = "subtitles";
    public static final String CODEJAM_MAY2012_UUID = "codejam-may-2012";

    public static final String KENDO_UUID = "kendo-example-written-exam";

    public static final int LEVEL_NOT_VISIBLE = 0;
    public static final int LEVEL_UNPUBLISHED = 1;
    public static final int LEVEL_PUBLISHED = 2;
    public static final int LEVEL_PINNED = 3;


    private String wpid; //legacy Wordpress id
    private String mtid; //legacy MoveableType id

    private String uuid;
    private String title;

    private Date created;

    private int publishedLevel;

    private boolean showGallery;
    private String galleryName;
    private String coverImage;

    private String bodyText;
    private String bodyHTML;

    private String blurb;

    private String keywordString;

    private Set<AbstractCategory> categories;
    private List<Keyword> keywords;

    protected AbstractPost()
    {
    }

    public AbstractPost(String uuid)
    {
        this.uuid = uuid;
    }


    public boolean isPost()
    {
        return false;
    }

    public boolean isPage()
    {
        return false;
    }

    public boolean isClubPost()
    {
        return false;
    }

    public boolean isLessonPost()
    {
        return false;
    }

    public boolean isProject()
    {
        for (AbstractCategory category : getCategories())
        {
            if (category.isProject())
            {
                return true;
            }
        }
        return false;
    }


    public boolean isHasKeywords()
    {
        return StringUtils.isNotBlank(keywordString);
    }


    public List<PostCategory> getTopPostCategories()
    {
        List<PostCategory> top = new ArrayList<PostCategory>();

        for (AbstractCategory category : getCategories())
        {
            if (category.isTopCategory() && category.isPost())
            {
                top.add((PostCategory) category);
            }
        }

        Collections.sort(top, new CategoryComparator());
        return top;
    }

    public List<AbstractCategory> getSubcategories(AbstractCategory top)
    {
        List<AbstractCategory> sub = new ArrayList<AbstractCategory>();

        for (AbstractCategory category : getCategories())
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

    public List<PostCategory> getAllPostCategories()
    {
        List<PostCategory> cat = new ArrayList<PostCategory>();

        for (AbstractCategory category : getCategories())
        {
            if (category.isPost())
            {
                cat.add((PostCategory) category);
            }
        }

        Collections.sort(cat, new CategoryComparator());
        return cat;
    }

    public List<ProjectCategory> getProjectCategories()
    {
        List<ProjectCategory> cat = new ArrayList<ProjectCategory>();

        for (AbstractCategory category : getCategories())
        {
            if (category.isProject() && !category.isTopCategory())
            {
                cat.add((ProjectCategory) category);
            }
        }

        Collections.sort(cat, new CategoryComparator());
        return cat;
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

    public int getPublishedLevel()
    {
        return publishedLevel;
    }

    public void setPublishedLevel(int publishedLevel)
    {
        this.publishedLevel = publishedLevel;
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

    public String getBlurb()
    {
        return blurb;
    }

    public void setBlurb(String blurb)
    {
        this.blurb = blurb;
    }

    public String getKeywordString()
    {
        return keywordString;
    }

    public void setKeywordString(String keywordString)
    {
        this.keywordString = keywordString;
    }

    public List<Keyword> getKeywords()
    {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords)
    {
        this.keywords = keywords;
    }

    public Set<AbstractCategory> getCategories()
    {
        return categories;
    }

    public void setCategories(Set<AbstractCategory> categories)
    {
        this.categories = categories;
    }
}
