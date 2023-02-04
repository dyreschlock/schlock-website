package com.schlock.website.pages;

import com.schlock.website.entities.Icon;
import com.schlock.website.entities.blog.Page;
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
        return Icon.RSS_DARK;
    }

    public Icon getTwitter()
    {
        return Icon.TWITTER_DARK;
    }

    public Icon getYoutube()
    {
        return Icon.YOUTUBE_DARK;
    }

    public Icon getStack()
    {
        return Icon.STACK_DARK;
    }

    public Icon getGithub()
    {
        return Icon.GITHUB_DARK;
    }

    public Icon getPsn()
    {
        return Icon.PSN_DARK;
    }

    public Icon getXbox()
    {
        return Icon.XBOX_DARK;
    }

    public Icon getSteam()
    {
        return Icon.STEAM_DARK;
    }

    public Icon getEbay()
    {
        return Icon.EBAY_DARK;
    }

    public Icon getDiscord() { return Icon.DISCORD_DARK; }

    public Icon getTwitch() { return Icon.TWITCH_DARK; }


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
