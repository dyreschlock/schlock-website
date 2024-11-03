package com.schlock.website.components.blog.link;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.blog.PostManagement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.Date;

public class PostLink
{
    private static final String POST_NUMBER_KEY = "post-number";

    @Parameter(required = true)
    @Property
    private AbstractPost post;

    @Parameter
    @Property
    private String cssClass;

    @Parameter(value = "true")
    private boolean displayDate;

    @Inject
    private DateFormatter dateFormat;

    @Inject
    private PostManagement postManagement;

    @Inject
    private Messages messages;


    public String getPostLink()
    {
        String href = "/";
        if (post.isCoursePage())
        {
            href += "courses/";
        }
        return href + post.getUuid();
    }

    public String getPostTitleHtml()
    {
        String title = post.getTitle();
        String html = postManagement.wrapJapaneseTextInTags(title);

        return html;
    }

    public boolean isShowNumber()
    {
        if (post.isPost())
        {
            Post p = (Post) post;
            return p.getNumber() != null;
        }
        return false;
    }

    public String getPostNumber()
    {
        if (post.isPost())
        {
            return messages.format(POST_NUMBER_KEY, ((Post) post).getDisplayNumber());
        }
        return null;
    }

    public String getPostYear()
    {
        return dateFormat.yearFormat(post.getCreated());
    }

    public boolean isDisplayDate()
    {
        return !post.isCoursePage() && !post.isPage();
    }

    public String getCreatedDate()
    {
        return dateFormat.dateFormat(post.getCreated());
    }


    public boolean isHasUpdatedTime()
    {
        return getUpdatedTime() != null;
    }

    public String getUpdatedTime()
    {
        String updatedTime = null;
        if (post.isPage())
        {
            Date date = postManagement.getUpdatedTime((Page) post);
            if(date != null)
            {
                updatedTime = dateFormat.dateFormat(date);
            }
        }
        return updatedTime;
    }
}
