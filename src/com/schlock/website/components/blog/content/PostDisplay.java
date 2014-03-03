package com.schlock.website.components.blog.content;

import com.schlock.website.entities.blog.Category;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.ViewState;
import com.schlock.website.pages.Index;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

public class PostDisplay
{
    @Parameter(required = true)
    @Property
    private Post post;


    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private PostDAO postDAO;


    @SessionState
    private ViewState viewState;


    @Property
    private Post currentPost;

    @Property
    private int currentIndex;

    private List<Post> cachedNextPosts;
    private List<Post> cachedPreviousPosts;

    @Property
    private Category currentCategory;

    @Property
    private Category currentSubcategory;


    public List<Category> getTopCategories()
    {
        return post.getTopCategories();
    }

    public List<Category> getSubcategories()
    {
        return post.getSubcategories(currentCategory);
    }

    Object onSelectCategory(Long categoryId)
    {
        viewState.setCurrentCategoryId(categoryId);

        boolean unpublished = viewState.isShowUnpublished();
        Post post = postDAO.getMostRecentPost(unpublished, categoryId);

        return linkSource.createPageRenderLinkWithContext(Index.class, post.getUuid());
    }


    public boolean isHasPreviousNextPosts()
    {
        return isHasNextPosts() || isHasPreviousPosts();
    }

    public boolean isHasNextPosts()
    {
        List<Post> posts = getNextPosts();
        return posts != null && posts.size() > 0;
    }

    public List<Post> getNextPosts()
    {
        if(cachedNextPosts == null)
        {
            boolean unpublished = viewState.isShowUnpublished();
            Long categoryId = viewState.getCurrentCategoryId();

            cachedNextPosts = postDAO.getNextPosts(post, unpublished, categoryId);
        }
        return cachedNextPosts;
    }

    public boolean isHasPreviousPosts()
    {
        List<Post> posts = getPreviousPosts();
        return posts != null && posts.size() > 0;
    }

    public List<Post> getPreviousPosts()
    {
        if(cachedPreviousPosts == null)
        {
            boolean unpublished = viewState.isShowUnpublished();
            Long categoryId = viewState.getCurrentCategoryId();

            cachedPreviousPosts = postDAO.getPreviousPosts(post, unpublished, categoryId);
        }
        return cachedPreviousPosts;
    }
}
