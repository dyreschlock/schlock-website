package com.schlock.website.pages.old.v2;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Keyword;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.database.blog.KeywordDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.List;

public class V2Projects extends AbstractVersion2Page
{
    @Inject
    private KeywordDAO keywordDAO;

    @Inject
    private PostDAO postDAO;

    @Inject
    private DateFormatter dateFormatter;


    private AbstractPost post;



    Object onActivate()
    {
        post = null;
        return true;
    }

    Object onActivate(String param)
    {
        post = postDAO.getByUuid(param);
        return true;
    }

    public AbstractPost getPost()
    {
        return post;
    }

    public String getPage()
    {
        if (isHasPost())
        {
            return "project";
        }
        return super.getPage();
    }

    public String getPostDate()
    {
        return dateFormatter.dateFormat(post.getCreated());
    }

    public boolean isProjectPost()
    {
        for(Keyword keyword : post.getKeywords())
        {
            if (keyword.isProject())
            {
                return true;
            }
        }
        return false;
    }

    public boolean isPhotoPost()
    {
        return !isProjectPost();
    }


    public List<Keyword> getPhotoCategories()
    {
        List<Keyword> keywords = new ArrayList<>();
        for(String name : getTravelPhotoCategoryNames())
        {
            keywords.add(keywordDAO.getByName(name));
        }
        for(String name : getClubPhotoCategoryUuids())
        {
            keywords.add(keywordDAO.getByName(name));
        }
        return keywords;
    }
}
