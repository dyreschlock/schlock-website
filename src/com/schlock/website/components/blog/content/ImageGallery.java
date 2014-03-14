package com.schlock.website.components.blog.content;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.services.blog.PostManagement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class ImageGallery
{
    @Parameter(required = true)
    @Property
    private Post post;


    @Inject
    private PostManagement postManagement;


    @Property
    private Integer currentIndex;

    @Property
    private String currentImage;



    public List<String> getGalleryImages()
    {
        List<String> gallery = postManagement.getGalleryImages(post);
        return gallery;
    }

    public boolean isNewLine()
    {
        return currentIndex % 4 == 0;
    }
}
