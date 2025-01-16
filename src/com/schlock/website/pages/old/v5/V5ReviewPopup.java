package com.schlock.website.pages.old.v5;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.blog.PostManagement;
import org.apache.tapestry5.ioc.annotations.Inject;

public class V5ReviewPopup extends AbstractVersion5Page
{
    @Inject
    private PostManagement postManagement;

    @Inject
    private DateFormatter dateFormatter;

    private AbstractPost post;

    Object onActivate(String param)
    {
        post = getPost(param);

        return true;
    }

    public AbstractPost getPost()
    {
        return post;
    }

    public String getPostBodyHTML()
    {
        String html = postManagement.generatePostHTML(post, getVersion());
        return html;
    }

    public String getPostDate()
    {
        return dateFormatter.shortDateFormat(post.getCreated());
    }
}
