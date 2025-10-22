package com.schlock.website.components.old.v4;

import com.schlock.website.components.old.AbstractOldPagedPreviousNext;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionPage;
import com.schlock.website.services.blog.PostArchiveManagement;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class Version4PagedPreviousNext extends AbstractOldPagedPreviousNext
{
    @Parameter(required = true)
    private List<String> keywordNames;

    @Inject
    private PostArchiveManagement archiveManagement;


    protected SiteVersion getVersion()
    {
        return SiteVersion.V4;
    }

    public List<String> getKeywordNames()
    {
        return keywordNames;
    }

    protected String getLinkContext()
    {
        if (StringUtils.isBlank(getPage()))
        {
            return AbstractOldVersionPage.ARCHIVE_PAGE;
        }
        return super.getLinkContext();
    }

    public boolean isHasPrevious()
    {
        if (isPostBasePage())
        {
            return true;
        }
        if (AbstractOldVersionPage.REVIEWS_PAGE.equals(getPage()) || getPost() != null)
        {
            return false;
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
