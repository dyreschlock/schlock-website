package com.schlock.website.pages.old;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.services.blog.CssCache;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractOldVersionIndex
{
    public static final String ARCHIVE_PAGE = "archive";

    public static final String PROJECTS_PAGE = "projects";
    public static final String GAMES_PAGE = "reviews";
    public static final String MUSIC_PAGE = "music";

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private CssCache cssCache;

    @Inject
    private PostDAO postDAO;


    abstract public String getPage();
    abstract public AbstractPost getPost();
    abstract public SiteVersion getVersion();


    protected boolean isSpecialPage(String param)
    {
        return Arrays.asList(ARCHIVE_PAGE, PROJECTS_PAGE, GAMES_PAGE, MUSIC_PAGE).contains(param);
    }



    public boolean isHasPost()
    {
        return getPost() != null;
    }

    public boolean isArchivePage()
    {
        return ARCHIVE_PAGE.equals(getPage());
    }

    public boolean isCategoryPage()
    {
        return Arrays.asList(PROJECTS_PAGE, GAMES_PAGE, MUSIC_PAGE).contains(getPage());
    }

    public String getPageCss()
    {
        return cssCache.getCssOldVersions(getPost(), getVersion());
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
