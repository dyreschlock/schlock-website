package com.schlock.website.pages.projects;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.blog.ProjectCategory;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.*;

public class ProjectsIndex
{
    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    private PostDAO postDAO;

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private Messages messages;


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


    private Map<Long, List<AbstractPost>> postCache;
    private Set<Long> excludeIds;


    Object onActivate()
    {
        category = null;
        postCache = new HashMap<>();
        excludeIds = new HashSet<>();
        return true;
    }

    Object onActivate(String parameter)
    {
        onActivate();

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


    public String getCurrentCategoryLink()
    {
        String uuid = currentCategory.getUuid();
        if (category != null && category.equals(currentCategory))
        {
            return linkSource.createPageRenderLink(ProjectsIndex.class).toURI();
        }
        return linkSource.createPageRenderLinkWithContext(ProjectsIndex.class, uuid).toURI();
    }

    public String getCurrentSubcategoryLink()
    {
        String uuid = currentSubcategory.getUuid();
        if (category != null && category.equals(currentSubcategory))
        {
            return linkSource.createPageRenderLink(ProjectsIndex.class).toURI();
        }
        return linkSource.createPageRenderLinkWithContext(ProjectsIndex.class, uuid).toURI();
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



    public List<ProjectCategory> getAllSubcategories()
    {
        if (category != null)
        {
            if (category.isTopCategory())
            {
                return categoryDAO.getSubProjectInOrder(category.getId());
            }
            return Arrays.asList(category);
        }
        List<ProjectCategory> categories = categoryDAO.getSubProjectInOrder();

        Collections.sort(categories, new Comparator<ProjectCategory>()
        {
            public int compare(ProjectCategory o1, ProjectCategory o2)
            {
                List<AbstractPost> p1 = getCategoryProjects(o1.getId());
                List<AbstractPost> p2 = getCategoryProjects(o2.getId());

                return p2.get(0).getCreated().compareTo(p1.get(0).getCreated());
            }
        });

        return categories;
    }

    private List<AbstractPost> getCategoryProjects(Long categoryId)
    {
        List<AbstractPost> posts = postCache.get(categoryId);
        if (posts == null)
        {
            posts = postDAO.getAllProjectsByCategory(false, categoryId);

            postCache.put(categoryId, posts);
        }
        return posts;
    }

    public List<AbstractPost> getCategoryProjects()
    {
        Long categoryId = currentSubcategory.getId();

        List<AbstractPost> pages = getCategoryProjects(categoryId);
        return pages;
    }

    public List<AbstractPost> getCategoryTopProjects()
    {
        List<AbstractPost> posts = getCategoryProjects();

        int total = (int) Math.floor(((double) posts.size()) / ((double) 7)) +1;
        int count = 0;

        List<AbstractPost> top = new ArrayList<>();
        for(AbstractPost post : posts)
        {
            long id = post.getId();
            if (!excludeIds.contains(id))
            {
                top.add(post);
                excludeIds.add(id);
                count++;

                if (count >= total)
                {
                    return top;
                }
            }
        }
        return top;
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

    public String getPageTitle()
    {
        return getPage().getTitle();
    }

    public String getPageDescription()
    {
        if (category != null)
        {
            return category.getDescription();
        }
        return messages.get("description");
    }
}
