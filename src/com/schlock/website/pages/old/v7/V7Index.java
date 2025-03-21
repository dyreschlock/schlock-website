package com.schlock.website.pages.old.v7;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.PostCategory;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;
import com.schlock.website.services.database.blog.CategoryDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

public class V7Index extends AbstractOldVersionPage
{
    @Inject
    private CategoryDAO categoryDAO;

    private PostCategory category;
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
        category = null;

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

        category = getCategory(param);
        if (category == null)
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
        category = null;
        pageNumber = Integer.parseInt(p2);

        if (isPagedPage(p1))
        {
            page = p1;
        }
        else
        {
            category = getCategory(p1);
            if (category == null)
            {
                page = p1;
            }
        }
        return true;
    }

    protected PostCategory getCategory(String param)
    {
        return (PostCategory) categoryDAO.getByUuid(PostCategory.class, param);
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

    public PostCategory getCategory()
    {
        return category;
    }

    public Integer getPageNumber()
    {
        return pageNumber;
    }
}
