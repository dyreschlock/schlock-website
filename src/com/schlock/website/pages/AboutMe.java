package com.schlock.website.pages;

import com.schlock.website.entities.Icon;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class AboutMe
{
    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private Messages messages;

    @Inject
    private PostDAO postDAO;


    private Post cachedPost;


    public Post getAboutMePost()
    {
        if(cachedPost == null)
        {
            cachedPost = postDAO.getByUuid(Post.ABOUT_ME_UUID);
        }
        return cachedPost;
    }

    public String getPageTitle()
    {
        return getAboutMePost().getTitle();
    }

    public String getRssRedirect()
    {
        String url = linkSource.createPageRenderLink(Feed.class).toURI();
        return url;
    }

    public Icon getRss()
    {
        return Icon.RSS;
    }

    public Icon getTwitter()
    {
        return Icon.TWITTER;
    }

    public Icon getFacebook()
    {
        return Icon.FACEBOOK;
    }

    public Icon getYoutube()
    {
        return Icon.YOUTUBE;
    }

    public Icon getStack()
    {
        return Icon.STACK;
    }

    public Icon getGithub()
    {
        return Icon.GITHUB;
    }

    public Icon getPsn()
    {
        return Icon.PSN;
    }

    public Icon getXbox()
    {
        return Icon.XBOX;
    }

    public Icon getSteam()
    {
        return Icon.STEAM;
    }

    public Icon getEbay()
    {
        return Icon.EBAY;
    }
}
