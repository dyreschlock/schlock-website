package com.schlock.website.pages;

import com.schlock.website.entities.blog.Category;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Alt
{
    @Inject
    private PostDAO postDAO;

    @Property
    private Post currentPost;



    public List<Post> getSixNenPosts()
    {
        List<Post> posts = getPostsByPrefix(Post.SIX_UUID_PREFIX);
        return posts;
    }

    public List<Post> getFiveNenPosts()
    {
        List<Post> posts = getPostsByPrefix(Post.FIVE_UUID_PREFIX);
        return posts;
    }

    public List<Post> getThreeFourNenPosts()
    {
        List<Post> posts = getPostsByPrefix(Post.THREE_FOUR_UUID_PREFIX);
        return posts;
    }

    public List<Post> getOneTwoNenPosts()
    {
        List<Post> posts = getPostsByPrefix(Post.ONE_TWO_UUID_PREFIX);
        return posts;
    }

    public List<Post> getSpecialPosts()
    {
        List<Post> posts = getPostsByPrefix(Post.SPECIAL_UUID_PREFIX);
        return posts;
    }

    private List<Post> getPostsByPrefix(String... prefix)
    {
        List<Post> posts = new ArrayList<Post>();
        for (Post post : getAllPosts())
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





    private List<Post> cachedPosts;

    private List<Post> getAllPosts()
    {
        if (cachedPosts == null)
        {
            List<Post> posts = postDAO.getByCategoryNames(Arrays.asList(Category.TEACHING), false);
            cachedPosts = posts;
        }
        return cachedPosts;
    }

    public String getPageTitle()
    {
        return getPage().getTitle();
    }

    private Post cachedPage;

    public Post getPage()
    {
        if (cachedPage == null)
        {
            cachedPage = postDAO.getByUuid(Post.ALT_MATERIALS_UUID);
        }
        return cachedPage;
    }
}
