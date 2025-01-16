package com.schlock.website.pages.old;

import com.schlock.website.components.old.AbstractOldLinks;
import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.PostCategory;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.services.blog.CssCache;
import com.schlock.website.services.blog.PostArchiveManagement;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public abstract class AbstractOldVersionPage extends AbstractOldLinks
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
    public static final String PHOTO_PAGE = "photo";
    public static final String CLUB_PAGE = "club";

    public static final int POSTS_PER_PAGE = 10;

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

    protected List<Long> getCategoryIds()
    {
        List<Long> categoryIds = new ArrayList<>();
        if (getCategory() != null)
        {
            categoryIds.add(getCategory().getId());
        }
        return categoryIds;
    }


    public boolean isHasPost()
    {
        return getPost() != null;
    }

    public boolean isHasPage()
    {
        return getPage() != null;
    }

    public boolean isPhotoPage()
    {
        return PHOTO_PAGE.equals(getPage());
    }

    public boolean isClubPage()
    {
        return CLUB_PAGE.equals(getPage());
    }

    public boolean isReviewsPage()
    {
        return REVIEWS_PAGE.equals(getPage());
    }

    public boolean isSiteMapPage()
    {
        return SITE_MAP_PAGE.equals(getPage());
    }




    protected boolean isPagedPage(String param)
    {
        return BLOG_PAGE.equals(param) || ARCHIVE_PAGE.equals(param);
    }

    protected boolean isMonthlyArchivePage(String param)
    {
        return archiveManagement.isIteration(param);
    }

    protected boolean isSubPage(String param)
    {
        return Arrays.asList(ARCHIVE_PAGE, PHOTO_PAGE, CLUB_PAGE, REVIEWS_PAGE, SITE_MAP_PAGE).contains(param);
    }



    public Integer getDefaultPostsPerPage()
    {
        return POSTS_PER_PAGE;
    }

    public List<AbstractPost> getPosts()
    {
        if (getPost() != null)
        {
            return Arrays.asList(getPost());
        }

        Integer pageNumber = getPageNumber();

        List<Post> results = new ArrayList<>();
        if (!getCategoryIds().isEmpty())
        {
            results = archiveManagement.getPagedPosts(getDefaultPostsPerPage(), pageNumber, new HashSet<>(getCategoryIds()));
        }
        else if (isPagedPage(getPage()))
        {
            results = archiveManagement.getPagedPosts(getDefaultPostsPerPage(), pageNumber);
        }
        else if (isMonthlyArchivePage(getPage()))
        {
            String archiveIteration = getPage();
            results = archiveManagement.getPagedPosts(getDefaultPostsPerPage(), pageNumber, archiveIteration);
        }

        List<AbstractPost> posts = new ArrayList<>();
        posts.addAll(results);
        return posts;
    }



    public PostCategory getUpdatesCategory()
    {
        return (PostCategory) categoryDAO.getByUuid(PostCategory.class, UPDATES_CAT_UUID);
    }

    protected List<String> getReviewCategoryUuids()
    {
        return Arrays.asList("reviews", "film", "books", "anime", "toys");
    }

    protected List<String> getTravelPhotoCategoryUuids()
    {
        return Arrays.asList("travel", "takayama", "america");
    }

    protected List<String> getClubPhotoCategoryUuids()
    {
        return Arrays.asList("events");
    }


    protected AbstractPost getDefaultPost()
    {
        return postDAO.getByUuid(BASE_PAGE);
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

    public String getPageCss()
    {
        return cssCache.getCssOldVersions(getPosts(), getVersion());
    }

    public String getProjectsPage()
    {
        return PROJECTS_PAGE;
    }

    public String getReviewsPage()
    {
        return REVIEWS_PAGE;
    }
}
