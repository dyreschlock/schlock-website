package com.schlock.website.pages.old.v4;

import com.schlock.website.entities.blog.AbstractCategory;
import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.PostCategory;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.database.blog.CategoryDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.*;

public class V4Index extends AbstractOldVersionPage
{
    @Inject
    private CategoryDAO categoryDAO;

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

    public boolean isPhotoPostPage()
    {
        return isPhotoOrClubPage() && getPost() != null;
    }

    public boolean isPhotoPage()
    {
        return PHOTO_PAGE.equals(page);
    }

    public boolean isClubPage()
    {
        return CLUB_PAGE.equals(page);
    }

    public boolean isPhotoOrClubPage()
    {
        return isPhotoPage() || isClubPage();
    }

    public boolean isReviewsPage()
    {
        return REVIEWS_PAGE.equals(page);
    }

    public boolean isStandardPage()
    {
        return !isPhotoPage() && !isClubPage() && !isReviewsPage();
    }

    public List<Long> getCategoryIds()
    {
        List<String> categoryUuids = new ArrayList<>();
        if (isPhotoPage())
        {
            categoryUuids = getTravelPhotoCategoryUuids();
        }
        else if (isClubPage())
        {
            categoryUuids = getClubPhotoCategoryUuids();
        }
        else if (isReviewsPage())
        {
            categoryUuids = getReviewCategoryUuids();
        }

        List<Long> categoryIds = new ArrayList<>();
        if (!categoryUuids.isEmpty())
        {
            for(String uuid : categoryUuids)
            {
                AbstractCategory cat = categoryDAO.getByUuid(PostCategory.class, uuid);
                categoryIds.add(cat.getId());
            }
        }
        else
        {
            categoryIds.add(getUpdatesCategory().getId());
        }
        return categoryIds;
    }



    public String getMainTopImage()
    {
        String link = context.webDomain();
        if (isPhotoPage())
        {
            link += "img/old/sunset_1.jpg";
        }
        else if (isClubPage())
        {
            link += "img/old/plush_1.jpg";
        }
        else if (isReviewsPage())
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
        if (isPhotoPage())
        {
            link += "img/old/sunset_2.jpg";
        }
        else if (isClubPage())
        {
            link += "img/old/plush_2.jpg";
        }
        else if (isReviewsPage())
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
