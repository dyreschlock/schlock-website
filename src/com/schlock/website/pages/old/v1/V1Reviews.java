package com.schlock.website.pages.old.v1;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Keyword;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.KeywordDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class V1Reviews extends AbstractVersion1Page
{
    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private DateFormatter dateFormatter;

    @Inject
    private KeywordDAO keywordDAO;

    @Inject
    private PostDAO postDAO;

    @Inject
    private PostManagement postManagement;

    private AbstractPost post;


    @Property
    private AbstractPost currentPost;

    @Property
    private Integer currentIndex;


    Object onActivate()
    {
        post = null;
        return true;
    }

    Object onActivate(String param)
    {
        post = postDAO.getByUuid(param);
        return true;
    }

    public boolean isHasPost()
    {
        return post != null;
    }

    public SiteVersion getVersion()
    {
        return SiteVersion.V1;
    }

    public List<AbstractPost> getPosts()
    {
        if (post != null)
        {
            return Arrays.asList(post);
        }
        return super.getPosts();
    }

    public String getPostTitle()
    {
        return post.getTitle();
    }

    public String getPostBodyHTML()
    {
        String html = postManagement.generatePostHTML(post, getVersion());
        return html;

    }

    public String getPostDate()
    {
        return dateFormatter.dotFormat(post.getCreated());
    }

    public List<Post> getReviewPosts()
    {
        Set<String> names = new HashSet<>(getReviewCategoryNames());
        return postDAO.getMostRecentPosts(null, false, null, null, names);
    }

    public String getCurrentPostLink()
    {
        String uuid = currentPost.getUuid();
        return linkSource.createPageRenderLinkWithContext(V1Reviews.class, uuid).toURI();

    }

    public String getCategoryName()
    {
        for(Keyword keyword : currentPost.getKeywords())
        {
            if (getReviewCategoryNames().contains(keyword.getName()))
            {
                return keyword.getName();
            }
        }
        return "Game";
    }

    public String getAlignment()
    {
        if (currentIndex % 2 == 0)
        {
            return "left";
        }
        return "right";
    }

    public String getNewLine()
    {
        if (currentIndex != 0 && currentIndex % 2 == 0)
        {
            return "</tr><tr>";
        }
        return "";
    }
}
