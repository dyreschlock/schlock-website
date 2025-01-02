package com.schlock.website.components.old;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.ViewState;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

public abstract class AbstractOldRecentPosts
{
    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private PostManagement postManagement;

    @Inject
    private PostDAO postDAO;

    @SessionState
    private ViewState viewState;


    @Property
    private Post currentPost;


    abstract protected Class getVersionIndexClass();



    public List<Post> getMostRecentPosts()
    {
        final int COUNT = 12;
        boolean unpublished = viewState.isShowUnpublished();

        List<Post> posts = postDAO.getMostRecentPosts(COUNT, unpublished, null, null, null);
        return posts;
    }

    public String getCurrentPostLink()
    {
        String uuid = currentPost.getUuid();
        return linkSource.createPageRenderLinkWithContext(getVersionIndexClass(), uuid).toURI();
    }

    public String getCurrentPostDescription()
    {
        return postManagement.generatePostDescription(currentPost);
    }
}
