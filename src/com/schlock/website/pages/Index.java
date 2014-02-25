package com.schlock.website.pages;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class Index
{
    @Inject
    private PageRenderLinkSource linkSource;

    Object onActivate()
    {


        return true;
    }

    Object onActivate(String parameter)
    {
        return true;
    }






    Object onCodejamLink()
    {
        return linkSource.createPageRenderLink(com.schlock.website.pages.codejam.may2012.Index.class);
    }
}
