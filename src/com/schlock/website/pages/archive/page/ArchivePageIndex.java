package com.schlock.website.pages.archive.page;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.pages.archive.ArchiveIndex;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

public class ArchivePageIndex
{
    private final String CRYSTANIA_UUID = "the-secret-of-crystania";

    @Inject
    private PostDAO postDAO;

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private Messages messages;

    @Property
    private Page currentPage;


    public List<Page> getPages()
    {
        return postDAO.getAllPagesNoCourses(true);
    }

    public Page getCrystaniaPage()
    {
        AbstractPost post = postDAO.getByUuid(CRYSTANIA_UUID);
        return (Page) post;
    }








    public String getPageTitle()
    {
        String title = messages.get("title");
        return title;
    }

    public String getPageDescription()
    {
        String text = messages.get("description");
        return text;
    }

    public String getPageUuid()
    {
        return Page.PAGE_ARCHIVE_UUID;
    }

    public String getPageUrl()
    {
        String link = linkSource.createPageRenderLink(ArchiveIndex.class).toURI();
        return link;
    }
}
