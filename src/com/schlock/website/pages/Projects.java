package com.schlock.website.pages;

import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.blog.Project;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class Projects
{
    @Inject
    private PostDAO postDAO;

    @Property
    private Project currentPage;

    @Property
    private int currentIndex;


    public String getExtraCss()
    {
        String extraCss = "";
        if ((currentIndex + 1) % 2 == 0)
        {
            extraCss += " twoColumnLast";
        }
        else
        {
            extraCss += " clr";
        }
        return extraCss;
    }

    public List<Project> getProjects()
    {
        List<Project> pages = postDAO.getAllProjects(true);
        return pages;
    }


    private Page cachedPage;

    public Page getPage()
    {
        if (cachedPage == null)
        {
            cachedPage = (Page) postDAO.getByUuid(Page.PROJECTS_UUID);
        }
        return cachedPage;
    }
}
