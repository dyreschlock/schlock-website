package com.schlock.website.pages.old.v1;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionIndex;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.blog.CssCache;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class V1Index extends AbstractOldVersionIndex
{
    @Inject
    private DeploymentContext context;

    @Inject
    private DateFormatter dateFormatter;

    @Inject
    private CssCache cssCache;

    @Inject
    private PostManagement postManagement;

    @Inject
    private PostDAO postDAO;


    @Property
    private AbstractPost post;



    Object onActivate()
    {
        post = postDAO.getMostRecentFrontPagePost(null);

        return true;
    }

    Object onActivate(String param)
    {
        AbstractPost requested = null;

        List<AbstractPost> posts = postDAO.getAllByUuid(param);
        for(AbstractPost post : posts)
        {
            if (!post.isCoursePage())
            {
                requested = post;
            }
        }
        if (requested == null)
        {
            return onActivate();
        }
        post = requested;

        return true;
    }


    public String getPageCss()
    {
        return cssCache.getCssOldVersions(post, SiteVersion.V1);
    }


    public String getImageLinkPopular()
    {
        String link = context.webDomain() + "img/old/popular.gif";
        return link;
    }

    public String getImageLinkComic()
    {
        String link = context.webDomain() + "img/old/pic4.jpg";
        return link;
    }

    public String getPostTitle()
    {
        return post.getTitle();
    }

    public String getPostBodyHTML()
    {
        String html = postManagement.generatePostHTML(post, SiteVersion.V1);
        return html;
    }

    public boolean isHasDate()
    {
        return post != null && post.getCreated() != null;
    }

    public String getPostDate()
    {
        return dateFormatter.shortDateFormat(post.getCreated());
    }
}
