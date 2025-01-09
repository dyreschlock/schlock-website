package com.schlock.website.pages.old.v6;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;
import org.apache.tapestry5.annotations.Property;

public class V6Index extends AbstractOldVersionPage
{
    private AbstractPost post;
    private String page;


    @Property
    private AbstractPost currentPost;

    @Property
    private Integer currentIndex;


    Object onActivate()
    {
        page = null;
        post = getDefaultPost();

        return true;
    }

    Object onActivate(String param)
    {
        page = null;
        post = null;

        if (isArchivePage(param))
        {
            page = param;
        }
        else
        {
            post = getPost(param);
        }
        return true;
    }

    public boolean isNotFirst()
    {
        return currentIndex != 0;
    }


    public String getPage()
    {
        return page;
    }

    public AbstractPost getPost()
    {
        return post;
    }

    public SiteVersion getVersion()
    {
        return SiteVersion.V6;
    }

    public Integer getPageNumber()
    {
        return 1;
    }

    public Integer getDefaultPostsPerPage()
    {
        return null;
    }
}
