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





    public String getPageTitle()
    {
        return getPage().getTitle();
    }

    private Post cachedPage;

    public Post getPage()
    {
        if(cachedPage == null)
        {
            cachedPage = postDAO.getByUuid(Post.ABOUT_ME_UUID);
        }
        return cachedPage;
    }
}
