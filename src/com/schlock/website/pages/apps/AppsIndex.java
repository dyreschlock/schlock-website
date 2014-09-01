package com.schlock.website.pages.apps;

import com.schlock.website.entities.blog.ApplicationPage;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class AppsIndex
{
    @Inject
    private PostDAO postDAO;

    @Property
    private ApplicationPage currentPage;



    public List<ApplicationPage> getApplicationPages()
    {
        List<ApplicationPage> pages = postDAO.getAllApplicationPages(false);
        return pages;
    }


    private Page cachedPage;

    public Page getPage()
    {
        if (cachedPage == null)
        {
            cachedPage = (Page) postDAO.getByUuid(Page.APPLICATIONS_UUID);
        }
        return cachedPage;
    }
}
