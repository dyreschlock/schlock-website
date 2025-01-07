package com.schlock.website.pages.old.v1;

import com.schlock.website.entities.blog.AbstractCategory;
import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.PostCategory;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.Arrays;
import java.util.List;

public class V1Reviews extends AbstractVersion1Page
{
    private static final String GAMES_PAGE = AbstractOldVersionPage.GAMES_PAGE;

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private CategoryDAO categoryDAO;

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


    public List<Post> getReviewPosts()
    {
        AbstractCategory cat = categoryDAO.getByUuid(PostCategory.class, GAMES_PAGE);
        Long categoryId = cat.getId();

        return postDAO.getMostRecentPosts(null, false, null, null, categoryId);
    }

    public String getCurrentPostLink()
    {
        String uuid = currentPost.getUuid();
        return linkSource.createPageRenderLinkWithContext(V1Reviews.class, uuid).toURI();

    }

    public String getCategoryName()
    {
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
