package com.schlock.website.pages.old.v5;

import com.schlock.website.entities.blog.AbstractCategory;
import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.blog.PostCategory;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.database.blog.CategoryDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class V5Index extends AbstractVersion5Page
{
    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    private DeploymentContext context;

    private AbstractPost post;
    private String page;
    private Integer pageNumber;
    private String subpage;


    Object onActivate()
    {
        page = null;
        post = getDefaultPost();
        pageNumber = 1;
        return true;
    }

    Object onActivate(String param)
    {
        post = null;
        page = null;
        pageNumber = 1;

        if (isSubPage(param))
        {
            page = param;
        }
        else
        {
            post = getPost(param);
        }

        if (Page.ABOUT_ME_UUID.equals(param))
        {
            page = SITE_MAP_PAGE;
        }
        return true;
    }

    Object onActivate(String p1, String p2)
    {
        post = null;
        page = p1;
        pageNumber = null;

        try
        {
            pageNumber = Integer.parseInt(p2);
        }
        catch(Exception e)
        {
        }

        if (pageNumber == null)
        {
            subpage = p2;
        }
        return true;
    }

    public List<Long> getCategoryIds()
    {
        if (isUpdatesPage())
        {
            return Arrays.asList(getUpdatesCategory().getId());
        }

        if (isReviewsPage())
        {
            List<Long> categoryIds = new ArrayList<>();
            for(String uuid : getReviewCategoryUuids())
            {
                AbstractCategory cat = categoryDAO.getByUuid(PostCategory.class, uuid);
                categoryIds.add(cat.getId());
            }
            return categoryIds;
        }

        if (isPhotoPage())
        {
            List<Long> categoryIds = new ArrayList<>();
            for(String uuid : getTravelPhotoCategoryUuids())
            {
                AbstractCategory cat = categoryDAO.getByUuid(PostCategory.class, uuid);
                categoryIds.add(cat.getId());
            }
            return categoryIds;
        }

        return super.getCategoryIds();
    }





    public String getImage1Link()
    {
        return layoutImageLink(1);
    }

    public String getImage2Link()
    {
        return layoutImageLink(2);
    }

    public String getImage3Link()
    {
        return layoutImageLink(3);
    }

    public String getImage4Link()
    {
        return layoutImageLink(4);
    }

    public String getImage5Link()
    {
        return layoutImageLink(5);
    }

    public String getImage6Link()
    {
        return layoutImageLink(6);
    }

    private String layoutImageLink(int number)
    {
        String link = "%simg/old/v5/%s_%s.jpg";

        String domain = context.webDomain();
        String page = getClassPage();

        return String.format(link, domain, page, number);
    }

    public boolean isUpdatesPage()
    {
        return ARCHIVE_PAGE.equals(getClassPage());
    }

    public AbstractPost getPost()
    {
        return post;
    }

    public String getClassPage()
    {
        if (StringUtils.isBlank(page))
        {
            return ARCHIVE_PAGE;
        }
        return page;
    }

    public String getPage()
    {
        return page;
    }

    public Integer getPageNumber()
    {
        return pageNumber;
    }

    public String getSubpage()
    {
        return subpage;
    }
}
