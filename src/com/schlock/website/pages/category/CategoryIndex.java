package com.schlock.website.pages.category;

import com.schlock.website.entities.blog.Category;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.ViewState;
import com.schlock.website.pages.Archive;
import com.schlock.website.pages.Index;
import com.schlock.website.services.DateFormatter;
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
import java.util.List;

public class CategoryIndex
{
    @SessionState
    private ViewState viewState;


    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private DateFormatter dateFormat;

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


    Object onActivate(String parameter)
    {
        if (StringUtils.isBlank(parameter))
        {
            return Archive.class;
        }

        category = categoryDAO.getByUuid(parameter);
        return true;
    }

    public String getCategoryTitle()
    {
        if (currentCategory.isTopCategory())
        {
            return messages.get("most-recent");
        }
        return currentCategory.getName();
    }


    public List<Post> getTop3Posts()
    {
        List<Post> posts = postManagement.getTop3Posts(category);
        return posts;
    }

    public String getDivId()
    {
        if (currentIndex == 0)
        {
            return "primaryPost";
        }
        if (currentIndex == 1)
        {
            return "secondaryPost";
        }
        if (currentIndex == 2)
        {
            return "tertiaryPost";
        }
        return "";
    }

    Object onSelectPost(String uuid)
    {
        return linkSource.createPageRenderLinkWithContext(Index.class, uuid);
    }

    public boolean isHasImage()
    {
        return StringUtils.isNotBlank(getCurrentImage());
    }

    public String getCurrentImage()
    {
        return postManagement.getPostImage(currentPost);
    }

    public String getCreatedDate()
    {
        return dateFormat.dateFormat(currentPost.getCreated());
    }




    public List<Post> getPosts()
    {
        int postCount = 20;
        boolean unpublished = viewState.isShowUnpublished();
        Long categoryId = currentCategory.getId();

        List<Post> posts = postDAO.getMostRecentPosts(postCount, unpublished, categoryId);
        return posts;
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
