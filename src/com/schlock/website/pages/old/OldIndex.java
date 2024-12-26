package com.schlock.website.pages.old;

import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class OldIndex
{
    @Inject
    private PostDAO postDAO;


    @Property
    private SiteVersion version;

    @Property
    private AbstractPost post;



    Object onActivate()
    {
        version = null;
        post = null;

        return true;
    }

    Object onActivate(String param)
    {
        version = SiteVersion.getVersion(param);
        post = null;

        return true;
    }

    Object onActivate(String param1, String param2)
    {
        version = SiteVersion.getVersion(param1);

        AbstractPost requested = null;

        List<AbstractPost> posts = postDAO.getAllByUuid(param2);
        for(AbstractPost post : posts)
        {
            if (!post.isCoursePage())
            {
                requested = post;
            }
        }
        if (posts.isEmpty())
        {
            requested = postDAO.getMostRecentPost(false, null);
        }
        post = requested;

        return true;
    }


    public boolean isHasNoPostNoVersion()
    {
        return post == null && version == null;
    }

    public boolean isVersion1()
    {
        return SiteVersion.V1.equals(version);
    }

    public boolean isVersion2()
    {
        return SiteVersion.V2.equals(version);
    }
}
