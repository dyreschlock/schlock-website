package com.schlock.website.pages.old.v4;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;
import com.schlock.website.services.DeploymentContext;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.Arrays;

public class V4Index extends AbstractOldVersionPage
{
    @Inject
    private DeploymentContext context;


    private AbstractPost post;
    private String page;
    private Integer pageNumber;


    @Property
    private AbstractPost currentPost;


    Object onActivate()
    {
        page = null;
        post = getDefaultPost();
        pageNumber = 1;
        return true;
    }

    Object onActivate(String param)
    {
        //aboutme
        //archive
        //photo
        //club
        //reviews

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
        //archive/2+
        //photo/2+
        //photo/[post uuid]
        //club/2+
        //club/[post uuid]
        //reviews/2+
        //reviews/[post uuid]

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

    private boolean isSubPage(String param)
    {
        return Arrays.asList(ARCHIVE_PAGE, PHOTO_PAGE, CLUB_PAGE, REVIEWS_PAGE).contains(param);
    }



    public String getMainTopImage()
    {
        String link = context.webDomain();
        if (PHOTO_PAGE.equals(page))
        {
            link += "img/old/sunset_1.jpg";
        }
        else if (CLUB_PAGE.equals(page))
        {
            link += "img/old/plush_1.jpg";
        }
        else if (REVIEWS_PAGE.equals(page))
        {
            link += "img/old/discus_1.jpg";
        }
        else
        {
            link += "img/old/trail_1.jpg";
        }
        return link;
    }

    public String getMainBottomImage()
    {
        String link = context.webDomain();
        if (PHOTO_PAGE.equals(page))
        {
            link += "img/old/sunset_2.jpg";
        }
        else if (CLUB_PAGE.equals(page))
        {
            link += "img/old/plush_2.jpg";
        }
        else if (REVIEWS_PAGE.equals(page))
        {
            link += "img/old/discus_2.jpg";
        }
        else
        {
            link += "img/old/trail_2.jpg";
        }
        return link;
    }

    public SiteVersion getVersion()
    {
        return SiteVersion.V4;
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
