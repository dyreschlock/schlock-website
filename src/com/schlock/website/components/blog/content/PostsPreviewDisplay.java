package com.schlock.website.components.blog.content;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.pages.Index;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.blog.PostManagement;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

public class PostsPreviewDisplay
{
    @Parameter(required = true)
    @Property
    private List<Post> posts;


    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private PostManagement postManagement;

    @Inject
    private DateFormatter dateFormat;


    @Property
    private Post currentPost;

    @Property
    private Integer currentIndex;



    public String getDivId()
    {
        String FIRST = "primaryPost";
        String SECOND = "secondaryPost";
        String THIRD = "tertiaryPost";

        boolean size2 = posts.size() == 2;
        boolean size3 = posts.size() == 3;

        boolean index0 = currentIndex == 0;
        boolean index1 = currentIndex == 1;
        boolean index2 = currentIndex == 2;

        if (size2)
        {
            if (index0) return SECOND;
            if (index1) return THIRD;
        }
        if (size3)
        {
            if (index0) return FIRST;
            if (index1) return SECOND;
            if (index2) return THIRD;
        }
        return "";
    }

    Object onSelectPost(String uuid)
    {
        return linkSource.createPageRenderLinkWithContext(Index.class, uuid);
    }

    public boolean isHasImage()
    {
        return StringUtils.isNotBlank(getCurrentImage());
    }

    public String getCurrentImage()
    {
        return postManagement.getPostImage(currentPost);
    }

    public String getCreatedDate()
    {
        return dateFormat.dateFormat(currentPost.getCreated());
    }

}
