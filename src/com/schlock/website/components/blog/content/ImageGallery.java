package com.schlock.website.components.blog.content;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.services.blog.PostManagement;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class ImageGallery
{
    @Parameter(required = true)
    @Property
    private Post post;


    @Inject
    private PostManagement postManagement;


    @InjectComponent
    private Zone imageOverlayZone;


    @Property
    private Integer currentIndex;

    @Property
    private String currentImage;

    @Property
    @Persist
    private String selectedImage;



    public List<String> getGalleryImages()
    {
        selectedImage = null;

        List<String> gallery = postManagement.getGalleryImages(post);
        return gallery;
    }

    public boolean isNewLine()
    {
        return currentIndex % 4 == 0;
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
}
