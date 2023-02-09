package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.Image;

public interface GoogleManagement
{
    void buildFolders() throws Exception;

    String getGoogleIdForImage(Image image);
}
