package com.schlock.website.components.blog;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.ViewState;
import com.schlock.website.services.database.blog.PostDAO;
import com.schlock.website.services.database.blog.impl.PostDAOImpl;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

@Import(stylesheet = {"context:layout/blog.css",
                        "context:layout/layout.css"})
public class LayoutBlog
{
    @Parameter(required = true)
    @Property
    private String title;

    @Inject
    private PostDAO postDAO;

    @SessionState
    private ViewState viewState;


    @Property
    private Post currentPost;

    @Property
    private Integer currentIndex;


    public List<Post> getRecentPosts()
    {
        int postCount = PostDAOImpl.TOP_RECENT;
        boolean unpublished = viewState.isShowUnpublished();

        List<Post> posts = postDAO.getRecentPostsByYearMonth(postCount, null, null, unpublished, null);
        return posts;
    }

    public List<Post> getRecentPinnedPosts()
    {
        int postCount = PostDAOImpl.TOP_RECENT;
        boolean unpublished = viewState.isShowUnpublished();

        List<Post> posts = postDAO.getRecentPinnedPostsByYearMonth(postCount, null, null, unpublished, null);
        return posts;
    }
}
