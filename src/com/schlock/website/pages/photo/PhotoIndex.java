package com.schlock.website.pages.photo;

import com.schlock.website.entities.blog.Image;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class PhotoIndex
{
    @Inject
    private ImageManagement imageManagement;

    @Inject
    private PostDAO postDAO;


    @Property
    private Image currentImage;

    @Property
    private Integer currentIndex;

    public List<Image> getImages()
    {
        return imageManagement.getGalleryImages(getPage());
    }

    public String getCurrentImageLink()
    {
        return currentImage.getImageLink();
    }

    public String getPageHeight()
    {
        int top_start = 40;

        int line_height = 200;
        int count = getImages().size();
        double lines = (count / 4.5) -1;

        int bottom_resize = 60;

        int height = top_start + (line_height * (int) Math.floor(lines)) - bottom_resize;
        return Integer.toString(height);
    }


    private Page cachedPage;

    public Page getPage()
    {
        if (cachedPage == null)
        {
            cachedPage = (Page) postDAO.getByUuid(Page.PHOTO_UUID);
        }
        return cachedPage;
    }

    public String getPageDescription()
    {
        return getPage().getBlurb();
    }
}
