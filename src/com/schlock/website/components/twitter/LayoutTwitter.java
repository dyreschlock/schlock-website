package com.schlock.website.components.twitter;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.pages.Feed;
import com.schlock.website.services.blog.CssCache;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class LayoutTwitter
{
    private static final String POST_UUID = "twitter-archive";

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private CssCache cssCache;

    @Inject
    private Messages messages;

    @Inject
    private ImageManagement imageManagement;

    @Inject
    private PostDAO postDAO;



    public String getWebsiteTitle()
    {
        return messages.get("website-title");
    }

    public String getTitle()
    {
        AbstractPost post = postDAO.getByUuid(POST_UUID);
        return post.getTitle();
    }

    public String getDescription()
    {
        AbstractPost post = postDAO.getByUuid(POST_UUID);
        return post.getBlurb();
    }

    public String getCoverImageUrl()
    {
        AbstractPost post = postDAO.getByUuid(POST_UUID);
        return imageManagement.getPostPreviewMetadataLink(post);
    }

    public String getPageUrl()
    {
        return "";
    }

    public String getRssUrl()
    {
        String url = linkSource.createPageRenderLink(Feed.class).toURI();
        return url;
    }

    public String getCss()
    {
        return cssCache.getCssForTwitter();
    }
}
