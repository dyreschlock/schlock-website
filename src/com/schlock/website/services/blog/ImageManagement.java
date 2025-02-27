package com.schlock.website.services.blog;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Image;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface ImageManagement
{
    List<Image> getGalleryImages(AbstractPost post);

    String getImagePostTitle(Image image);

    Date getImagePostCreateDate(Image image);

    String getImagePostUrl(Image image);


    Image getPostPreviewImage(AbstractPost post);

    String getPostPreviewImageLink(AbstractPost post);

    String getPostPreviewMetadataLink(String uuid);

    void generateImageObjects();

    String updateImagesInHTML(AbstractPost post, String html, boolean useGalleryLink);

    void createPostPreviewImages();

    void removeInvalidCharactersFromImageFilenames(String webDirPath) throws IOException;

    void createThumbnailsForDirectory(String webDirPath) throws IOException;

    void generateWebpFilesFromImages() throws IOException;


    void convertAndCopyImage(File originalLocation, File outputLocation, int newImageWidth) throws IOException;
}
