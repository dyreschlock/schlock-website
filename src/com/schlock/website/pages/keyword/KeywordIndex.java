package com.schlock.website.pages.keyword;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Keyword;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.blog.KeywordManagement;
import com.schlock.website.services.blog.PostArchiveManagement;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.KeywordDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
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
    private ImageManagement imageManagement;

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


    private Keyword selectedKeyword;
    private Page cachedPage;



    Object onActivate()
    {
        selectedKeyword = null;
        return true;
    }

    Object onActivate(String name)
    {
        selectedKeyword = keywordDAO.getByName(name);
        return true;
    }


    public List<Object[]> getKeywords()
    {
        return keywordManagement.getAllAvailableKeywordNamesAndWeights();
    }

    public String getWeightClass()
    {
        int weight = (int) currentKeyword[1];
        return "weight" + weight;
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
        return selectedKeyword != null;
    }

    public boolean isKeywordVisible()
    {
        return selectedKeyword != null && selectedKeyword.isVisible();
    }

    public String getSelectedKeywordName()
    {
        if (isKeywordSelected())
        {
            return selectedKeyword.getName();
        }
        return null;
    }

    public AbstractPost getMostRecent()
    {
        int LIMIT = 1;
        String keywordName = getSelectedKeywordName();

        List<Post> posts = postManagement.getTopPosts(LIMIT, keywordName, Collections.EMPTY_SET);
        return posts.get(0);
    }

    public List<String> getYearMonthIterations()
    {
        String keywordName = getSelectedKeywordName();
        return archiveManagement.getYearlyMonthlyIterations(keywordName);
    }

    public String getIterationTitle()
    {
        return archiveManagement.getIterationTitle(currentIteration);
    }

    public List<Post> getPosts()
    {
        String keywordName = getSelectedKeywordName();
        return archiveManagement.getPosts(currentIteration, keywordName);
    }

    public List<Post> getPreviewPosts()
    {
        Set<Long> exclude = new HashSet<>();
        exclude.add(getMostRecent().getId());

        String keywordName = getSelectedKeywordName();
        return archiveManagement.getPreviewPosts(currentIteration, keywordName, exclude);
    }

    public String getCoverImagePostUuid()
    {
        if (isKeywordSelected())
        {
            AbstractPost mostRecent = getMostRecent();
            String link = imageManagement.getPostPreviewImageLink(mostRecent);
            if (StringUtils.isNotBlank(link))
            {
                return mostRecent.getUuid();
            }
            return "";
        }
        return null;
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
            title = selectedKeyword.getTitle();
            if (!selectedKeyword.isVisible())
            {
                title = messages.format("title-keyword", title);
            }
        }
        return title;
    }

    public String getPageDescription()
    {
        String text = messages.get("description");
        if (isKeywordSelected())
        {
            if (selectedKeyword.isVisible())
            {
                text = selectedKeyword.getDescription();
            }
            else
            {
                String keyword = selectedKeyword.getTitle();
                text = messages.format("description-keyword", keyword);
            }
        }
        return text;
    }

    public String getKeywordDescription()
    {
        String description = getPageDescription();

        description = description.replace(". ", ".<br/>");

        return description;
    }

    public String getPageUrl()
    {
        String url = linkSource.createPageRenderLink(KeywordIndex.class).toURI();
        if (isKeywordSelected())
        {
            url += "/" + selectedKeyword.getName();
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
