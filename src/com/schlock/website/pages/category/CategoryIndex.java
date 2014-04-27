package com.schlock.website.pages.category;

import com.schlock.website.entities.blog.Category;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.ViewState;
import com.schlock.website.pages.Archive;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CategoryIndex
{
    @SessionState
    private ViewState viewState;


    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private Messages messages;

    @Inject
    private PostManagement postManagement;

    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    private PostDAO postDAO;


    private Category category;

    @Property
    private Category currentCategory;

    @Property
    private Post currentPost;

    @Property
    private Integer currentIndex;


    Object onActivate()
    {
        category = categoryDAO.getFirstCategory();
        return true;
    }

    Object onActivate(String parameter)
    {
        if (StringUtils.isBlank(parameter))
        {
            return Archive.class;
        }

        category = categoryDAO.getByUuid(parameter);
        return true;
    }

    public boolean isPageCategory()
    {
        Long pageId = category.getId();
        Long catId = currentCategory.getId();

        return pageId.equals(catId);
    }

    public String getCategoryTitle()
    {
        if (isPageCategory())
        {
            return messages.get("most-recent");
        }
        return currentCategory.getName();
    }


    Object onSelectCategory(String categoryUuid)
    {
        return linkSource.createPageRenderLinkWithContext(CategoryIndex.class, categoryUuid);
    }


    public Post getMostRecent()
    {
        int LIMIT = 1;
        List<Post> posts = postManagement.getTopPostsForCategory(LIMIT, category, Collections.EMPTY_LIST);
        return posts.get(0);
    }

    public List<Post> getPreviewPosts()
    {
        int count = getPosts().size();
        int LIMIT = (int) Math.floor(((double) count ) / ((double) 7));
        if (LIMIT < 1)
        {
            LIMIT = 1;
        }

        List<Long> exclude = Arrays.asList(getMostRecent().getId());

        List<Post> posts = postManagement.getTopPostsForCategory(LIMIT, currentCategory, exclude);
        return posts;
    }


    public List<Post> getPosts()
    {
        int postCount = 20;
        boolean unpublished = viewState.isShowUnpublished();
        Long categoryId = currentCategory.getId();

        List<Post> posts = postDAO.getMostRecentPosts(postCount, unpublished, categoryId);
        return posts;
    }

    public boolean isHasManyPosts()
    {
        if (!currentCategory.isTopCategory())
        {
            return getPosts().size() > 10;
        }
        return false;
    }


    public Category getParent()
    {
        return category.getParent();
    }

    public boolean isSubcategory()
    {
        return !category.isTopCategory();
    }

    public String getReturnToCategory()
    {
        return messages.format("return", category.getParent().getName());
    }


    private List<Category> cachedCategories;

    public List<Category> getCategories()
    {
        if (cachedCategories == null)
        {
            Long id = category.getId();

            List<Category> categories = new ArrayList<Category>();
            categories.add(category);
            categories.addAll(categoryDAO.getSubInOrder(id));

            cachedCategories = categories;
        }
        return cachedCategories;
    }


    public String getPageTitle()
    {
        String title = "";
        if(category != null)
        {
            if (!category.isTopCategory())
            {
                Category parent = category.getParent();

                title += parent.getName() + " .";
            }
            title += category.getName();
        }
        return title;
    }
}
