package com.schlock.website.components.blog.link;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.pages.Index;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class PostLink
{
    @Inject
    private PageRenderLinkSource linkSource;

    @Parameter(required = true)
    @Property
    private Post post;


    Object onSelectPost(String postUuid)
    {
        return linkSource.createPageRenderLinkWithContext(Index.class, postUuid);
    }
}
