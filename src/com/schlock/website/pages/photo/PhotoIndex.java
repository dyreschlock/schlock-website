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

    public List<Image> getImages()
    {
        return imageManagement.getGalleryImages(getPage());
    }

    public String getCurrentImageLink()
    {
        return currentImage.getImageLink();
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
