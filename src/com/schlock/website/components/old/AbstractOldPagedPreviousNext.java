package com.schlock.website.components.old;

import com.schlock.website.entities.blog.AbstractCategory;
import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;
import com.schlock.website.services.blog.PostArchiveManagement;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

public abstract class AbstractOldPagedPreviousNext
{
    @Parameter(required = true)
    private AbstractPost post;

    @Parameter(required = true)
    private Integer pageNumber;

    @Parameter
    private AbstractCategory category;

    @Parameter
    private String page;

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private PostArchiveManagement archiveManagement;


    abstract protected SiteVersion getVersion();


    public boolean isHasNext()
    {
        if (post != null)
        {
            return false;
        }

        int nextpage = pageNumber -1;
        return getPagePostCount(nextpage) > 0;
    }

    public String getNextPageLink()
    {
        int nextpage = pageNumber -1;
        return getPageLink(nextpage);
    }

    public boolean isHasPrevious()
    {
        if (post != null)
        {
            return false;
        }

        int previousPage = pageNumber +1;
        return getPagePostCount(previousPage) > 0;
    }

    public String getPreviousPageLink()
    {
        int previouspage = pageNumber +1;
        return getPageLink(previouspage);
    }

    protected String getLinkContext()
    {
        String context = page;
        if (StringUtils.isBlank(context) && category != null)
        {
            context = category.getUuid();
        }
        return context;
    }

    protected String getPageLink(int pageNumber)
    {
        String context = getLinkContext();
        if (pageNumber > 1)
        {
            return linkSource.createPageRenderLinkWithContext(getVersion().indexClass(), context, pageNumber).toURI();
        }
        return linkSource.createPageRenderLinkWithContext(getVersion().indexClass(), context).toURI();

    }

    protected int getPagePostCount(int pageNumber)
    {
        final int postCount = AbstractOldVersionPage.POSTS_PER_PAGE;

        List<Post> results;
        if (AbstractOldVersionPage.BLOG_PAGE.equals(page))
        {
            results = archiveManagement.getPagedPosts(postCount, pageNumber);
        }
        else if (category != null)
        {
            Long catId = category.getId();
            results = archiveManagement.getPagedPosts(postCount, pageNumber, catId);
        }
        else
        {
            String archiveIteration = page;
            results = archiveManagement.getPagedPosts(postCount, pageNumber, archiveIteration);
        }
        return results.size();
    }
}
