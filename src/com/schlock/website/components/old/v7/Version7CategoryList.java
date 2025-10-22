package com.schlock.website.components.old.v7;

import com.schlock.website.entities.blog.Keyword;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.services.database.blog.KeywordDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.HashMap;
import java.util.List;

public class Version7CategoryList
{
    @Inject
    private KeywordDAO keywordDAO;

    @Inject
    private PostDAO postDAO;

    @Property
    private Keyword currentKeyword;


    private HashMap<String, Integer> categoryCount = new HashMap<>();


    public List<Keyword> getKeywords()
    {
        return keywordDAO.getAllPostKeywordsInAlphaOrder();
    }

    public Integer getPostCount()
    {
        String keywordName = currentKeyword.getName();
        Integer count = categoryCount.get(keywordName);
        if (count == null)
        {
            List<Post> posts = postDAO.getMostRecentPosts(null, false, null, null, keywordName);
            count = posts.size();

            categoryCount.put(keywordName, count);
        }
        return count;
    }
}
