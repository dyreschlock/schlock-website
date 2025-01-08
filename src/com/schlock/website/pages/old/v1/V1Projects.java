package com.schlock.website.pages.old.v1;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Image;
import com.schlock.website.entities.blog.ProjectCategory;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

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
    private ProjectCategory currentCategory;

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

    public List<ProjectCategory> getProjectCategories()
    {
        return categoryDAO.getTopProjectInOrder();
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
        List<AbstractPost> posts = postDAO.getAllProjectsByCategory(false, categoryId);
        return posts;
    }

    public String getCurrentPostImage()
    {
        Image image = imageManagement.getPostImage(currentPost);
        if (image != null)
        {
            return image.getImageLink();
        }
        return null;

    }

    public String getCurrentPostLink()
    {
        return linkSource.createPageRenderLinkWithContext(V1Projects.class, currentPost.getUuid()).toURI();
    }
}
