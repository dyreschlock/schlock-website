package com.schlock.website.components.blog;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Image;
import com.schlock.website.services.blog.CssCache;
import com.schlock.website.services.blog.ImageManagement;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class LayoutBlog
{
    @Parameter
    @Property
    private AbstractPost post;

    @Parameter
    private String pageName;

    @Inject
    private ImageManagement imageManagement;

    @Inject
    private CssCache cssCache;

    @Inject
    private Messages messages;


    public String getTitle()
    {
        String title = getWebsiteTitle();
        String name = getPageTitle();

        return title + " // " + name;
    }

    public String getWebsiteTitle()
    {
        return messages.get("website-title");
    }

    public String getPageTitle()
    {
        String name = "";
        if (StringUtils.isNotBlank(pageName))
        {
            name = pageName;
        }
        else if (post != null)
        {
            name = post.getTitle();
        }
        return name;
    }

    public boolean isHasCoverImage()
    {
        return post != null && post.getCoverImage() != null;
    }

    public String getCoverImageUrl()
    {
        String imageUrl = "";
        if (post != null)
        {
            Image coverImage = imageManagement.getPostImage(post);
            if (coverImage != null)
            {
                if (StringUtils.isNotBlank(coverImage.getMetaThumbnailLink()))
                {
                    imageUrl = coverImage.getMetaThumbnailLink();
                }
                else
                {
                    imageUrl = coverImage.getImageLink();
                }
            }
        }
        return imageUrl;
    }

    public String getPrimaryCss()
    {
        return cssCache.getAllCss();
    }
}
