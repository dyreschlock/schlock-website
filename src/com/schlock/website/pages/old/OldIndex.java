package com.schlock.website.pages.old;

import com.schlock.website.pages.old.v5.AbstractVersion5Page;
import com.schlock.website.services.DeploymentContext;
import org.apache.tapestry5.ioc.annotations.Inject;

public class OldIndex extends AbstractVersion5Page
{
    @Inject
    private DeploymentContext context;


    public String getImageLinkWinter1()
    {
        return context.webDomain() + "img/old/winter1.jpg";
    }

    public String getImageLinkWinter2()
    {
        return context.webDomain() + "img/old/winter2.jpg";
    }
}
