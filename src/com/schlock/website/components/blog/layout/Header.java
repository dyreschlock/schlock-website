package com.schlock.website.components.blog.layout;

import com.schlock.website.entities.blog.Keyword;
import com.schlock.website.pages.AboutMe;
import com.schlock.website.pages.archive.ArchiveIndex;
import com.schlock.website.pages.keyword.KeywordIndex;
import com.schlock.website.pages.photo.PhotoIndex;
import com.schlock.website.pages.projects.ProjectsIndex;
import com.schlock.website.services.database.blog.KeywordDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

public class Header
{
    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private KeywordDAO keywordDAO;

    @Property
    private Keyword currentKeyword;


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


    public List<Keyword> getKeywords()
    {
        return keywordDAO.getTopPostKeywordsInOrder();
    }

    public String getKeywordLink()
    {
        return linkSource.createPageRenderLinkWithContext(KeywordIndex.class, currentKeyword.getName()).toURI();
    }
}
