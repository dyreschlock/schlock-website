package com.schlock.website.pages.old.v2;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.pages.old.AbstractOldVersionIndex;
import com.schlock.website.services.blog.CssCache;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

public class V2Index extends AbstractOldVersionIndex
{
    @Inject
    private CssCache cssCache;

    @Inject
    private PostDAO postDAO;


    @Property
    private AbstractPost post;


    Object onActivate()
    {
        post = getPost(postDAO, null);

        return true;
    }

    Object onActivate(String param)
    {
        post = getPost(postDAO, param);

        return true;
    }






    public String getPageCss()
    {
        return cssCache.getCssOldVersions(post, SiteVersion.V2);
    }
}
