package com.schlock.website.components.old.v6;

import com.schlock.website.components.old.AbstractOldPreviousNext;
import com.schlock.website.entities.old.SiteVersion;
import org.apache.tapestry5.annotations.Parameter;

public class Version6PreviousNext extends AbstractOldPreviousNext
{
    @Parameter(required = true)
    private String page;




    protected SiteVersion getVersion()
    {
        return SiteVersion.V6;
    }
}
