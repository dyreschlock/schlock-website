package com.schlock.website.components.old.v1n2;

import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.v1.V1Hotline;
import com.schlock.website.pages.old.v1.V1Radio;
import com.schlock.website.pages.old.v1.V1SiteMap;
import com.schlock.website.pages.old.v2.V2Index;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class Version1n2MenuBar
{
    @Inject
    private PageRenderLinkSource linkSource;

    @Parameter(required=true)
    private SiteVersion version;


    public boolean isV1()
    {
        return SiteVersion.V1.equals(version);
    }

    public boolean isV2()
    {
        return SiteVersion.V2.equals(version);
    }


    public String getHomeLink()
    {
        return linkSource.createPageRenderLink(version.indexClass()).toURI();
    }

    public String getArchiveLink()
    {
        String param = V2Index.ARCHIVE_PAGE;
        return linkSource.createPageRenderLinkWithContext(version.indexClass(), param).toURI();
    }

    public String getAboutMeLink()
    {
        String param = Page.ABOUT_ME_UUID;
        return linkSource.createPageRenderLinkWithContext(version.indexClass(), param).toURI();
    }

    public String getProjectsLink()
    {
        String param = V2Index.PROJECTS_PAGE;
        return linkSource.createPageRenderLinkWithContext(version.indexClass(), param).toURI();
    }

    public String getGamesLink()
    {
        String param = V2Index.GAMES_PAGE;
        return linkSource.createPageRenderLinkWithContext(version.indexClass(), param).toURI();
    }

    public String getSiteMapLink()
    {
        return linkSource.createPageRenderLink(V1SiteMap.class).toURI();
    }

    public String getRadioLink()
    {
        return linkSource.createPageRenderLink(V1Radio.class).toURI();
    }

    public String getHotlineLink()
    {
        return linkSource.createPageRenderLink(V1Hotline.class).toURI();
    }
}
