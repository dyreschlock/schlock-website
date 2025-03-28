package com.schlock.website.pages;

import com.schlock.website.entities.Icon;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.Date;

public class AboutMe
{
    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private DateFormatter dateFormatter;

    @Inject
    private Messages messages;

    @Inject
    private PostDAO postDAO;



    public String getLastUpdated()
    {
        return dateFormatter.shortDateFormat(new Date());
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

    public Icon getDiscord() { return Icon.DISCORD; }

    public Icon getTwitch() { return Icon.TWITCH; }

    public Icon getBluesky()
    {
        return Icon.BLUESKY;
    }


    private Page cachedPage;

    public Page getPage()
    {
        if(cachedPage == null)
        {
            cachedPage = (Page) postDAO.getByUuid(Page.ABOUT_ME_UUID);
        }
        return cachedPage;
    }
}
