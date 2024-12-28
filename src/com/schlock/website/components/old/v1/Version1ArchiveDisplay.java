package com.schlock.website.components.old.v1;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.pages.old.v1.V1Index;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

public class Version1ArchiveDisplay
{
    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private PostDAO postDAO;

    @Property
    private AbstractPost currentPost;


    public List<Post> getAllPosts()
    {
        return postDAO.getAllPublished();
    }

    public String getCurrentPostLink()
    {
        String uuid = currentPost.getUuid();
        return linkSource.createPageRenderLinkWithContext(V1Index.class, uuid).toURI();
    }
}
