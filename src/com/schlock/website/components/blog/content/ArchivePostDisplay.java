package com.schlock.website.components.blog.content;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.pages.Index;
import com.schlock.website.services.DateFormatter;
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
    private DateFormatter dateFormat;

    @Inject
    private PostManagement postManagement;

    @Parameter(required = true)
    @Property
    private AbstractPost post;

    @Parameter(required = true)
    @Property
    private String postClass;



    Object onSelectPost(String postUuid)
    {
        return linkSource.createPageRenderLinkWithContext(Index.class, postUuid);
    }

    public String getCreatedDate()
    {
        return dateFormat.dateFormat(post.getCreated());
    }

    public String getPreviewHtml()
    {
        String html = postManagement.generatePostPreview(post);
        return html;
    }
}
