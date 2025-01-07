package com.schlock.website.components.old.v2;

import com.schlock.website.components.old.AbstractOldLinks;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.services.DeploymentContext;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Version2SiteMapDisplay extends AbstractOldLinks
{
    @Inject
    private DeploymentContext context;

    public SiteVersion getVersion()
    {
        return SiteVersion.V2;
    }

    public String getImageLinkStar1()
    {
        String link = context.webDomain() + "img/old/star1.gif";
        return link;
    }

    public String getImageLinkStar2()
    {
        String link = context.webDomain() + "img/old/star2.gif";
        return link;
    }
}
