package com.schlock.website.components.blog.content;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.pages.Index;
import com.schlock.website.services.blog.PostManagement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class ArchivePostDisplay
{
    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private PostManagement postManagement;

    @Parameter(required = true)
    @Property
    private Post post;


    Object onSelectPost(String postUuid)
    {
        return linkSource.createPageRenderLinkWithContext(Index.class, postUuid);
    }

    public String getPreviewHtml()
    {
        String html = postManagement.generatePostPreview(post);
        return html;
    }
}
