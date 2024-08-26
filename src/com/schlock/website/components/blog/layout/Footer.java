package com.schlock.website.components.blog.layout;

import com.schlock.website.entities.Icon;
import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.ViewState;
import com.schlock.website.pages.AboutMe;
import com.schlock.website.pages.Feed;
import com.schlock.website.services.database.blog.PostDAO;
import com.schlock.website.services.database.blog.impl.PostDAOImpl;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

public class Footer
{
    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private PostDAO postDAO;


    @SessionState
    private ViewState viewState;


    @Property
    private AbstractPost currentPost;

    @Property
    private Integer currentIndex;



    public String getPostClass()
    {
        if (currentIndex >= PostDAOImpl.MIN_RECENT)
        {
            return "minSizeHidden";
        }
        return "";
    }

    public List<Post> getRecentPosts()
    {
        int postCount = PostDAOImpl.TOP_RECENT +2;
        boolean unpublished = viewState.isShowUnpublished();

        List<Post> posts = postDAO.getMostRecentPosts(postCount, unpublished, null, null, null);
        return posts;
    }

    public List<Post> getRecentPinnedPosts()
    {
        int postCount = PostDAOImpl.TOP_RECENT +2;
        boolean unpublished = viewState.isShowUnpublished();

        List<Post> posts = postDAO.getMostRecentPinnedPosts(postCount, unpublished, null, null, null);
        return posts;
    }

    public List<Page> getPages()
    {
        boolean unpublished = viewState.isShowUnpublished();

        List<Page> pages = postDAO.getAllPages(unpublished);
        return pages;
    }

    public String getRssUrl()
    {
        String url = linkSource.createPageRenderLink(Feed.class).toURI();
        return url;
    }

    public String getAboutMeUrl()
    {
        String url = linkSource.createPageRenderLink(AboutMe.class).toURI();
        return url;
    }

    public Icon getSeed()
    {
        return Icon.SEED;
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

    public Icon getGithub()
    {
        return Icon.GITHUB;
    }

    public Icon getEbay()
    {
        return Icon.EBAY;
    }

    public Icon getDiscord()
    {
        return Icon.DISCORD;
    }

    public Icon getTwitch()
    {
        return Icon.TWITCH;
    }
}
