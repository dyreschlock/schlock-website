package com.schlock.website.components.blog;

import com.schlock.website.entities.blog.Category;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.ViewState;
import com.schlock.website.pages.Archive;
import com.schlock.website.pages.Index;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.services.PageRenderLinkSource;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public class LinksPanel
{
    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    private PostDAO postDAO;


    @Property
    private Category currentCategory;

    @Property
    private Category currentSubcategory;

    @Property
    private Post currentPost;


    @InjectComponent
    private Zone linksZone;

    @InjectPage
    private Index index;

    @InjectPage
    private Archive archive;

    @SessionState
    private ViewState viewState;


    public List<Category> getCategories()
    {
        return categoryDAO.getTopCategoriesInOrder();
    }

    public List<Category> getSubcategories()
    {
        return categoryDAO.getSubcategoriesInOrder(currentCategory.getId());
    }

    public boolean isExpanded()
    {
        return currentCategory.getId().equals(viewState.getExpandedCategoryId());
    }

    Object onExpandCategory(Long categoryId)
    {
        if (categoryId.equals(viewState.getExpandedCategoryId()))
        {
            viewState.setExpandedCategoryId(null);
        }
        else
        {
            viewState.setExpandedCategoryId(categoryId);
        }
        return linksZone;
    }

    Object onRemoveCategory()
    {
        viewState.setCurrentCategoryId(null);
        return index;
    }

    Object onSelectCategory(Long categoryId)
    {
        viewState.setCurrentCategoryId(categoryId);

        boolean unpublished = viewState.isShowUnpublished();
        Post post = postDAO.getMostRecentPost(unpublished, categoryId);

        return linkSource.createPageRenderLinkWithContext(Index.class, post.getUuid());
    }

    Object onSelectArchive()
    {
        return archive;
    }


    public List<Post> getRecentPosts()
    {
        boolean unpublished = viewState.isShowUnpublished();
        Long categoryId = viewState.getCurrentCategoryId();

        return Collections.EMPTY_LIST;
    }

    public List<Post> getPinnedPosts()
    {
        boolean unpublished = viewState.isShowUnpublished();

        return Collections.EMPTY_LIST;
    }

    public List<Post> getPages()
    {
        boolean unpublished = viewState.isShowUnpublished();
        return postDAO.getAllPages(unpublished);
    }
}
