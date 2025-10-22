package com.schlock.website.components.blog.content;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Keyword;
import com.schlock.website.pages.Index;
import com.schlock.website.pages.projects.ProjectsIndex;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.blog.PostManagement;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

public class MiniPostDisplay
{
    @Parameter(required = true)
    @Property
    private AbstractPost post;

    @Inject
    private DateFormatter dateFormat;

    @Inject
    private PostManagement postManagement;

    @Inject
    private PageRenderLinkSource linkSource;


    @Property
    private Keyword currentKeyword;

    public boolean isShowDateLinks()
    {
        if (post.isPage() && !post.isProject())
        {
            return false;
        }
        return true;
    }

    public String getCreatedDate()
    {
        return dateFormat.dateFormat(post.getCreated());
    }

    public String getPostLink()
    {
        return linkSource.createPageRenderLinkWithContext(Index.class, post.getUuid()).toURI();
    }

    public String getPostTitleHtml()
    {
        String title = post.getTitle();
        String html = postManagement.wrapJapaneseTextInTags(title);

        return html;
    }

    public boolean isProject()
    {
        return post.isProject();
    }

    public List<Keyword> getProjectKeywords()
    {
        return post.getAllSubProjectKeywords();
    }

    public String getProjectLink()
    {
        return linkSource.createPageRenderLinkWithContext(ProjectsIndex.class, currentKeyword.getName()).toURI();
    }

    public String getCssClass()
    {
        String css = "";

        Keyword parent = currentKeyword.getParent();
        if (parent != null)
        {
            css = parent.getName();
        }
        return css;
    }


    public String getPreviewHtml()
    {
        String html = postManagement.generatePostPreview(post);
        return html;
    }

    public boolean isHasLessonPlan()
    {
        return StringUtils.isNotBlank(post.getLessonPlanLink());
    }

    public boolean isHasFlashCards()
    {
        return StringUtils.isNotBlank(post.getFlashCardsLink());
    }
}
