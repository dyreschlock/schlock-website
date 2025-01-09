package com.schlock.website.pages.old;

import com.schlock.website.entities.blog.*;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.services.blog.CssCache;
import com.schlock.website.services.blog.PostArchiveManagement;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractOldVersionPage
{
    public static final String BASE_PAGE = "site-design-history";
    public static final String UPDATES_CAT_UUID = "updates";

    public static final String ARCHIVE_PAGE = "archive";
    public static final String BLOG_PAGE = "paged";

    public static final String ANIME_PAGE = "anime";
    public static final String GUEST_BOOK_PAGE = "guestbook";
    public static final String HOTLINE_PAGE = "hotline";
    public static final String MUSIC_PAGE = "music";
    public static final String PROJECTS_PAGE = "projects";
    public static final String RADIO_PAGE = "radio";
    public static final String RELEASES_PAGE = "releases";
    public static final String REVIEWS_PAGE = "reviews";
    public static final String SITE_MAP_PAGE = "sitemap";

    public static final int POSTS_PER_PAGE = 10;

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private CssCache cssCache;

    @Inject
    private PostArchiveManagement archiveManagement;

    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    private PostDAO postDAO;


    abstract public String getPage();
    abstract public AbstractPost getPost();
    abstract public SiteVersion getVersion();

    public PostCategory getCategory()
    {
        return null;
    }

    public Integer getPageNumber()
    {
        return null;
    }



    protected boolean isSpecialPage(String param)
    {
        return Arrays.asList(ARCHIVE_PAGE, PROJECTS_PAGE, REVIEWS_PAGE, MUSIC_PAGE).contains(param);
    }

    protected boolean isPagedPage(String param)
    {
        return BLOG_PAGE.equals(param) || ARCHIVE_PAGE.equals(param);
    }

    protected boolean isArchivePage(String param)
    {
        return archiveManagement.isIteration(param);
    }

    public String getProjectsPage()
    {
        return PROJECTS_PAGE;
    }

    public String getReviewsPage()
    {
        return REVIEWS_PAGE;
    }

    public boolean isHasPost()
    {
        return getPost() != null;
    }

    public boolean isHasPage()
    {
        return getPage() != null;
    }

    public String getPageCss()
    {
        return cssCache.getCssOldVersions(getPosts(), getVersion());
    }

    public List<AbstractPost> getPosts()
    {
        if (getPost() != null)
        {
            return Arrays.asList(getPost());
        }

        Integer pageNumber = getPageNumber();

        List<Post> results = new ArrayList<>();
        if (getCategory() != null)
        {
            Long catId = getCategory().getId();
            results = archiveManagement.getPagedPosts(POSTS_PER_PAGE, pageNumber, catId);
        }
        else if (isPagedPage(getPage()))
        {
            results = archiveManagement.getPagedPosts(POSTS_PER_PAGE, pageNumber);
        }
        else if (isArchivePage(getPage()))
        {
            String archiveIteration = getPage();
            results = archiveManagement.getPagedPosts(POSTS_PER_PAGE, pageNumber, archiveIteration);
        }

        List<AbstractPost> posts = new ArrayList<>();
        posts.addAll(results);
        return posts;
    }



    protected AbstractPost getDefaultPost()
    {
        return postDAO.getByUuid(BASE_PAGE);
    }

    protected PostCategory getUpdatesCategory()
    {
        return (PostCategory) categoryDAO.getByUuid(PostCategory.class, UPDATES_CAT_UUID);
    }

    protected AbstractPost getPost(String param)
    {
        if (StringUtils.isBlank(param))
        {
            return postDAO.getMostRecentFrontPagePost(null);
        }

        List<AbstractPost> posts = postDAO.getAllByUuid(param);
        for(AbstractPost post : posts)
        {
            if (!post.isCoursePage())
            {
                return post;
            }
        }
        return getPost(null);
    }

    protected PostCategory getCategory(String param)
    {
        return (PostCategory) categoryDAO.getByUuid(PostCategory.class, param);
    }


    public String getHomeLink()
    {
        return linkSource.createPageRenderLink(getVersion().indexClass()).toURI();
    }

    public String getAboutMeLink()
    {
        return linkSource.createPageRenderLinkWithContext(getVersion().indexClass(), Page.ABOUT_ME_UUID).toURI();
    }

    public String getReviewsLink()
    {
        return linkSource.createPageRenderLinkWithContext(getVersion().indexClass(), REVIEWS_PAGE).toURI();
    }

    public String getReleasesLink()
    {
        return linkSource.createPageRenderLinkWithContext(getVersion().indexClass(), RELEASES_PAGE).toURI();
    }

    public String getAnimeLink()
    {
        return linkSource.createPageRenderLinkWithContext(getVersion().indexClass(), ANIME_PAGE).toURI();
    }
}
