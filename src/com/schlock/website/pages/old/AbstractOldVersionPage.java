package com.schlock.website.pages.old;

import com.schlock.website.components.old.AbstractOldLinks;
import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Keyword;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.services.blog.CssCache;
import com.schlock.website.services.blog.PostArchiveManagement;
import com.schlock.website.services.database.blog.KeywordDAO;
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
    public static final String PROFILE_PAGE = "profile";

    public static final int POSTS_PER_PAGE = 10;

    @Inject
    private CssCache cssCache;

    @Inject
    private PostArchiveManagement archiveManagement;

    @Inject
    private KeywordDAO keywordDAO;

    @Inject
    private PostDAO postDAO;


    abstract public String getPage();
    abstract public AbstractPost getPost();
    abstract public SiteVersion getVersion();

    public Keyword getKeyword()
    {
        return null;
    }

    public Integer getPageNumber()
    {
        return null;
    }

    protected List<String> getKeywordNames()
    {
        List<String> names = new ArrayList<>();
        if (getKeyword() != null)
        {
            names.add(getKeyword().getName());
        }
        return names;
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

    public boolean isArchivePage()
    {
        return ARCHIVE_PAGE.equals(getPage());
    }

    public boolean isDefaultPost()
    {
        return getDefaultPost().equals(getPost());
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
        if (!getKeywordNames().isEmpty())
        {
            results = archiveManagement.getPagedPosts(getDefaultPostsPerPage(), pageNumber, new HashSet<>(getKeywordNames()));
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



    public Keyword getUpdatesCategory()
    {
        return keywordDAO.getByName(UPDATES_CAT_UUID);
    }

    public List<String> getReviewCategoryNames()
    {
        return Arrays.asList("game-reviews", "film-tv", "books", "anime", "toys");
    }

    protected List<String> getTravelPhotoCategoryNames()
    {
        return Arrays.asList("travel", "urban-exploration", "hida-takayama", "america");
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
            return postDAO.getMostRecentFrontPagePost();
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

    public String getProjectsPageName()
    {
        return PROJECTS_PAGE;
    }

    public String getReviewsPageName()
    {
        return REVIEWS_PAGE;
    }

    public String getPhotoPageName()
    {
        return PHOTO_PAGE;
    }
}
