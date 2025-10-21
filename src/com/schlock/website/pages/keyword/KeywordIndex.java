package com.schlock.website.pages.keyword;

import com.schlock.website.entities.blog.*;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.blog.KeywordManagement;
import com.schlock.website.services.blog.PostArchiveManagement;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.KeywordDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.*;

public class KeywordIndex
{
    @SessionState
    private ViewState viewState;

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
    private Keyword currentSubKeyword;

    @Property
    private String currentIteration;

    @Property
    private AbstractPost currentPost;


    private Keyword selectedKeyword;
    private Page cachedPage;

    private Set<Long> excludeIds;


    Object onActivate()
    {
        selectedKeyword = null;
        excludeIds = new HashSet<>();

        return true;
    }

    Object onActivate(String name)
    {
        selectedKeyword = keywordDAO.getByName(name);

        excludeIds = new HashSet<>();
        excludeIds.add(getMostRecent().getId());

        return true;
    }


    public List<Object[]> getKeywords()
    {
        return keywordManagement.getAllAvailableKeywordNamesAndWeights();
    }

    public String getWeightClass()
    {
        int weight = (int) currentKeyword[2];
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
        String title = (String) currentKeyword[1];
        return title;
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

    private List<Keyword> cachedKeywords;

    public List<Keyword> getSubKeywords()
    {
        if (cachedKeywords == null)
        {
            List<Keyword> keywords = new ArrayList<>();
            if (selectedKeyword.isTopKeyword())
            {
                keywords.add(selectedKeyword);
            }
            keywords.addAll(keywordDAO.getSubInOrder(selectedKeyword));

            cachedKeywords = keywords;
        }
        return cachedKeywords;
    }

    public boolean isCurrentKeywordCurrent()
    {
        return selectedKeyword.equals(currentSubKeyword);
    }

    public String getCurrentSubKeywordLink()
    {
        return linkSource.createPageRenderLinkWithContext(KeywordIndex.class, currentSubKeyword.getName()).toURI();
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

    public List<Post> getSubPosts()
    {
        int postCount = 21;
        boolean unpublished = viewState.isShowUnpublished();
        String keywordName = currentSubKeyword.getName();

        List<Post> posts = postDAO.getMostRecentPosts(postCount, unpublished, null, null, null, keywordName);
        return posts;
    }

    public List<Post> getSubPreviewPosts()
    {
        int count = getSubPosts().size();
        int LIMIT = (int) Math.floor(((double) count ) / ((double) 7));
        if (LIMIT < 1)
        {
            LIMIT = 1;
        }

        String keywordName = currentSubKeyword.getName();

        List<Post> posts = postManagement.getTopPosts(LIMIT, keywordName, excludeIds);

        return posts;
    }

    public List<Post> getPosts()
    {
        String keywordName = getSelectedKeywordName();
        return archiveManagement.getPosts(currentIteration, keywordName);
    }

    public List<Post> getPreviewPosts()
    {
        String keywordName = getSelectedKeywordName();
        return archiveManagement.getPreviewPosts(currentIteration, keywordName, excludeIds);
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
            if (selectedKeyword.isVisible())
            {
                title = selectedKeyword.getTitle();
                if (!selectedKeyword.isTopKeyword())
                {
                    title = selectedKeyword.getParent().getTitle() + " /" + title;
                }
            }
            else
            {
                title = keywordManagement.getKeywordTitle(selectedKeyword.getName());
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
