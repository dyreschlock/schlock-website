package com.schlock.website.components.blog.content;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.ImageComment;
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
    private String currentImage;

    @Property
    @Persist
    private String selectedImage;


    public String getImageMessage()
    {
        String message = messages.get("images");
        int count = getGalleryImages().size();

        return String.format(message, count);
    }


    public List<String> getGalleryImages()
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
        return StringUtils.isNotBlank(selectedImage);
    }

    Object onSelectImage(String image)
    {
        this.selectedImage = image;

        return imageOverlayZone;
    }

    public String getImageLink()
    {
        String thumbless = selectedImage.replaceAll("_t.jpg", ".jpg");
        return thumbless;
    }


    public boolean isHasPreviousImage()
    {
        return StringUtils.isNotBlank(getPreviousImage());
    }

    public String getPreviousImage()
    {
        List<String> gallery = loadGalleryImages();

        int index = gallery.indexOf(selectedImage);
        if (index == 0)
        {
            return null;
        }
        return gallery.get(index - 1);
    }

    public boolean isHasNextImage()
    {
        return StringUtils.isNotBlank(getNextImage());
    }

    public String getNextImage()
    {
        List<String> gallery = loadGalleryImages();

        int index = gallery.indexOf(selectedImage);
        if (index == gallery.size() - 1)
        {
            return null;
        }
        return gallery.get(index + 1);
    }

    public boolean isHasImageComment()
    {
        ImageComment comment = getImageComment();
        return comment != null;
    }

    public String getImageCommentHtml()
    {
        ImageComment comment = getImageComment();
        if (comment != null)
        {
            String html = postManagement.wrapJapaneseTextInTags(comment.getCommentText());
            return html;
        }
        return "";
    }

    private ImageComment getImageComment()
    {
        Set<ImageComment> comments = post.getImageComments();
        for (ImageComment comment : comments)
        {
            String image = comment.getImageName();
            String sImage = selectedImage;

            if (StringUtils.endsWith(sImage, image))
            {
                return comment;
            }
        }
        return null;
    }


    private List<String> cachedGalleryImages;

    private List<String> loadGalleryImages()
    {
        if(cachedGalleryImages == null)
        {
            cachedGalleryImages = postManagement.getGalleryImages(post);
        }
        return cachedGalleryImages;
    }
}
