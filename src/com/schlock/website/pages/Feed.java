package com.schlock.website.pages;

import com.schlock.website.entities.blog.AbstractCategory;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.ContentType;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.Date;
import java.util.List;
import java.util.Set;

@ContentType(value="application/rss+xml")
public class Feed
{
    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private PostManagement postManagement;

    @Inject
    private DateFormatter dateFormatter;

    @Inject
    private Messages messages;

    @Inject
    private PostDAO postDAO;


    @Property
    private Post currentPost;

    @Property
    private AbstractCategory currentCategory;


    public List<Post> getRecentPosts()
    {
        int postCount = 20;

        List<Post> posts = postDAO.getMostRecentPosts(postCount, false, null, null, null);
        return posts;
    }

    public Set<AbstractCategory> getCategories()
    {
        return currentPost.getCategories();
    }

    public String getCurrentDate()
    {
        return dateFormatter.rssFeedFormat(new Date());
    }

    public String getCurrentPostBodyHTML()
    {
        String html = postManagement.generatePostHTML(currentPost, true);
        return html;
    }

    public String getCurrentPostURL()
    {
        String uuid = currentPost.getUuid();
        String redirect = linkSource.createPageRenderLinkWithContext(Index.class, uuid).toURI();

        String url = messages.get("website-url");

        return url + redirect.substring(1);
    }

    public String getCurrentPostDate()
    {
        return dateFormatter.rssFeedFormat(currentPost.getCreated());
    }
}
