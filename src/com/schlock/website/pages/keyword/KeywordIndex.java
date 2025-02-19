package com.schlock.website.pages.keyword;

import com.schlock.website.entities.blog.Page;
import com.schlock.website.services.blog.KeywordManagement;
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
    private KeywordManagement keywordManagement;

    @Inject
    private PostDAO postDAO;

    @Property
    private Object[] currentKeyword;

    private Page cachedPage;


    public List<Object[]> getKeywords()
    {
        return keywordManagement.getAllAvailableKeywordNamesAndWeights();
    }

    public String getWeightClass()
    {
        long count = (long) currentKeyword[1];

        String cls = "weight";
        if (count >= 10)
        {
            return cls + "5";
        }
        if(count >= 7)
        {
            return cls + "4";
        }
        if (count >= 5)
        {
            return cls + "3";
        }
        if (count >= 3)
        {
            return cls + "2";
        }
        return cls + "1";
    }

    public String getCurrentKeywordLink()
    {
        String uuid = (String) currentKeyword[0];
        String url = linkSource.createPageRenderLinkWithContext(KeywordIndex.class, uuid).toURI();
        return url;
    }

    public String getCurrentKeywordName()
    {
        String uuid = (String) currentKeyword[0];
        return keywordManagement.getKeywordTitle(uuid);
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
