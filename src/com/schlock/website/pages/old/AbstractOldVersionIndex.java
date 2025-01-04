package com.schlock.website.pages.old;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.blog.PostCategory;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.services.blog.CssCache;
import com.schlock.website.services.blog.PostArchiveManagement;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractOldVersionIndex
{
    public static final String ARCHIVE_PAGE = "archive";

    public static final String PROJECTS_PAGE = "projects";
    public static final String GAMES_PAGE = "reviews";
    public static final String MUSIC_PAGE = "music";

    public static final String BLOG_PAGE = "paged";

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
        return Arrays.asList(ARCHIVE_PAGE, PROJECTS_PAGE, GAMES_PAGE, MUSIC_PAGE).contains(param);
    }

    protected boolean isBlogPage(String param)
    {
        return BLOG_PAGE.equals(param);
    }

    protected boolean isArchivePage(String param)
    {
        return archiveManagement.isIteration(param);
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
        return cssCache.getCssOldVersions(getPost(), getVersion());
    }

    public List<AbstractPost> getPosts()
    {
        Integer pageNumber = getPageNumber();
        if (getCategory() != null)
        {
            
        }
        if (isBlogPage(getPage()))
        {

        }
        if (isArchivePage(getPage()))
        {

        }
        if (getPost() != null)
        {
            return Arrays.asList(getPost());
        }
        return Collections.EMPTY_LIST;
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

    public String getArchiveLink()
    {
        return linkSource.createPageRenderLinkWithContext(getVersion().indexClass(), ARCHIVE_PAGE).toURI();
    }

    public String getAboutMeLink()
    {
        return linkSource.createPageRenderLinkWithContext(getVersion().indexClass(), Page.ABOUT_ME_UUID).toURI();
    }

    public String getProjectsLink()
    {
        return linkSource.createPageRenderLinkWithContext(getVersion().indexClass(), PROJECTS_PAGE).toURI();
    }

    public String getGamesLink()
    {
        return linkSource.createPageRenderLinkWithContext(getVersion().indexClass(), GAMES_PAGE).toURI();
    }

    public String getMusicLink()
    {
        return linkSource.createPageRenderLinkWithContext(getVersion().indexClass(), MUSIC_PAGE).toURI();
    }
}
