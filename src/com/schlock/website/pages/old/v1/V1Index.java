package com.schlock.website.pages.old.v1;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionIndex;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.blog.CssCache;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.Arrays;

public class V1Index extends AbstractOldVersionIndex
{
    private static final String ARCHIVE_PAGE = "archive";
    private static final String PROJECTS_PAGE = "projects";
    private static final String GAMES_PAGE = "review";
    private static final String MUSIC_PAGE = "music";

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private DeploymentContext context;

    @Inject
    private CssCache cssCache;

    @Inject
    private PostDAO postDAO;


    private String page;

    @Property
    private AbstractPost post;



    Object onActivate()
    {
        page = null;
        post = getPost(postDAO, null);
        return true;
    }

    Object onActivate(String param)
    {
        if (Arrays.asList(ARCHIVE_PAGE, PROJECTS_PAGE, GAMES_PAGE, MUSIC_PAGE).contains(param))
        {
            page = param;
            post = null;
        }
        else
        {
            page = null;
            post = getPost(postDAO, param);
        }

        return true;
    }


    public boolean isHasPost()
    {
        return post != null;
    }

    public boolean isArchivePage()
    {
        return ARCHIVE_PAGE.equals(page);
    }

    public boolean isCategoryPage()
    {
        return Arrays.asList(PROJECTS_PAGE, GAMES_PAGE, MUSIC_PAGE).contains(page);
    }

    public String getPageCss()
    {
        return cssCache.getCssOldVersions(post, SiteVersion.V1);
    }



    public String getArchiveLink()
    {
        return linkSource.createPageRenderLinkWithContext(V1Index.class, ARCHIVE_PAGE).toURI();
    }

    public String getAboutMeLink()
    {
        return linkSource.createPageRenderLinkWithContext(V1Index.class, Page.ABOUT_ME_UUID).toURI();
    }

    public String getProjectsLink()
    {
        return linkSource.createPageRenderLinkWithContext(V1Index.class, PROJECTS_PAGE).toURI();
    }

    public String getGamesLink()
    {
        return linkSource.createPageRenderLinkWithContext(V1Index.class, GAMES_PAGE).toURI();
    }

    public String getMusicLink()
    {
        return linkSource.createPageRenderLinkWithContext(V1Index.class, MUSIC_PAGE).toURI();
    }

    public String getImageLinkPopular()
    {
        String link = context.webDomain() + "img/old/popular.gif";
        return link;
    }

    public String getImageLinkComic()
    {
        String link = context.webDomain() + "img/old/pic4.jpg";
        return link;
    }
}
