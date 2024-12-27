package com.schlock.website.pages.old;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public abstract class AbstractOldVersionIndex
{
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
