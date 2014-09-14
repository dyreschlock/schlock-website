package com.schlock.website.components.blog.content;

import com.schlock.website.entities.blog.AbstractCategory;
import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.ProjectCategory;
import com.schlock.website.pages.Index;
import com.schlock.website.pages.Projects;
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
    private PageRenderLinkSource linkSource;

    @Inject
    private DateFormatter dateFormat;

    @Inject
    private PostManagement postManagement;


    @Property
    private AbstractCategory currentCategory;


    Object onSelectPost(String uuid)
    {
        return linkSource.createPageRenderLinkWithContext(Index.class, uuid);
    }

    public String getCreatedDate()
    {
        return dateFormat.dateFormat(post.getCreated());
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

    public List<ProjectCategory> getProjectCategories()
    {
        return post.getProjectCategories();
    }

    public String getCategoryCssClass()
    {
        String css = "";

        AbstractCategory parent = currentCategory.getParent();
        if(parent != null)
        {
            css = parent.getUuid();
        }
        return css;
    }

    Object onSelectProjectCategory(String categoryUuid)
    {
        return linkSource.createPageRenderLinkWithContext(Projects.class, categoryUuid);
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
