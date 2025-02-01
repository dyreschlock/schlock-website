package com.schlock.website.components.blog;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.pages.Feed;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.blog.CssCache;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.blog.PostManagement;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class LayoutBlog
{
    @Inject
    private PageRenderLinkSource linkSource;

    @Parameter
    @Property
    private AbstractPost post;

    @Parameter
    private String pageName;

    @Parameter
    private String pageDescription;

    @Parameter
    private String pageUrl;

    @Inject
    private ImageManagement imageManagement;

    @Inject
    private PostManagement postManagement;

    @Inject
    private CssCache cssCache;

    @Inject
    private DeploymentContext context;

    @Inject
    private Messages messages;


    public String getTitle()
    {
        String title = getWebsiteTitle();
        String name = getPageTitle();

        return title + " // " + name;
    }

    public String getWebsiteTitle()
    {
        return messages.get("website-title");
    }

    public String getPageTitle()
    {
        String name = "";
        if (StringUtils.isNotBlank(pageName))
        {
            name = pageName;
        }
        else if (post != null)
        {
            name = post.getTitle();
        }
        return name;
    }

    public String getPageUrl()
    {
        String url = "";
        if (StringUtils.isNotBlank(pageUrl))
        {
            url = pageUrl;
        }
        else if (post != null)
        {
            if (post.isCoursePage())
            {
                url = "/courses";
            }
            url += "/" + post.getUuid();
        }

        String domain = context.webDomain();
        if (StringUtils.isNotBlank(url) && !url.startsWith(domain))
        {
            url = domain + url.substring(1);
        }

        return url;
    }

    public boolean isHasDescription()
    {
        String description = getPostDescription();
        return StringUtils.isNotBlank(description);
    }

    public String getPostDescription()
    {
        String description = "";
        if (post != null)
        {
            description = postManagement.generatePostDescription(post);
        }
        if (StringUtils.isBlank(description) && StringUtils.isNotBlank(pageDescription))
        {
            return pageDescription;
        }
        return description;
    }

    public boolean isHasCoverImage()
    {
        if (post != null)
        {
            String link = imageManagement.getPostPreviewImageLink(post);
            return link != null;
        }
        return false;
    }

    public String getCoverImageUrl()
    {
        String imageUrl = "";
        if (post != null)
        {
            String uuid = post.getUuid();
            imageUrl = imageManagement.getPostPreviewMetadataLink(uuid);
        }
        return imageUrl;
    }

    public String getRssUrl()
    {
        String url = linkSource.createPageRenderLink(Feed.class).toURI();
        return url;
    }

    public String getPrimaryCss()
    {
        return cssCache.getAllCss(post);
    }

    public String getExtraCSS()
    {
        String extra = "";
        if (post != null && Page.ERROR_PAGE_UUID.equals(post.getUuid()))
        {
            extra = "error";
        }
        return extra;
    }
}
