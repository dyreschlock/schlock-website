package com.schlock.website.components.old.v7;

import com.schlock.website.components.old.AbstractOldPostDisplay;
import com.schlock.website.entities.blog.AbstractCategory;
import com.schlock.website.entities.old.SiteVersion;
import org.apache.tapestry5.annotations.Property;

import java.util.List;

public class Version7PostDisplay extends AbstractOldPostDisplay
{
    @Property
    private AbstractCategory currentCategory;

    @Property
    private Integer currentIndex;

    protected SiteVersion getVersion()
    {
        return SiteVersion.V7;
    }

    public List<AbstractCategory> getCategories()
    {
        return getPost().getAllPostCategories();
    }


    public boolean isNotFirst()
    {
        return !new Integer(0).equals(currentIndex);
    }
}
