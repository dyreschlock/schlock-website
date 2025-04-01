package com.schlock.website.components.blog.layout;

import com.schlock.website.entities.blog.AbstractCategory;
import com.schlock.website.entities.blog.PostCategory;
import com.schlock.website.pages.AboutMe;
import com.schlock.website.pages.archive.ArchiveIndex;
import com.schlock.website.pages.category.CategoryIndex;
import com.schlock.website.pages.photo.PhotoIndex;
import com.schlock.website.pages.projects.ProjectsIndex;
import com.schlock.website.services.database.blog.CategoryDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

public class Header
{
    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private CategoryDAO categoryDAO;

    @Property
    private AbstractCategory currentCategory;


    public String getArchiveLink()
    {
        return linkSource.createPageRenderLink(ArchiveIndex.class).toURI();
    }

    public String getPhotoLink()
    {
        return linkSource.createPageRenderLink(PhotoIndex.class).toURI();
    }

    public String getProjectsLink()
    {
        return linkSource.createPageRenderLink(ProjectsIndex.class).toURI();
    }

    public String getAboutMeLink()
    {
        return linkSource.createPageRenderLink(AboutMe.class).toURI();
    }


    public List<PostCategory> getCategories()
    {
        return categoryDAO.getTopInOrder();
    }

    public String getCategoryLink()
    {
        return linkSource.createPageRenderLinkWithContext(CategoryIndex.class, currentCategory.getUuid()).toURI();
    }
}
