package com.schlock.website.components.blog;

import com.schlock.website.entities.Icon;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.ViewState;
import com.schlock.website.pages.AboutMe;
import com.schlock.website.pages.Index;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.PostDAO;
import com.schlock.website.services.database.blog.impl.PostDAOImpl;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

@Import(stylesheet = {"context:layout/blog.css",
                        "context:layout/layout.css"})
public class LayoutBlog
{
    @Parameter(required = true)
    @Property
    private String title;


    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private PostManagement postManagement;

    @Inject
    private PostDAO postDAO;

    @InjectPage
    private Index index;

    @InjectPage
    private AboutMe aboutMe;



    @SessionState
    private ViewState viewState;


    @Property
    private Post currentPost;

    @Property
    private Integer currentIndex;


    Object onHome()
    {
        return index;
    }

    Object onAboutMe()
    {
        return aboutMe;
    }

    @CommitAfter
    void onRegeneratePostHTML()
    {
        postManagement.regenerateAllPostHTML();
    }

    Object onCodejamLink()
    {
        return linkSource.createPageRenderLink(com.schlock.website.pages.codejam.may2012.Index.class);
    }

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

    public List<Post> getPages()
    {
        boolean unpublished = viewState.isShowUnpublished();

        List<Post> pages = postDAO.getAllPages(unpublished);
        return pages;
    }

    public Icon getTwitter()
    {
        return Icon.TWITTER;
    }

    public Icon getFacebook()
    {
        return Icon.FACEBOOK;
    }

    public Icon getYoutube()
    {
        return Icon.YOUTUBE;
    }

    public Icon getEbay()
    {
        return Icon.EBAY;
    }
}
