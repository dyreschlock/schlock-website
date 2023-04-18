package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.Image;

public interface GoogleManagement
{
    void generateGoogleImageIds();

    void updateImagesWithDirectLinks();

    String getGoogleIdForImage(Image image);

    String getDirectImageLinkForImage(Image image);
}
