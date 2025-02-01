package com.schlock.website.pages.old.v1;

import com.schlock.website.entities.blog.*;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class V1Projects extends AbstractVersion1Page
{
    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    private PostDAO postDAO;

    @Inject
    private ImageManagement imageManagement;

    @Inject
    private DateFormatter dateFormatter;


    private AbstractPost post;


    @Property
    private AbstractCategory currentCategory;

    @Property
    private AbstractPost currentPost;


    Object onActivate()
    {
        post = null;
        return true;
    }

    Object onActivate(String param)
    {
        post = postDAO.getByUuid(param);
        return true;
    }

    public AbstractPost getPost()
    {
        return post;
    }

    public String getPage()
    {
        if (isHasPost())
        {
            return "project";
        }
        return super.getPage();
    }

    public String getPostDate()
    {
        return dateFormatter.dateFormat(post.getCreated());
    }

    public String getPostTitle()
    {
        return post.getTitle();
    }

    public boolean isProjectPost()
    {
        for(AbstractCategory cat : post.getCategories())
        {
            if (cat.isProject())
            {
                return true;
            }
        }
        return false;
    }

    public boolean isPhotoPost()
    {
        return !isProjectPost();
    }



    public List<ProjectCategory> getProjectCategories()
    {
        return categoryDAO.getTopProjectInOrder();
    }

    public List<AbstractCategory> getPhotoCategories()
    {
        List<AbstractCategory> categories = new ArrayList<>();
        for(String uuid : getTravelPhotoCategoryUuids())
        {
            categories.add(categoryDAO.getByUuid(PostCategory.class, uuid));
        }
        for(String uuid : getClubPhotoCategoryUuids())
        {
            categories.add(categoryDAO.getByUuid(PostCategory.class, uuid));
        }
        return categories;
    }

    public String getCategoryTitle()
    {
        return currentCategory.getName();
    }

    public String getCategoryEarlyDate()
    {
        List<AbstractPost> posts = getCategoryPosts();

        AbstractPost post = posts.get(posts.size() - 1);
        return dateFormatter.shortDateFormat(post.getCreated());
    }

    public List<AbstractPost> getCategoryPosts()
    {
        Long categoryId = currentCategory.getId();

        List<AbstractPost> posts = null;
        if (getClubPhotoCategoryUuids().contains(currentCategory.getUuid()))
        {
            List<ClubPost> results = postDAO.getAllClubPosts(true);

            posts = new ArrayList<>();
            posts.addAll(results);
        }
        else if (currentCategory.isProject())
        {
            posts = postDAO.getAllProjectsByCategory(false, categoryId);
        }
        else if (currentCategory.isPost())
        {
            posts = postDAO.getMostRecentPostsWithGallery(null, false, null, null, categoryId, Collections.EMPTY_SET);
        }
        return posts;
    }

    public String getCurrentPostImage()
    {
        String link = imageManagement.getPostPreviewImageLink(currentPost);
        if (link != null)
        {
            return link;
        }
        return null;
    }

    public String getCurrentPostLink()
    {
        return linkSource.createPageRenderLinkWithContext(V1Projects.class, currentPost.getUuid()).toURI();
    }
}
