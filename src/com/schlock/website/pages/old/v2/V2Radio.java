package com.schlock.website.pages.old.v2;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class V2Radio extends AbstractVersion2Page
{
    @Inject
    private PageRenderLinkSource linkSource;

    public String getPage()
    {
        return RADIO_PAGE;
    }

    public String getEventLink()
    {
        return linkSource.createPageRenderLinkWithContext(V2Projects.class, "dave-ralph-the-quest").toURI();
    }
}
