package com.schlock.website.pages.old;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractOldVersionIndex
{
    public static final String ARCHIVE_PAGE = "archive";

    public static final String PROJECTS_PAGE = "projects";
    public static final String GAMES_PAGE = "reviews";
    public static final String MUSIC_PAGE = "music";


    protected boolean isSpecialPage(String param)
    {
        return Arrays.asList(ARCHIVE_PAGE, PROJECTS_PAGE, GAMES_PAGE, MUSIC_PAGE).contains(param);
    }



    protected AbstractPost getPost(final PostDAO postDAO, String param)
    {
        if (StringUtils.isBlank(param))
        {
            return postDAO.getMostRecentFrontPagePost(null);
        }

        List<AbstractPost> posts = postDAO.getAllByUuid(param);
        for(AbstractPost post : posts)
        {
            if (!post.isCoursePage())
            {
                return post;
            }
        }
        return getPost(postDAO, null);
    }
}
