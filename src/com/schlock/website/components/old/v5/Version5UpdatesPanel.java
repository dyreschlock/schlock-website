package com.schlock.website.components.old.v5;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.PostCategory;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

import java.util.List;

public class Version5UpdatesPanel
{
    @Parameter(required = true)
    @Property
    private List<AbstractPost> posts;

    @Parameter(required = true)
    @Property
    private AbstractPost post;

    @Parameter(required = true)
    @Property
    private Integer pageNumber;

    @Parameter(required = true)
    @Property
    private PostCategory category;

    @Property
    private AbstractPost currentPost;

}
