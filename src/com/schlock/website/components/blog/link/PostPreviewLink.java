package com.schlock.website.components.blog.link;

import com.schlock.website.entities.blog.Image;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.pages.Index;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.blog.PostManagement;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class PostPreviewLink
{
    @Parameter(required = true)
    @Property
    private Post post;

    @Parameter
    @Property
    private String cssClass;


    @Inject
    private PostManagement postManagement;

    @Inject
    private ImageManagement imageManagement;

    @Inject
    private DateFormatter dateFormat;


    public String getPostTitleHtml()
    {
        String title = post.getTitle();
        String html = postManagement.wrapJapaneseTextInTags(title);

        return html;
    }

    public boolean isHasImage()
    {
        return StringUtils.isNotBlank(getCurrentImage());
    }

    public String getCurrentImage()
    {
        Image image = imageManagement.getPostImage(post);
        if (image != null)
        {
            return image.getImageLink();
        }
        return null;
    }

    public String getCreatedDate()
    {
        return dateFormat.dateFormat(post.getCreated());
    }
}
