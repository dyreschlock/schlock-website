package com.schlock.website.components.blog.content;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Image;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.blog.LayoutManagement;
import com.schlock.website.services.blog.PostManagement;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class ImageGallery
{
    @Parameter(required = true)
    @Property
    private AbstractPost post;


    @Inject
    private LayoutManagement layoutManagement;

    @Inject
    private ImageManagement imageManagement;

    @Inject
    private PostManagement postManagement;

    @Inject
    private Messages messages;


    @Property
    private Integer currentIndex;

    @Property
    private Image currentImage;

    @Property
    @Persist
    private Image selectedImage;


    public String getImageMessage()
    {
        String message = messages.get("images");
        int count = getGalleryImages().size();

        return String.format(message, count);
    }


    public List<Image> getGalleryImages()
    {
        selectedImage = null;

        return loadGalleryImages();
    }

    public String getColumnClasses()
    {
        String cls = layoutManagement.getColumnClassByIndex(currentIndex);
        return cls;
    }


    public String getImageArrayJS()
    {
        String code = "";

        List<Image> images = getGalleryImages();
        for(int i = 0; i < images.size(); i++)
        {
            String index = Integer.toString(i);
            String url = images.get(i).getImageLink();

            String codeLine = String.format("images[%s] = \"%s\";\n", index, url);
            code += codeLine;
        }
        return code;
    }

    public String getImageCommentArrayJS()
    {
        String code = "";

        List<Image> images = getGalleryImages();
        for(int i = 0; i < images.size(); i++)
        {
            String index = Integer.toString(i);
            String comment = images.get(i).getCommentText();
            if (StringUtils.isBlank(comment))
            {
                comment = "";
            }

            String codeLine = String.format("comments[%s] = \"%s\";\n", index, comment);
            code += codeLine;
        }
        return code;
    }

    private List<Image> cachedGalleryImages;

    private List<Image> loadGalleryImages()
    {
        if(cachedGalleryImages == null)
        {
            cachedGalleryImages = imageManagement.getGalleryImages(post);
        }
        return cachedGalleryImages;
    }
}
