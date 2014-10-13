package com.schlock.website.pages.category;

import com.schlock.website.entities.blog.AbstractCategory;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.PostCategory;
import com.schlock.website.entities.blog.ViewState;
import com.schlock.website.pages.archive.ArchiveIndex;
import com.schlock.website.services.blog.PostArchiveManagement;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.*;

public class CategoryIndex
{
    @SessionState
    private ViewState viewState;


    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private Messages messages;

    @Inject
    private PostArchiveManagement archiveManagement;

    @Inject
    private PostManagement postManagement;

    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    private PostDAO postDAO;


    private AbstractCategory category;

    @Property
    private AbstractCategory currentCategory;

    @Property
    private Post currentPost;

    @Property
    private Integer currentIndex;

    @Property
    private String currentIteration;


    private Set<Long> excludeIds;


    Object onActivate()
    {
        category = categoryDAO.getFirstCategory();

        excludeIds = new HashSet<Long>();
        excludeIds.add(getMostRecent().getId());

        return true;
    }

    Object onActivate(String parameter)
    {
        if (StringUtils.isBlank(parameter))
        {
            return ArchiveIndex.class;
        }

        category = categoryDAO.getByUuid(PostCategory.class, parameter);

        excludeIds = new HashSet<Long>();
        excludeIds.add(getMostRecent().getId());

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
        Long categoryId = category.getId();

        List<Post> posts = postManagement.getTopPosts(LIMIT, categoryId, Collections.EMPTY_SET);
        return posts.get(0);
    }

    public List<Post> getPosts()
    {
        int postCount = 20;
        boolean unpublished = viewState.isShowUnpublished();
        Long categoryId = currentCategory.getId();

        List<Post> posts = postDAO.getMostRecentPosts(postCount, unpublished, null, null, categoryId);
        return posts;
    }

    public List<Post> getPreviewPosts()
    {
        int count = getPosts().size();
        int LIMIT = (int) Math.floor(((double) count ) / ((double) 7));
        if (LIMIT < 1)
        {
            LIMIT = 1;
        }

        Long categoryId = currentCategory.getId();

        List<Post> posts = postManagement.getTopPosts(LIMIT, categoryId, excludeIds);
        for (Post post : posts)
        {
            excludeIds.add(post.getId());
        }
        return posts;
    }


    public AbstractCategory getParent()
    {
        return category.getParent();
    }

    public boolean isSubcategory()
    {
        return !category.isTopCategory();
    }


    public List<String> getIterations()
    {
        Long id = category.getId();
        return archiveManagement.getYearlyMonthlyIterations(id);
    }

    public String getIterationTitle()
    {
        return archiveManagement.getIterationTitle(currentIteration);
    }

    public List<Post> getArchivePosts()
    {
        Long id = category.getId();
        return archiveManagement.getPosts(currentIteration, id);
    }

    public List<Post> getArchivePreviewPosts()
    {
        Long id = category.getId();

        Set<Long> exclude = new HashSet<Long>();
        exclude.add(getMostRecent().getId());

        return archiveManagement.getPreviewPosts(currentIteration, id, exclude);
    }


    public String getReturnToCategory()
    {
        return messages.format("return", category.getParent().getName());
    }


    private List<AbstractCategory> cachedCategories;

    public List<AbstractCategory> getCategories()
    {
        if (cachedCategories == null)
        {
            Long id = category.getId();

            List<AbstractCategory> categories = new ArrayList<AbstractCategory>();
            if (category.isTopCategory())
            {
                categories.add(category);
            }
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
                AbstractCategory parent = category.getParent();

                title += parent.getName() + " .";
            }
            title += category.getName();
        }
        return title;
    }
}
