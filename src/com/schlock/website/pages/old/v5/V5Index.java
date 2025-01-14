package com.schlock.website.pages.old.v5;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;
import com.schlock.website.services.DeploymentContext;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.ioc.annotations.Inject;

public class V5Index extends AbstractOldVersionPage
{
    @Inject
    private DeploymentContext context;

    private AbstractPost post;
    private String page;
    private Integer pageNumber;


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
        return true;
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


    public SiteVersion getVersion()
    {
        return SiteVersion.V5;
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
}
