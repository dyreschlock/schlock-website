package com.schlock.website.components.blog.link;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.blog.PostManagement;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PostPreviewLink
{
    private static final String PRIMARY_POST_CSS = "primaryPost";
    private static final String POST_NUMBER_KEY = "post-number";

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

    @Inject
    private Messages messages;


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
        String link = imageManagement.getPostPreviewImageLink(post);
        if (link != null)
        {
            return link;
        }
        return null;
    }

    public boolean isShowNumber()
    {
        return StringUtils.equalsIgnoreCase(PRIMARY_POST_CSS, cssClass) && post.getNumber() != null;
    }

    public String getPostNumber()
    {
        return messages.format(POST_NUMBER_KEY, post.getDisplayNumber());
    }

    public String getCreatedDate()
    {
        return dateFormat.dateFormat(post.getCreated());
    }
}
