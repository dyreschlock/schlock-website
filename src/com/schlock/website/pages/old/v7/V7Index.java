package com.schlock.website.pages.old.v7;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Keyword;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;
import com.schlock.website.services.database.blog.KeywordDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

public class V7Index extends AbstractOldVersionPage
{
    @Inject
    private KeywordDAO keywordDAO;

    private Keyword keyword;
    private AbstractPost post;
    private String page;
    private Integer pageNumber;

    @Property
    private AbstractPost currentPost;


    Object onActivate()
    {
        // paged - page 1

        page = null;
        pageNumber = 1;

        post = getDefaultPost();
        keyword = null;

        return true;
    }

    Object onActivate(String param)
    {
        // category - page 1
        // archive - page 1
        // post

        page = null;
        post = null;
        pageNumber = 1;

        keyword = getKeyword(param);
        if (keyword == null)
        {
            if (isMonthlyArchivePage(param) || isPagedPage(param))
            {
                page = param;
            }
            else
            {
                post = getPost(param);
            }
        }
        return true;
    }

    Object onActivate(String p1, String p2)
    {
        // paged - page 2+
        // category - page 2+
        // archive - page 2+

        post = null;

        page = null;
        keyword = null;
        pageNumber = Integer.parseInt(p2);

        if (isPagedPage(p1))
        {
            page = p1;
        }
        else
        {
            keyword = getKeyword(p1);
            if (keyword == null)
            {
                page = p1;
            }
        }
        return true;
    }

    protected Keyword getKeyword(String param)
    {
        return keywordDAO.getByName(param);
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
        return SiteVersion.V7;
    }

    public Keyword getKeyword()
    {
        return keyword;
    }

    public Integer getPageNumber()
    {
        return pageNumber;
    }
}
