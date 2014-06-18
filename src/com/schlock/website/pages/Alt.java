package com.schlock.website.pages;

import com.schlock.website.entities.blog.LessonPost;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.List;

public class Alt
{
    @Inject
    private PostDAO postDAO;

    @Inject
    private Messages messages;

    @Inject
    private PostManagement postManagement;


    @Property
    private LessonPost currentPost;

    @Property
    private String currentYear;


    public String getYearTitle()
    {
        String key = currentYear + "-title";
        return getTitle(key);
    }

    public String getSixNenTitle()
    {
        return getTitle("6nen-title");
    }

    public String getFiveNenTitle()
    {
        return getTitle("5nen-title");
    }

    public String getThreeFourNenTitle()
    {
        return getTitle("34nen-title");
    }

    public String getOneTwoNenTitle()
    {
        return getTitle("12nen-title");
    }

    private String getTitle(String nlsKey)
    {
        String message = messages.get(nlsKey);
        return postManagement.wrapJapaneseTextInTags(message);
    }


    public List<LessonPost> getSixNenPosts()
    {
        List<LessonPost> posts = getPostsByPrefix(LessonPost.SIX_UUID_PREFIX);
        return posts;
    }

    public List<LessonPost> getFiveNenPosts()
    {
        List<LessonPost> posts = getPostsByPrefix(LessonPost.FIVE_UUID_PREFIX);
        return posts;
    }

    public List<LessonPost> getThreeFourNenPosts()
    {
        List<LessonPost> posts = getPostsByPrefix(LessonPost.THREE_FOUR_UUID_PREFIX);
        return posts;
    }

    public List<LessonPost> getOneTwoNenPosts()
    {
        List<LessonPost> posts = getPostsByPrefix(LessonPost.ONE_TWO_UUID_PREFIX);
        return posts;
    }

    public List<LessonPost> getSpecialPosts()
    {
        List<LessonPost> posts = getPostsByPrefix(LessonPost.SPECIAL_UUID_PREFIX);
        return posts;
    }

    private List<LessonPost> getPostsByPrefix(String startingPrefix)
    {
        String prefix = startingPrefix + "-" + currentYear + "-";

        List<LessonPost> posts = new ArrayList<LessonPost>();
        for (LessonPost post : getAllPosts())
        {
            if (StringUtils.startsWithIgnoreCase(post.getUuid(), prefix))
            {
                posts.add(post);
            }
        }
        return posts;
    }




    public List<String> getYears()
    {
        List<String> years = new ArrayList<String>();

        for (Post post : getAllPosts())
        {
            String[] parts = StringUtils.split(post.getUuid(), "-");

            if (parts.length > 2)
            {
                String year = parts[1];
                if (year.length() == 2 && !years.contains(year))
                {
                    years.add(year);
                }
            }
        }
        return years;
    }

    private List<LessonPost> cachedPosts;

    private List<LessonPost> getAllPosts()
    {
        if (cachedPosts == null)
        {
            List<LessonPost> posts = postDAO.getAllLessonPosts(true);
            cachedPosts = posts;
        }
        return cachedPosts;
    }

    private Page cachedPage;

    public Page getPage()
    {
        if (cachedPage == null)
        {
            cachedPage = (Page) postDAO.getByUuid(Page.ALT_MATERIALS_UUID);
        }
        return cachedPage;
    }
}
