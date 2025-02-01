package com.schlock.website.components.blog.layout;

import com.schlock.website.entities.Icon;
import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.ViewState;
import com.schlock.website.pages.AboutMe;
import com.schlock.website.pages.Feed;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.SiteGenerationCache;
import com.schlock.website.services.database.blog.PostDAO;
import com.schlock.website.services.database.blog.impl.PostDAOImpl;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Footer
{
    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private SiteGenerationCache siteCache;

    @Inject
    private DateFormatter dateFormat;

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

    public List<String[]> getRecentPosts()
    {
        List<String[]> postDetails = (List<String[]>) siteCache.getCachedObject(SiteGenerationCache.FOOTER_POST_DETAILS);
        if (postDetails == null)
        {
            int postCount = PostDAOImpl.TOP_RECENT +2;
            boolean unpublished = viewState.isShowUnpublished();

            List<Post> posts = postDAO.getMostRecentPosts(postCount, unpublished, null, null, Collections.EMPTY_SET);
            postDetails = createPostDetails(posts);

            siteCache.addToObjectCache(postDetails, SiteGenerationCache.FOOTER_POST_DETAILS);
        }
        return postDetails;
    }

    public List<String[]> getRecentPinnedPosts()
    {
        List<String[]> postDetails = (List<String[]>) siteCache.getCachedObject(SiteGenerationCache.FOOTER_PINNED_DETAILS);
        if (postDetails == null)
        {
            int postCount = PostDAOImpl.TOP_RECENT +2;
            boolean unpublished = viewState.isShowUnpublished();

            List<Post> posts = postDAO.getMostRecentPinnedPosts(postCount, unpublished, null, null, null);
            postDetails = createPostDetails(posts);

            siteCache.addToObjectCache(postDetails, SiteGenerationCache.FOOTER_PINNED_DETAILS);
        }
        return postDetails;
    }

    public List<String[]> getPages()
    {
        List<String[]> postDetails = (List<String[]>) siteCache.getCachedObject(SiteGenerationCache.FOOTER_PAGE_DETAILS);
        if (postDetails == null)
        {
            boolean unpublished = viewState.isShowUnpublished();

            List<Page> pages = postDAO.getAllPages(unpublished);
            postDetails = createPostDetails(pages);

            siteCache.addToObjectCache(postDetails, SiteGenerationCache.FOOTER_PAGE_DETAILS);
        }
        return postDetails;
    }

    private List<String[]> createPostDetails(List<? extends AbstractPost> posts)
    {
        List<String[]> postDetails = new ArrayList<>();
        for(AbstractPost post : posts)
        {
            String[] details = new String[3];

            details[0] = post.getTitle();
            if (post.isPost())
            {
                details[1] = dateFormat.dateFormat(post.getCreated());
            }
            details[2] = post.getUuid();

            postDetails.add(details);
        }
        return postDetails;
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

    public Icon getBlueSky()
    {
        return Icon.BLUESKY;
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
