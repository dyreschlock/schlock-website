package com.schlock.website.components.old.v1n2;

import com.schlock.website.components.old.AbstractOldLinks;
import com.schlock.website.entities.old.SiteVersion;
import org.apache.tapestry5.annotations.Parameter;

public class Version1n2MenuBar extends AbstractOldLinks
{
    @Parameter(required=true)
    private SiteVersion version;

    public SiteVersion getVersion()
    {
        return version;
    }

    public boolean isV1()
    {
        return SiteVersion.V1.equals(version);
    }

    public boolean isV2()
    {
        return SiteVersion.V2.equals(version);
    }


}
