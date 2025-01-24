package com.schlock.website.components.old.v5;

import com.schlock.website.components.old.AbstractOldPostDisplay;
import com.schlock.website.entities.blog.Image;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.v5.V5PhotoPopup;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class Version5PhotoPageDisplay extends AbstractOldPostDisplay
{
    @Parameter(required = true)
    private Integer imageNumber;

    @Inject
    private PageRenderLinkSource linkSource;

    protected SiteVersion getVersion()
    {
        return SiteVersion.V5;
    }

    protected Class getIndexClass()
    {
        return V5PhotoPopup.class;
    }

    public String getPostTitle()
    {
        return super.getPostTitle().toLowerCase();
    }

    public String getPostDate()
    {
        return super.getPostDate().toLowerCase();
    }

    public String getImagePageLink(Image image)
    {
        int index = getPostImages().indexOf(image);
        return getPhotoLink(index);
    }

    public boolean isHasImage()
    {
        return imageNumber != null;
    }

    public String getImageLink()
    {
        int index = imageNumber;
        Image image = getPostImages().get(index);

        return image.getImageLink();
    }

    public boolean isHasNext()
    {
        if (isHasImage())
        {
            int size = getPostImages().size();
            return imageNumber < size-1;
        }
        return false;
    }

    public String getNextLink()
    {
        int index = imageNumber +1;
        return getPhotoLink(index);
    }

    public boolean isHasPrevious()
    {
        if (isHasImage())
        {
            return imageNumber > 0;
        }
        return false;
    }

    public String getPreviousLink()
    {
        int index = imageNumber -1;
        return getPhotoLink(index);
    }

    private String getPhotoLink(int index)
    {
        String uuid = getPost().getUuid();
        return linkSource.createPageRenderLinkWithContext(V5PhotoPopup.class, uuid, index).toURI();
    }
}
