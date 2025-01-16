package com.schlock.website.components.old;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.ClubPost;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.Date;
import java.util.List;

public abstract class AbstractOldPostListingDisplay
{
    private static final String ARCHIVE_PAGE = AbstractOldVersionPage.ARCHIVE_PAGE;
    private static final String PROJECTS_PAGE = AbstractOldVersionPage.PROJECTS_PAGE;
    private static final String GAMES_PAGE = AbstractOldVersionPage.REVIEWS_PAGE;
    private static final String MUSIC_PAGE = AbstractOldVersionPage.MUSIC_PAGE;


    @Inject
    private DeploymentContext context;

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private DateFormatter dateFormatter;

    @Inject
    private PostManagement postManagement;

    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    private PostDAO postDAO;


    @Parameter(required = true)
    @Property
    private List<Long> categoryIds;

    @Parameter
    private String page;


    @Property
    private Long currentCategoryId;

    @Property
    private AbstractPost currentPost;

    @Property
    private Integer currentIndex;


    abstract protected SiteVersion getVersion();

    public String getPage()
    {
        return page;
    }


    public String getCurrentCategoryName()
    {
        return categoryDAO.getById(currentCategoryId).getName();
    }


    public List<Post> getPosts()
    {
        List<Post> posts = postDAO.getMostRecentPosts(null, false, null, null, currentCategoryId);
        return posts;
    }


    public String getCurrentPostDescription()
    {
        return postManagement.generatePostDescription(currentPost);
    }

    protected Class getIndexClass()
    {
        return getVersion().indexClass();
    }

    public String getCurrentPostLink()
    {
        String uuid = currentPost.getUuid();
        String page = getPage();
        if (page != null)
        {
            return linkSource.createPageRenderLinkWithContext(getIndexClass(), getPage(), uuid).toURI();
        }
        return linkSource.createPageRenderLinkWithContext(getIndexClass(), uuid).toURI();
    }

    public String getCurrentClubDate()
    {
        if(currentPost.isClubPost())
        {
            Date date = ((ClubPost) currentPost).getEventDate();
            return dateFormatter.shortDateFormat(date);
        }
        return "";
    }

    public String getNewLine()
    {
        if (currentIndex != 0 && currentIndex % 2 == 0)
        {
            return "</tr><tr>";
        }
        return "";
    }

    public String getStarImageLink()
    {
        String link = context.webDomain() + "img/old/star1.gif";
        return link;
    }
}
