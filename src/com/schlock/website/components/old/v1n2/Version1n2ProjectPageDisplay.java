package com.schlock.website.components.old.v1n2;

import com.schlock.website.entities.blog.AbstractCategory;
import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.ClubPost;
import com.schlock.website.entities.blog.ProjectCategory;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.v1.V1Projects;
import com.schlock.website.pages.old.v2.V2Projects;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Version1n2ProjectPageDisplay
{
    @Parameter(required = true)
    private SiteVersion version;

    @Parameter(required = true)
    @Property
    private List<AbstractCategory> photoCategories;


    @Inject
    private ImageManagement imageManagement;

    @Inject
    private DateFormatter dateFormatter;

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    private PostDAO postDAO;



    @Property
    private AbstractCategory currentCategory;

    @Property
    private AbstractPost currentPost;


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

        List<AbstractPost> posts = null;
        if ("events".equals(currentCategory.getUuid()))
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
            posts = postDAO.getMostRecentPostsWithGallery(null, false, null, null, categoryId, null, Collections.EMPTY_SET);
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
        Class projectsPage = V1Projects.class;
        if (SiteVersion.V2.equals(version))
        {
            projectsPage = V2Projects.class;
        }
        return linkSource.createPageRenderLinkWithContext(projectsPage, currentPost.getUuid()).toURI();
    }
}
