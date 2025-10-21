package com.schlock.website.pages.projects;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Keyword;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.services.database.blog.KeywordDAO;
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
    private KeywordDAO keywordDAO;

    @Inject
    private PostDAO postDAO;

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private Messages messages;


    @Persist
    private Keyword selectedKeyword;

    @Property
    private Keyword currentKeyword;

    @Property
    private Keyword currentSubKeyword;

    @Property
    private AbstractPost currentPage;

    @Property
    private int currentIndex;


    private Map<String, List<AbstractPost>> postCache;
    private Set<Long> excludeIds;


    Object onActivate()
    {
        selectedKeyword = null;
        postCache = new HashMap<>();
        excludeIds = new HashSet<>();
        return true;
    }

    Object onActivate(String parameter)
    {
        onActivate();

        if (StringUtils.isNotBlank(parameter))
        {
            selectedKeyword = keywordDAO.getByName(parameter);
        }
        return true;
    }



    public List<Keyword> getKeywords()
    {
        return keywordDAO.getTopProjectKeywordsInOrder();
    }

    public String getExtraCss()
    {
        String css = "";
        if ((currentIndex + 1) % 4 == 0)
        {
            css += " fourColumnLast";
        }
        return css;
    }


    public String getCurrentKeywordLink()
    {
        if (selectedKeyword != null && selectedKeyword.equals(currentKeyword))
        {
            return linkSource.createPageRenderLink(ProjectsIndex.class).toURI();
        }
        String name = currentKeyword.getName();
        return linkSource.createPageRenderLinkWithContext(ProjectsIndex.class, name).toURI();
    }

    public String getCurrentSubKeywordLink()
    {
        if (selectedKeyword != null && selectedKeyword.equals(currentSubKeyword))
        {
            return linkSource.createPageRenderLink(ProjectsIndex.class).toURI();
        }
        String name = currentSubKeyword.getName();
        return linkSource.createPageRenderLinkWithContext(ProjectsIndex.class, name).toURI();
    }


    public List<Keyword> getSubKeywords()
    {
        return keywordDAO.getSubInOrder(currentKeyword);
    }

    public String getKeywordCssClass()
    {
        String css = currentKeyword.getName();
        if (selectedKeyword != null)
        {
            String selected = selectedKeyword.getName();

            String par = currentKeyword.getName();
            String cat = currentSubKeyword.getName();

            if (!StringUtils.equalsIgnoreCase(selected, par) &&
                    !StringUtils.equalsIgnoreCase(selected, cat))
            {
                css += "Unselected";
            }
        }
        return css;
    }



    public List<Keyword> getAllSubKeywords()
    {
        if (selectedKeyword != null)
        {
            if (selectedKeyword.isTopKeyword())
            {
                return keywordDAO.getSubInOrder(selectedKeyword);
            }
            return Arrays.asList(selectedKeyword);
        }

        List<Keyword> keywords = keywordDAO.getSubProjectKeywordsInOrder();

        Collections.sort(keywords, new Comparator<Keyword>()
        {
            public int compare(Keyword o1, Keyword o2)
            {
                List<AbstractPost> p1 = getCategoryProjects(o1.getName());
                List<AbstractPost> p2 = getCategoryProjects(o2.getName());

                return p2.get(0).getCreated().compareTo(p1.get(0).getCreated());
            }
        });

        return keywords;
    }

    private List<AbstractPost> getCategoryProjects(String keywordName)
    {
        List<AbstractPost> posts = postCache.get(keywordName);
        if (posts == null)
        {
            posts = postDAO.getAllProjectsByKeyword(keywordName);

            postCache.put(keywordName, posts);
        }
        return posts;
    }

    public List<AbstractPost> getProjectPages()
    {
        String keywordName = currentSubKeyword.getName();

        List<AbstractPost> pages = getCategoryProjects(keywordName);
        return pages;
    }

    public List<AbstractPost> getTopProjectPages()
    {
        List<AbstractPost> posts = getProjectPages();

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
        if (selectedKeyword != null)
        {
            return selectedKeyword.getDescription();
        }
        return messages.get("description");
    }
}
