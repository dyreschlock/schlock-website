package com.schlock.website.components.blog.link;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.pages.Index;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.blog.PostManagement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.Date;

public class PostLink
{
    @Parameter(required = true)
    @Property
    private AbstractPost post;

    @Parameter
    @Property
    private String cssClass;

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private DateFormatter dateFormat;

    @Inject
    private PostManagement postManagement;


    public String getPostTitleHtml()
    {
        String title = post.getTitle();
        String html = postManagement.wrapJapaneseTextInTags(title);

        return html;
    }

    Object onSelectPost(String postUuid)
    {
        return linkSource.createPageRenderLinkWithContext(Index.class, postUuid);
    }

    public String getCreatedDate()
    {
        return dateFormat.dateFormat(post.getCreated());
    }


    public boolean isHasUpdatedTime()
    {
        return getUpdatedTime() != null;
    }

    public String getUpdatedTime()
    {
        String updatedTime = null;
        if (post.isPage())
        {
            Date date = postManagement.getUpdatedTime((Page) post);
            if(date != null)
            {
                updatedTime = dateFormat.dateFormat(date);
            }
        }
        return updatedTime;
    }
}
