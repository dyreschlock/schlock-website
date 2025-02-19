package com.schlock.website.pages.keyword;

import com.schlock.website.entities.blog.Keyword;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.services.database.blog.KeywordDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

public class KeywordIndex
{
    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private Messages messages;

    @Inject
    private KeywordDAO keywordDAO;

    @Inject
    private PostDAO postDAO;

    @Property
    private Keyword currentKeyword;

    private Page cachedPage;


    public List<Keyword> getKeywords()
    {
        return keywordDAO.getAllAvailable();
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

    public String getPageUrl()
    {
        String url = linkSource.createPageRenderLink(KeywordIndex.class).toURI();
        return url;
    }

    public Page getPage()
    {
        if (cachedPage == null)
        {
            cachedPage = (Page) postDAO.getByUuid(Page.KEYWORD_CLOUD_UUID);
        }
        return cachedPage;
    }
}
