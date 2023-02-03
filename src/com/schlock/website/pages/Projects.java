package com.schlock.website.pages;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.blog.ProjectCategory;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class Projects
{
    private static final String NULL = "NULL";

    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    private PostDAO postDAO;

    @Persist
    private ProjectCategory category;

    @Property
    private ProjectCategory currentCategory;

    @Property
    private ProjectCategory currentSubcategory;

    @Property
    private AbstractPost currentPage;

    @Property
    private int currentIndex;

    @InjectComponent
    private Zone postZone;


    Object onActivate()
    {
        category = null;

        return true;
    }

    Object onActivate(String parameter)
    {
        category = null;
        if (StringUtils.isNotBlank(parameter))
        {
            category = (ProjectCategory) categoryDAO.getByUuid(ProjectCategory.class, parameter);
        }
        return true;
    }


    public List<ProjectCategory> getCategories()
    {
        return categoryDAO.getTopProjectInOrder();
    }

    public String getExtraCategoryCss()
    {
        String css = "";
        if ((currentIndex + 1) % 4 == 0)
        {
            css += " fourColumnLast";
        }
        return css;
    }

    public String getCategoryUuid()
    {
        String uuid = currentCategory.getUuid();
        if (category != null && category.equals(currentCategory))
        {
            uuid = NULL;
        }
        return uuid;
    }

    public List<ProjectCategory> getSubcategories()
    {
        return categoryDAO.getSubProjectInOrder(currentCategory.getId());
    }

    public String getCategoryCssClass()
    {
        String css = currentCategory.getUuid();
        if (category != null)
        {
            String selected = category.getUuid();

            String par = currentCategory.getUuid();
            String cat = currentSubcategory.getUuid();

            if (!StringUtils.equalsIgnoreCase(selected, par) &&
                    !StringUtils.equalsIgnoreCase(selected, cat))
            {
                css += "Unselected";
            }
        }
        return css;
    }

    public String getSubcategoryUuid()
    {
        String uuid = currentSubcategory.getUuid();
        if (category != null && category.equals(currentSubcategory))
        {
            uuid = NULL;
        }
        return uuid;
    }



    public String getExtraPostCss()
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

    public List<AbstractPost> getProjects()
    {
        Long categoryId = null;
        if (category != null)
        {
            categoryId = category.getId();
        }

        List<AbstractPost> pages = postDAO.getAllProjectsByCategory(true, categoryId);
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
