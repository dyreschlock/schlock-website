package com.schlock.website.components.blog.content;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.pages.Index;
import com.schlock.website.services.blog.PostManagement;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class MiniPostDisplay
{
    @Parameter(required = true)
    @Property
    private Post post;

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private PostManagement postManagement;


    Object onSelectPost(String uuid)
    {
        return linkSource.createPageRenderLinkWithContext(Index.class, uuid);
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
