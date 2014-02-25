package com.schlock.website.components.blog;

import com.schlock.website.model.blog.Category;
import com.schlock.website.model.blog.Post;
import com.schlock.website.model.blog.ViewState;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;

import javax.inject.Inject;
import java.util.List;

public class LinksPanel
{
    @Inject
    private PostDAO postDAO;

    @Inject
    private CategoryDAO categoryDAO;

    @Property
    private Category currentCategory;

    @Property
    private Post currentPost;

    @SessionState
    private ViewState viewState;


    public List<Category> getCategories()
    {
        return categoryDAO.getAllInOrder();
    }

    public List<Post> getRecentPosts()
    {
        boolean unpublished = viewState.isShowUnpublished();
        return postDAO.getRecentPosts(unpublished);
    }

    public List<Post> getPinnedPosts()
    {
        boolean unpublished = viewState.isShowUnpublished();
        return postDAO.getRecentPinnedPosts(unpublished);
    }

    public List<Post> getPages()
    {
        boolean unpublished = viewState.isShowUnpublished();
        return postDAO.getAllPages(unpublished);
    }
}
