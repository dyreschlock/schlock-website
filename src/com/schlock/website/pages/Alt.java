package com.schlock.website.pages;

import com.schlock.website.entities.blog.LessonPost;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.List;

public class Alt
{
    @Inject
    private PostDAO postDAO;

    @Property
    private LessonPost currentPost;



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

    private List<LessonPost> getPostsByPrefix(String... prefix)
    {
        List<LessonPost> posts = new ArrayList<LessonPost>();
        for (LessonPost post : getAllPosts())
        {
            for (String pre : prefix)
            {
                if (StringUtils.startsWithIgnoreCase(post.getUuid(), pre + "-"))
                {
                    posts.add(post);
                }
            }
        }
        return posts;
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

    public String getPageTitle()
    {
        return getPage().getTitle();
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
