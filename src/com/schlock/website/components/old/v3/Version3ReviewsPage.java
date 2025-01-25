package com.schlock.website.components.old.v3;

import com.schlock.website.components.old.AbstractOldPostDisplay;
import com.schlock.website.entities.blog.AbstractCategory;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.services.DeploymentContext;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class Version3ReviewsPage extends AbstractOldPostDisplay
{
    @Inject
    private DeploymentContext context;


    @Parameter(required = true)
    private List<String> reviewCategoryUuids;


    public String getCategoryName()
    {
        for(AbstractCategory cat : getPost().getCategories())
        {
            if (reviewCategoryUuids.contains(cat.getUuid()))
            {
                return cat.getName();
            }
        }
        return "Game";
    }

    protected SiteVersion getVersion()
    {
        return SiteVersion.V3;
    }

    public String getCircleImage1Link()
    {
        String link = context.webDomain() + "img/old/circle1.gif";
        return link;
    }

    public String getCircleImage2Link()
    {
        String link = context.webDomain() + "img/old/circle2.gif";
        return link;
    }
}
