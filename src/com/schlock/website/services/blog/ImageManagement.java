package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Image;

import java.util.List;

public interface ImageManagement
{
    public List<Image> getGalleryImages(AbstractPost post);

    public Image getPostImage(AbstractPost post);

    public void generateImages();

}
