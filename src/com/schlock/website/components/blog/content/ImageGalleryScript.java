package com.schlock.website.components.blog.content;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Image;
import com.schlock.website.pages.Index;
import com.schlock.website.services.blog.CssCache;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

public class ImageGalleryScript
{
    private static final String POST_LINK_TEXT_KEY = "post-link-text";

    @Parameter(required = true)
    @Property
    private AbstractPost post;

    @Inject
    private ImageManagement imageManagement;

    @Inject
    private PostManagement postManagement;

    @Inject
    private PostDAO postDAO;

    @Inject
    private CssCache cssCache;

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private Messages messages;


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

            String comment = null;
            String postLink = "";
            if (post.isPhotoPage())
            {
                String postUuid = image.getPostUuid();
                if (StringUtils.isNotBlank(postUuid))
                {
                    List<AbstractPost> posts = postDAO.getAllByUuid(postUuid);
                    for(AbstractPost post : posts)
                    {
                        if (!post.isCoursePage() && post.isPublished())
                        {
                            comment = messages.format(POST_LINK_TEXT_KEY, post.getTitle());
                            postLink = linkSource.createPageRenderLinkWithContext(Index.class, postUuid).toURI();
                        }
                    }
                }
            }

            if(StringUtils.isBlank(comment))
            {
                comment = image.getCommentText();
                if (StringUtils.isBlank(comment))
                {
                    comment = parent.getCommentText();
                }
            }

            if (StringUtils.isBlank(comment))
            {
                comment = "";
            }
            comment = postManagement.generateCommentHTML(comment);

            String imageCode = String.format("images[%s] = \"%s\";\n", index, parentUrl);
            String parentCode = String.format("linkImages[%s] = \"%s\";\n", index, parentUrl);
            String commentCode = String.format("comments[%s] = \"%s\";\n", index, comment);
            String postCode = String.format("linkPosts[%s] = \"%s\";\n", index, postLink);

            code += imageCode;
            code += parentCode;
            code += commentCode;
            code += postCode;
        }
        return code;
    }


}
