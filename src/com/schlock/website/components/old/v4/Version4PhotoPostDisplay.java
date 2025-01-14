package com.schlock.website.components.old.v4;

import com.schlock.website.components.old.AbstractOldPostDisplay;
import com.schlock.website.entities.blog.Image;
import com.schlock.website.entities.old.SiteVersion;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

public class Version4PhotoPostDisplay extends AbstractOldPostDisplay
{
    @Parameter(required = true)
    @Property
    private String page;

    @Inject
    private PageRenderLinkSource linkSource;


    protected SiteVersion getVersion()
    {
        return SiteVersion.V4;
    }

    protected List<Image> getPostImages()
    {
        List<Image> images = super.getPostImages();
        if (images.size() < getColumnCount())
        {
            return images;
        }
        if (images.size() < getColumnCount() *2)
        {
            return images.subList(0, getColumnCount());
        }
        return images.subList(0, getColumnCount() *2);
    }

    public String getPostLink()
    {
        Class indexPage = getVersion().indexClass();
        String uuid = getPost().getUuid();

        return linkSource.createPageRenderLinkWithContext(indexPage, page, uuid).toURI();
    }
}
