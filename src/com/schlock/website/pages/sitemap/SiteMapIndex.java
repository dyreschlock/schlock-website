package com.schlock.website.pages.sitemap;

import com.schlock.website.entities.blog.PostCategory;
import com.schlock.website.entities.blog.ProjectCategory;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.blog.PostArchiveManagement;
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
    private PostArchiveManagement archiveManagement;

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




    public List<String> getDirectoryPages()
    {
        List<String> pages = new ArrayList<>();

        pages.addAll(getArchivePages());
        pages.addAll(getCategoryPages());
        pages.addAll(getProjectPages());
        return pages;
    }

    private List<String> getArchivePages()
    {
        final String ARCHIVE = "archive";

        List<String> pages = new ArrayList<>();

        pages.add(ARCHIVE + "/index.html");
        for(String year : archiveManagement.getYearlyMonthlyIterations(null, null))
        {
            pages.add(ARCHIVE + "/" + year + "/index.html");

            Integer y = Integer.parseInt(year);
            for(String yearMonth : archiveManagement.getYearlyMonthlyIterations(y, null))
            {
                String path = yearMonth.replace("-", "/");

                pages.add(ARCHIVE + "/" + path + ".html");
            }
        }
        return pages;
    }

    private List<String> getCategoryPages()
    {
        final String CATEGORY = "category";

        List<String> pages = new ArrayList<>();

        for(PostCategory cat : categoryDAO.getTopInOrder())
        {
            pages.add(CATEGORY + "/" + cat.getUuid() + ".html");

            for(PostCategory sub : categoryDAO.getSubInOrder(cat.getId()))
            {
                pages.add(CATEGORY + "/" + sub.getUuid() + ".html");
            }
        }
        return pages;
    }

    private List<String> getProjectPages()
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
