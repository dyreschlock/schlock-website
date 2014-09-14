package com.schlock.website.components.blog.content;

import com.schlock.website.entities.blog.*;
import com.schlock.website.services.database.blog.PostDAO;
import com.schlock.website.services.database.blog.impl.PostDAOImpl;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostPreviousNext
{
    @Parameter(required = true)
    @Property
    private AbstractPost post;

    @Inject
    private Messages messages;

    @Inject
    private PostDAO postDAO;


    @SessionState
    private ViewState viewState;


    @Property
    private Post currentPost;

    @Property
    private int currentPostIndex;

    @Property
    private AbstractCategory currentCategory;

    @Property
    private int currentCategoryIndex;



    public List<PostCategory> getCategories()
    {
        List<PostCategory> categories = new ArrayList<PostCategory>();
        categories.add(null);

        for (PostCategory top : post.getTopPostCategories())
        {
            categories.add(top);
            for (AbstractCategory sub : post.getSubcategories(top))
            {
                categories.add((PostCategory) sub);
            }
        }
        return categories;
    }


    public boolean isNewLine()
    {
        if (currentCategory == null || currentCategory.isTopCategory())
        {
            return true;
        }

        List<PostCategory> categories = getCategories();
        int nextCategoryIndex = currentCategoryIndex + 1;

        if (nextCategoryIndex == categories.size())
        {
            return true;
        }

        PostCategory nextCategory = categories.get(nextCategoryIndex);
        if (nextCategory.isTopCategory())
        {
            return true;
        }

        return false;
    }

    public String getPostClass()
    {
        if (currentPostIndex >= PostDAOImpl.MIN_RECENT)
        {
            return "minSizeHidden";
        }
        return "";
    }

    public String getCategoryClass()
    {
        String classes = "";
        if (currentCategory != null)
        {
            classes += " minSizeHidden";
            if (!currentCategory.isTopCategory())
            {
                classes += " subcategory";
            }
        }
        return classes;
    }


    public boolean isHasNextPosts()
    {
        List<Post> posts = getNextPosts();
        return posts != null && posts.size() > 0;
    }

    public List<Post> getNextPosts()
    {
        boolean unpublished = viewState.isShowUnpublished();

        List<Post> posts = null;
        if (currentCategory != null)
        {
            Long categoryId = currentCategory.getId();

            Post next = postDAO.getNextPost(post, unpublished, categoryId);
            if(next != null)
            {
                posts = Arrays.asList(next);
            }
        }
        else
        {
            posts = postDAO.getNextPosts(post, unpublished, null);
        }
        return posts;
    }

    public boolean isHasPreviousPosts()
    {
        List<Post> posts = getPreviousPosts();
        return posts != null && posts.size() > 0;
    }

    public List<Post> getPreviousPosts()
    {
        boolean unpublished = viewState.isShowUnpublished();

        List<Post> posts = null;
        if (currentCategory != null)
        {
            Long categoryId = currentCategory.getId();

            Post previous = postDAO.getPreviousPost(post, unpublished, categoryId);
            if(previous != null)
            {
                posts = Arrays.asList(previous);
            }
        }
        else
        {
            posts = postDAO.getPreviousPosts(post, unpublished, null);
        }
        return posts;
    }

    public String getNextTitle()
    {
        if (currentCategory != null)
        {
            String categoryName = currentCategory.getName();

            if (currentCategory.getParent() != null)
            {
                String parentName = currentCategory.getParent().getName();
                categoryName = parentName + " ." + categoryName;
            }

            String message = messages.get("nextInCategory");
            return String.format(message, categoryName);
        }
        return messages.get("next");
    }

    public String getPreviousTitle()
    {
        if (currentCategory != null)
        {
            String categoryName = currentCategory.getName();

            if (currentCategory.getParent() != null)
            {
                String parentName = currentCategory.getParent().getName();
                categoryName = parentName + " ." + categoryName;
            }

            String message = messages.get("previousInCategory");
            return String.format(message, categoryName);
        }
        return messages.get("previous");
    }
}
