package com.schlock.website.components.old.v2;

import com.schlock.website.components.old.AbstractOldPostDisplay;
import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.blog.PostManagement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Version2PostDisplay extends AbstractOldPostDisplay
{
    @Parameter(required = true)
    @Property
    private AbstractPost post;

    @Inject
    private PostManagement postManagement;

    @Inject
    private ImageManagement imageManagement;

    @Inject
    private DateFormatter dateFormatter;


    public String getPostTitle()
    {
        return post.getTitle();
    }

    public String getPostBodyHTML()
    {
        String html = postManagement.generatePostHTML(post, SiteVersion.V2);
        return html;
    }

    public boolean isHasDate()
    {
        return post != null && post.isPost() && post.getCreated() != null;
    }

    public String getPostDate()
    {
        return dateFormatter.shortDateFormat(post.getCreated());
    }

    public String getImagesTableHTML()
    {
        return getImagesTableHTML(imageManagement, post);
    }
}
