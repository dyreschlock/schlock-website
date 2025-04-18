package com.schlock.website.pages.map;

import com.schlock.website.entities.blog.Page;
import com.schlock.website.services.blog.MapLocationManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.ioc.annotations.Inject;

public class MapIndex
{
    @Inject
    private MapLocationManagement mapManagement;

    @Inject
    private PostDAO postDAO;

    private Page cachedPage;

    public Page getPage()
    {
        if (cachedPage == null)
        {
            cachedPage = (Page) postDAO.getByUuid(Page.MAP_UUID);
        }
        return cachedPage;
    }

    public String getPageDescription()
    {
        return getPage().getBlurb();
    }

    public String getJavascript()
    {
        return mapManagement.generateMapJavascript();
    }
}
