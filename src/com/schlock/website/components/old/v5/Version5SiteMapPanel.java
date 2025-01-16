package com.schlock.website.components.old.v5;

import com.schlock.website.components.old.AbstractOldLinks;
import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.old.SiteVersion;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class Version5SiteMapPanel extends AbstractOldLinks
{
    @Parameter(required = true)
    @Property
    private AbstractPost post;


    public boolean isHasPost()
    {
        return post != null;
    }


    public SiteVersion getVersion()
    {
        return SiteVersion.V5;
    }
}
