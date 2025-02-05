package com.schlock.website.components.blog.content;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Image;
import com.schlock.website.services.blog.CssCache;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.blog.PostManagement;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class ImageGalleryScript
{
    @Parameter(required = true)
    @Property
    private AbstractPost post;

    @Inject
    private ImageManagement imageManagement;

    @Inject
    private PostManagement postManagement;

    @Inject
    private CssCache cssCache;


    public boolean isPostHasGallery()
    {
        return post != null && (post.isHasGallery() || post.isPhotoPage());
    }

    public String getImageGalleryStyles()
    {
        return cssCache.getImageGalleryCss(post);
    }

    private List<Image> getGalleryImages()
    {
        return imageManagement.getGalleryImages(post);
    }

    public String getImageDataJS()
    {
        String code = "";

        List<Image> images = getGalleryImages();
        for(int i = 0; i < images.size(); i++)
        {
            String index = Integer.toString(i);

            Image image = images.get(i);
            Image parent = image;
            if (image.getParent() != null)
            {
                parent = image.getParent();
            }

            String imageUrl = image.getImageLink();
            String parentUrl = parent.getImageLink();

            String comment = image.getCommentText();
            if (StringUtils.isBlank(comment))
            {
                comment = parent.getCommentText();
            }
            if (StringUtils.isBlank(comment))
            {
                comment = "";
            }
            comment = postManagement.generateCommentHTML(comment);

            String imageCode = String.format("images[%s] = \"%s\";\n", index, parentUrl);
            String parentCode = String.format("linkImages[%s] = \"%s\";\n", index, parentUrl);
            String commentCode = String.format("comments[%s] = \"%s\";\n", index, comment);

            code += imageCode;
            code += parentCode;
            code += commentCode;
        }
        return code;
    }
}
