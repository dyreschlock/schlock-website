package com.schlock.website.components.blog.content;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Image;
import com.schlock.website.services.blog.LayoutManagement;
import com.schlock.website.services.blog.PostManagement;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;
import java.util.Set;

public class ImageGallery
{
    @Parameter(required = true)
    @Property
    private AbstractPost post;


    @Inject
    private LayoutManagement layoutManagement;

    @Inject
    private PostManagement postManagement;

    @Inject
    private Messages messages;


    @InjectComponent
    private Zone imageOverlayZone;


    @Property
    private Integer currentIndex;

    @Property
    private Image currentImage;

    @Property
    @Persist
    private Image selectedImage;


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

    public String getColumnClasses()
    {
        String cls = layoutManagement.getColumnClassByIndex(currentIndex);
        return cls;
    }

    public boolean isHasImageSelected()
    {
        return selectedImage != null;
    }

    Object onSelectImage(String imageName)
    {
        this.selectedImage = null;
        for(Image image : loadGalleryImages())
        {
            if (StringUtils.equalsIgnoreCase(imageName, image.getImageName()))
            {
                this.selectedImage = image;
            }
        }

        return imageOverlayZone;
    }

    public boolean isHasPreviousImage()
    {
        return getPreviousImage() != null;
    }

    public Image getPreviousImage()
    {
        List<Image> gallery = loadGalleryImages();

        int index = gallery.indexOf(selectedImage);
        if (index == 0)
        {
            return null;
        }
        return gallery.get(index - 1);
    }

    public boolean isHasNextImage()
    {
        return getNextImage() != null;
    }

    public Image getNextImage()
    {
        List<Image> gallery = loadGalleryImages();

        int index = gallery.indexOf(selectedImage);
        if (index == gallery.size() - 1)
        {
            return null;
        }
        return gallery.get(index + 1);
    }

    public boolean isHasImageComment()
    {
        return selectedImage != null && selectedImage.getCommentText() != null;
    }

    public String getImageCommentHtml()
    {
        if(selectedImage != null && selectedImage.getCommentText() != null)
        {
            String comment = selectedImage.getCommentText();
            String html = postManagement.wrapJapaneseTextInTags(comment);
            return html;
        }
        return "";
    }


    private List<Image> cachedGalleryImages;

    private List<Image> loadGalleryImages()
    {
        if(cachedGalleryImages == null)
        {
            cachedGalleryImages = postManagement.getGalleryImages(post);
        }
        return cachedGalleryImages;
    }
}
