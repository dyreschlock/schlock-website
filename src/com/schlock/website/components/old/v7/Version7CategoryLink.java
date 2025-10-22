package com.schlock.website.components.old.v7;

import com.schlock.website.entities.blog.Keyword;
import com.schlock.website.entities.old.SiteVersion;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.services.PageRenderLinkSource;

import javax.inject.Inject;

public class Version7CategoryLink
{
    @Parameter(required = true)
    @Property
    private Keyword keyword;

    @Inject
    private PageRenderLinkSource linkSource;


    public String getKeywordLink()
    {
        Class indexClass = SiteVersion.V7.indexClass();
        String name = keyword.getName();

        return linkSource.createPageRenderLinkWithContext(indexClass, name).toURI();
    }
}
