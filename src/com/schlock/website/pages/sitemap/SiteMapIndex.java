package com.schlock.website.pages.sitemap;

import com.schlock.website.entities.blog.ProjectCategory;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.database.blog.CategoryDAO;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SiteMapIndex
{
    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    private DateFormatter dateFormatter;

    @Inject
    private DeploymentContext context;



    @Property
    private String currentPage;


    public String getDomain()
    {
        return context.webDomain();
    }


    @Persist
    private String currentTime;

    public String getCurrentTime()
    {
        if (currentTime == null)
        {
            currentTime = dateFormatter.w3DateFormat(new Date());
        }
        return currentTime;
    }

    //blog posts
    //course pages
    //regular pages

    //archive pages
    //category pages
    //project pages

    public List<String> getProjectPages()
    {
        final String PROJECTS = "projects";

        List<String> pages = new ArrayList<>();
        pages.add(PROJECTS + "/index.html");

        for(ProjectCategory cat : categoryDAO.getTopProjectInOrder())
        {
            pages.add(PROJECTS + "/" + cat.getUuid() + ".html");

            for(ProjectCategory sub : categoryDAO.getSubProjectInOrder(cat.getId()))
            {
                pages.add(PROJECTS + "/" + sub.getUuid() + ".html");
            }
        }
        return pages;
    }
}
