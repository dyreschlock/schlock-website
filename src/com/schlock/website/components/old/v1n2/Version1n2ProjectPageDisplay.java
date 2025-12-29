package com.schlock.website.components.old.v1n2;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.ClubPost;
import com.schlock.website.entities.blog.Keyword;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.v1.V1Projects;
import com.schlock.website.pages.old.v2.V2Projects;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.SiteGenerationCache;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.database.blog.KeywordDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.ArrayList;
import java.util.List;

public class Version1n2ProjectPageDisplay
{
    @Parameter(required = true)
    private SiteVersion version;

    @Parameter(required = true)
    @Property
    private List<Keyword> photoCategories;


    @Inject
    private ImageManagement imageManagement;

    @Inject
    private DateFormatter dateFormatter;

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private SiteGenerationCache siteCache;

    @Inject
    private KeywordDAO keywordDAO;

    @Inject
    private PostDAO postDAO;



    @Property
    private Keyword currentKeyword;

    @Property
    private AbstractPost currentPost;


    public List<Keyword> getProjectCategories()
    {
        return keywordDAO.getTopProjectKeywordsInOrder();
    }

    public boolean isHasPosts()
    {
        List<AbstractPost> posts = getCategoryPosts();

        return !posts.isEmpty();
    }

    public String getCategoryEarlyDate()
    {
        List<AbstractPost> posts = getCategoryPosts();

        AbstractPost post = posts.get(posts.size() - 1);
        return dateFormatter.shortDateFormat(post.getCreated());
    }

    public List<AbstractPost> getCategoryPosts()
    {
        String keywordName = currentKeyword.getName();

        List<AbstractPost> posts = siteCache.getCachedAbstractPosts(SiteGenerationCache.OLD_VERSION_PROJECT_POSTS, keywordName);
        if (posts == null)
        {
            posts = new ArrayList<>();
            if ("events".equals(keywordName))
            {
                List<ClubPost> results = postDAO.getAllClubPosts(true);
                posts.addAll(results);
            }
            else if (currentKeyword.isProject())
            {
                posts = postDAO.getAllProjectsByKeyword(keywordName);
            }
            else if (currentKeyword.isVisible())
            {
                List<Post> results = postDAO.getMostRecentPostsThrough2009WithGallery(false, keywordName);
                posts.addAll(results);
            }
            siteCache.addToPostCache(posts, SiteGenerationCache.OLD_VERSION_PROJECT_POSTS, keywordName);
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
