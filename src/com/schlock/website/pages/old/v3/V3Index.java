package com.schlock.website.pages.old.v3;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;
import com.schlock.website.services.DeploymentContext;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.List;

public class V3Index extends AbstractOldVersionPage
{
    @Inject
    private DeploymentContext context;

    @Inject
    private Messages messages;


    private AbstractPost post;
    private String page;
    private Integer pageNumber;

    @Property
    private AbstractPost currentPost;

    @Property
    private Integer currentIndex;

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

    Object onActivate(String p1, String p2)
    {

        page = p1;
        post = null;
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
            post = getPost(p2);
        }
        return true;
    }


    public List<Long> getCategoryIds()
    {
        List<Long> categoryIds = new ArrayList<>();
        if (isArchivePage() || isDefaultPost())
        {
            categoryIds.add(getUpdatesCategory().getId());
        }
        return categoryIds;
    }

    public boolean isShowPrevious()
    {
        int size = getPosts().size();

        return currentIndex == size-1;
    }

    public String getPageTitle()
    {
        return messages.get(getClassPage());
    }

    public String getMainTopImage()
    {
        String image = "%s.jpg";
        return getImageLink(image);
    }

    public String getGrid1Image()
    {
        String image = "%s_grid1.jpg";
        return getImageLink(image);
    }

    public String getGrid2Image()
    {
        String image = "%s_grid2.jpg";
        return getImageLink(image);
    }

    public String getGrid3Image()
    {
        String image = "%s_grid3.jpg";
        return getImageLink(image);
    }

    public String getGrid4Image()
    {
        String image = "%s_grid4.jpg";
        return getImageLink(image);
    }

    private String getImageLink(String urlFormat)
    {
        String link = "%simg/old/v3/%s";
        String domain = context.webDomain();
        String image = String.format(urlFormat, getClassPage());

        return String.format(link, domain, image);
    }

    public SiteVersion getVersion()
    {
        return SiteVersion.V3;
    }

    public AbstractPost getPost()
    {
        return post;
    }

    public String getClassPage()
    {
        if (getPost() != null && Page.ABOUT_ME_UUID.equals(getPost().getUuid()))
        {
            return PROFILE_PAGE;
        }
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
