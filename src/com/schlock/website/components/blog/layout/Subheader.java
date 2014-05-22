package com.schlock.website.components.blog.layout;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Category;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.ViewState;
import com.schlock.website.pages.Index;
import com.schlock.website.pages.category.CategoryIndex;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

public class Subheader
{
    @Parameter
    private AbstractPost post;


    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private PostManagement postManagement;

    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    private PostDAO postDAO;


    @SessionState
    private ViewState viewState;


    @Property
    private Category currentCategory;

    @Property
    private Integer currentIndex;



    public boolean isHasBoth()
    {
        return isHasNext() && isHasPrevious();
    }


    public boolean isHasPrevious()
    {
        return getPreviousPost() != null;
    }

    private Post cachedPreviousPost;

    public Post getPreviousPost()
    {
        if (post != null && !post.isPage() && cachedPreviousPost == null)
        {
            Post currentPost = (Post) post;
            boolean unpublished = viewState.isShowUnpublished();

            cachedPreviousPost = postDAO.getPreviousPost(currentPost, unpublished, null);
        }
        return cachedPreviousPost;
    }

    public String getPreviousPostTitleHtml()
    {
        String title = getPreviousPost().getTitle();
        String html = postManagement.wrapJapaneseTextInTags(title);

        return html;
    }


    public boolean isHasNext()
    {
        return getNextPost() != null;
    }

    private Post cachedNextPost;

    public Post getNextPost()
    {
        if (post != null && !post.isPage() && cachedNextPost == null)
        {
            Post currentPost = (Post) post;
            boolean unpublished = viewState.isShowUnpublished();

            cachedNextPost = postDAO.getNextPost(currentPost, unpublished, null);
        }
        return cachedNextPost;
    }

    public String getNextPostTitleHtml()
    {
        String title = getNextPost().getTitle();
        String html = postManagement.wrapJapaneseTextInTags(title);

        return html;
    }

    Object onSelectPost(String postUuid)
    {
        return linkSource.createPageRenderLinkWithContext(Index.class, postUuid);
    }



    public List<Category> getCategories()
    {
        return categoryDAO.getTopInOrder();
    }

    public boolean isNotFirst()
    {
        return currentIndex != 0;
    }

    Object onSelectCategory(String categoryUuid)
    {
        return linkSource.createPageRenderLinkWithContext(CategoryIndex.class, categoryUuid);
    }
}
