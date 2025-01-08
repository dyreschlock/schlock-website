package com.schlock.website.pages.old.v2;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class V2Releases extends AbstractVersion2Page
{
    private static final String ONE = "1";
    private static final String TWO = "2";
    private static final String THREE = "3";

    @Inject
    private PageRenderLinkSource linkSource;


    private String pageNumber;

    Object onActivate()
    {
        return onActivate(ONE);
    }

    Object onActivate(String param)
    {
        pageNumber = param;
        return true;
    }

    public String getPage()
    {
        return RELEASES_PAGE;
    }

    public boolean isPage1()
    {
        return ONE.equals(pageNumber);
    }

    public boolean isPage2()
    {
        return TWO.equals(pageNumber);
    }

    public boolean isPage3()
    {
        return THREE.equals(pageNumber);
    }

    public String getPage1Link()
    {
        return linkSource.createPageRenderLink(V2Releases.class).toURI();
    }

    public String getPage2Link()
    {
        return linkSource.createPageRenderLinkWithContext(V2Releases.class, TWO).toURI();
    }

    public String getPage3Link()
    {
        return linkSource.createPageRenderLinkWithContext(V2Releases.class, THREE).toURI();
    }
}
