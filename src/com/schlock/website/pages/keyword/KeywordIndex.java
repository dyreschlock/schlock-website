package com.schlock.website.pages.keyword;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.services.blog.KeywordManagement;
import com.schlock.website.services.blog.PostArchiveManagement;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.KeywordDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KeywordIndex
{
    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private Messages messages;

    @Inject
    private PostArchiveManagement archiveManagement;

    @Inject
    private KeywordManagement keywordManagement;

    @Inject
    private PostManagement postManagement;

    @Inject
    private KeywordDAO keywordDAO;

    @Inject
    private PostDAO postDAO;

    @Property
    private Object[] currentKeyword;

    @Property
    private String currentIteration;

    @Property
    private AbstractPost currentPost;

    private String selectedKeywordName;
    private Page cachedPage;



    Object onActivate()
    {
        selectedKeywordName = null;
        return true;
    }

    Object onActivate(String name)
    {
        selectedKeywordName = name;
        return true;
    }


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


    public boolean isKeywordSelected()
    {
        return selectedKeywordName != null;
    }

    public AbstractPost getMostRecent()
    {
        int LIMIT = 1;

        List<Post> posts = postManagement.getTopPosts(LIMIT, selectedKeywordName, Collections.EMPTY_SET);
        return posts.get(0);
    }

    public List<String> getYearMonthIterations()
    {
        return archiveManagement.getYearlyMonthlyIterations(selectedKeywordName);
    }

    public String getIterationTitle()
    {
        return archiveManagement.getIterationTitle(currentIteration);
    }

    public List<Post> getPosts()
    {
        return archiveManagement.getPosts(currentIteration, selectedKeywordName);
    }

    public List<Post> getPreviewPosts()
    {
        Set<Long> exclude = new HashSet<>();
        exclude.add(getMostRecent().getId());

        return archiveManagement.getPreviewPosts(currentIteration, selectedKeywordName, exclude);
    }



    public String getReturnLink()
    {
        return linkSource.createPageRenderLink(KeywordIndex.class).toURI();
    }

    public String getPageTitle()
    {
        String title = messages.get("title");
        if (isKeywordSelected())
        {
            String keyword = keywordManagement.getKeywordTitle(selectedKeywordName);
            title = messages.format("title-keyword", keyword);
        }
        return title;
    }

    public String getPageDescription()
    {
        String text = messages.get("description");
        if (isKeywordSelected())
        {
            String keyword = keywordManagement.getKeywordTitle(selectedKeywordName);
            text = messages.format("description-keyword", keyword);
        }
        return text;
    }

    public String getPageUrl()
    {
        String url = linkSource.createPageRenderLink(KeywordIndex.class).toURI();
        if (isKeywordSelected())
        {
            url = "/" + selectedKeywordName;
        }
        return url;
    }

    public String getPageUuid()
    {
        return Page.KEYWORD_CLOUD_UUID;
    }

    public Page getPage()
    {
        if (cachedPage == null)
        {
            cachedPage = (Page) postDAO.getByUuid(getPageUuid());
        }
        return cachedPage;
    }
}
