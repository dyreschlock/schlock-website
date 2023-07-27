package com.schlock.website.components.apps;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class LayoutApps
{
    @Inject
    private Messages messages;

    @Inject
    private PostManagement postManagement;

    @Inject
    private ImageManagement imageManagement;

    @Inject
    private PostDAO postDAO;

    @Parameter(required = true)
    @Property
    private String title;

    @Parameter(required = true)
    private String postUuid;

    @Parameter
    @Property
    private String css;

    public String getWebsiteTitle()
    {
        return messages.get("website-title");
    }

    private AbstractPost getGamesPost()
    {
        return postDAO.getByUuid(postUuid);
    }

    public String getPostDescription()
    {
        AbstractPost post = getGamesPost();

        String description = postManagement.generatePostDescription(post);
        return description;
    }

    public String getCoverImageUrl()
    {
        AbstractPost post = getGamesPost();

        String imageUrl = imageManagement.getPostPreviewMetadataLink(post);
        return imageUrl;
    }
}
