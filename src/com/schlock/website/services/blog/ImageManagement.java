package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Image;

import java.util.List;

public interface ImageManagement
{
    List<Image> getGalleryImages(AbstractPost post);

    Image getPostImage(AbstractPost post);

    String getPostPreviewMetadataLink(AbstractPost post);

    void generateImageObjects();

    String updateImagesInHTML(String html);
}
