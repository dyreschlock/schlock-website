package com.schlock.website.components.old.v7;

import com.schlock.website.entities.blog.PostCategory;
import com.schlock.website.entities.old.SiteVersion;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.services.PageRenderLinkSource;

import javax.inject.Inject;

public class Version7CategoryLink
{
    @Parameter(required = true)
    private PostCategory category;

    @Inject
    private PageRenderLinkSource linkSource;


    public String getCategoryTitle()
    {
        return category.getName();
    }

    public String getCategoryLink()
    {
        Class indexClass = SiteVersion.V7.indexClass();
        String uuid = category.getUuid();

        return linkSource.createPageRenderLinkWithContext(indexClass, uuid).toURI();
    }
}
