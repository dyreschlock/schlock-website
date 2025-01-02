package com.schlock.website.components.old.v2;

import com.schlock.website.pages.old.v2.V2Index;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class Version2MenuDisplay
{
    @Inject
    private PageRenderLinkSource linkSource;

    public String getHomeLink()
    {
        return linkSource.createPageRenderLink(V2Index.class).toURI();
    }

    public String getProjectsLink()
    {
        String param = V2Index.PROJECTS_PAGE;
        return linkSource.createPageRenderLinkWithContext(V2Index.class, param).toURI();
    }

    public String getGamesLink()
    {
        String param = V2Index.GAMES_PAGE;
        return linkSource.createPageRenderLinkWithContext(V2Index.class, param).toURI();
    }
}
