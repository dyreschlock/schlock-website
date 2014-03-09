package com.schlock.website.pages;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.services.database.blog.PostDAO;
import com.schlock.website.services.database.blog.impl.PostDAOImpl;
import org.apache.tapestry5.annotations.ContentType;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

@ContentType(value="application/rss+xml")
public class Feed
{
    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private Messages messages;

    @Inject
    private PostDAO postDAO;


    @Property
    private Post currentPost;


    public List<Post> getRecentPosts()
    {
        int postCount = PostDAOImpl.TOP_RECENT;

        List<Post> posts = postDAO.getMostRecentPosts(postCount, false);
        return posts;
    }

    public String getCurrentPostURL()
    {
        String uuid = currentPost.getUuid();
        String redirect = linkSource.createPageRenderLinkWithContext(Index.class, uuid).toURI();

        String url = messages.get("website-url");

        return url + redirect.substring(1);
    }
}
