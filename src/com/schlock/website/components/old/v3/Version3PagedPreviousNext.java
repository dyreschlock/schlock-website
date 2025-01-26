package com.schlock.website.components.old.v3;

import com.schlock.website.components.old.AbstractOldPagedPreviousNext;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;
import com.schlock.website.services.blog.PostArchiveManagement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class Version3PagedPreviousNext extends AbstractOldPagedPreviousNext
{
    @Parameter(required = true)
    private List<Long> categoryIds;

    @Inject
    private PostArchiveManagement archiveManagement;

    protected SiteVersion getVersion()
    {
        return SiteVersion.V3;
    }

    public List<Long> getCategoryIds()
    {
        return categoryIds;
    }

    public boolean isHasPrevious()
    {
        if (isPostBasePage())
        {
            return true;
        }
        return super.isHasPrevious();
    }

    public String getPreviousPageLink()
    {
        if (getPost() != null)
        {
            return getPageLink(1);
        }
        return super.getPreviousPageLink();
    }

    protected int getPagePostCount(int pageNumber)
    {
        if (AbstractOldVersionPage.CLUB_PAGE.equals(getPage()))
        {
            final int postCount = AbstractOldVersionPage.POSTS_PER_PAGE;

            List<Post> posts = archiveManagement.getPagedClubPosts(postCount, pageNumber);
            return posts.size();
        }
        return super.getPagePostCount(pageNumber);
    }
}
