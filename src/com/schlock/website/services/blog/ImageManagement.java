package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Image;

import java.io.IOException;
import java.util.List;

public interface ImageManagement
{
    List<Image> getGalleryImages(AbstractPost post);

    Image getPostImage(AbstractPost post);

    String getPostPreviewMetadataLink(String uuid);

    void generateImageObjects();

    String updateImagesInHTML(AbstractPost post, String html, boolean useGalleryLink);

    void createPostPreviewImages();

    void removeInvalidCharactersFromImageFilenames(String webDirPath) throws IOException;

    void createThumbnailsForDirectory(String webDirPath) throws IOException;

    void generateWebpFilesFromImages() throws IOException;
}
