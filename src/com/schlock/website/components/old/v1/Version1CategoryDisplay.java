package com.schlock.website.components.old.v1;

import com.schlock.website.entities.blog.*;
import com.schlock.website.pages.old.v1.V1Index;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Version1CategoryDisplay
{
    private static final String PROJECTS_PAGE = V1Index.PROJECTS_PAGE;
    private static final String GAMES_PAGE = V1Index.GAMES_PAGE;
    private static final String MUSIC_PAGE = V1Index.MUSIC_PAGE;


    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private Messages messages;

    @Inject
    private DateFormatter dateFormatter;

    @Inject
    private PostManagement postManagement;

    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    private PostDAO postDAO;


    @Parameter(required = true)
    private String type;

    @Property
    private AbstractPost currentPost;

    @Property
    private Integer currentIndex;



    public boolean isMusicPage()
    {
        return MUSIC_PAGE.equals(type);
    }

    public List<AbstractPost> getPosts()
    {
        if (PROJECTS_PAGE.equals(type))
        {
            return postDAO.getAllProjectsByCategory(false, null);
        }
        if (GAMES_PAGE.equals(type))
        {
            AbstractCategory cat = categoryDAO.getByUuid(PostCategory.class, GAMES_PAGE);
            Long categoryId = cat.getId();

            List<Post> posts = postDAO.getMostRecentPosts(null, false, null, null, categoryId);

            List<AbstractPost> results = new ArrayList<>();
            results.addAll(posts);
            return results;
        }
        if (MUSIC_PAGE.equals(type))
        {
            List<ClubPost> posts = postDAO.getAllClubPosts(true);

            List<AbstractPost> results = new ArrayList<>();
            results.addAll(posts);
            return results;
        }
        return null;
    }


    public String getPageTitle()
    {
        return messages.get(type);
    }



    public String getCurrentPostDescription()
    {
        return postManagement.generatePostDescription(currentPost);
    }

    public String getCurrentPostLink()
    {
        String uuid = currentPost.getUuid();
        return linkSource.createPageRenderLinkWithContext(V1Index.class, uuid).toURI();
    }

    public String getCurrentClubDate()
    {
        if(currentPost.isClubPost())
        {
            Date date = ((ClubPost) currentPost).getEventDate();
            return dateFormatter.shortDateFormat(date);
        }
        return "";
    }

    public String getNewLine()
    {
        if (currentIndex != 0 && currentIndex % 2 == 0)
        {
            return "</tr><tr>";
        }
        return "";
    }
}
