package com.schlock.website.pages.old.v4;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.blog.PostArchiveManagement;
import com.schlock.website.services.database.blog.KeywordDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class V4Index extends AbstractOldVersionPage
{
    @Inject
    private KeywordDAO keywordDAO;

    @Inject
    private DeploymentContext context;

    @Inject
    private PostArchiveManagement archiveManagement;


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

    public boolean isPhotoPostPage()
    {
        return isPhotoOrClubPage() && getPost() != null;
    }

    public boolean isPhotoOrClubPage()
    {
        return isPhotoPage() || isClubPage();
    }

    public boolean isStandardPage()
    {
        return !isPhotoPage() && !isClubPage() && !isReviewsPage();
    }

    public List<String> getKeywordNames()
    {
        List<String> categoryNames = new ArrayList<>();
        if (isPhotoPage())
        {
            categoryNames = getTravelPhotoCategoryNames();
        }
        else if (isClubPage())
        {
            categoryNames = getClubPhotoCategoryUuids();
        }
        else if (isReviewsPage())
        {
            categoryNames = getReviewCategoryNames();
        }

        if (categoryNames.isEmpty())
        {
            categoryNames = Arrays.asList(getUpdatesCategory().getName());
        }
        return categoryNames;
    }

    public List<AbstractPost> getPosts()
    {
        if (isClubPage() && getPost() == null)
        {
            List<Post> posts = archiveManagement.getPagedClubPosts(getDefaultPostsPerPage(), getPageNumber());

            List<AbstractPost> results = new ArrayList<>();
            results.addAll(posts);
            return results;
        }
        return super.getPosts();
    }

    public String getMainTopImage()
    {
        String link = "%simg/old/v4/%s_1.jpg";

        String domain = context.webDomain();
        String page = getClassPage();

        return String.format(link, domain, page);
    }

    public String getMainBottomImage()
    {
        String link = "%simg/old/v4/%s_2.jpg";

        String domain = context.webDomain();
        String page = getClassPage();

        return String.format(link, domain, page);
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
