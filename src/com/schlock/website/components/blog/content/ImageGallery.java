package com.schlock.website.components.blog.content;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Image;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.blog.LayoutManagement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class ImageGallery
{
    @Parameter(required = true)
    @Property
    private AbstractPost post;


    @Inject
    private LayoutManagement layoutManagement;

    @Inject
    private ImageManagement imageManagement;

    @Inject
    private Messages messages;


    @Property
    private Integer currentIndex;

    @Property
    private Image currentImage;

    @Property
    @Persist
    private Image selectedImage;


    public boolean isPostHasGallery()
    {
        return post.isHasGallery() || post.isPhotoPage();
    }

    public boolean isShowCount()
    {
        return !post.isPhotoPage();
    }

    public String getImageMessage()
    {
        String message = messages.get("images");
        int count = getGalleryImages().size();

        return String.format(message, count);
    }


    public List<Image> getGalleryImages()
    {
        selectedImage = null;

        return loadGalleryImages();
    }

    public String getAltText()
    {
        if (post.isPhotoPage())
        {
            return imageManagement.getImagePostTitle(currentImage);
        }

        Image image = currentImage;
        if (image.getParent() != null)
        {
            image = image.getParent();
        }
        return image.getImageName();
    }


    private List<Image> cachedGalleryImages;

    private List<Image> loadGalleryImages()
    {
        if(cachedGalleryImages == null)
        {
            cachedGalleryImages = imageManagement.getGalleryImages(post);
        }
        return cachedGalleryImages;
    }
}
