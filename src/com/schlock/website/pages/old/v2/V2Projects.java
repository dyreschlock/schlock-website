package com.schlock.website.pages.old.v2;

import com.schlock.website.entities.blog.AbstractCategory;
import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.PostCategory;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.List;

public class V2Projects extends AbstractVersion2Page
{
    @Inject
    private CategoryDAO categoryDAO;

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
        for(AbstractCategory cat : post.getCategories())
        {
            if (cat.isProject())
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


    public List<AbstractCategory> getPhotoCategories()
    {
        List<AbstractCategory> categories = new ArrayList<>();
        for(String uuid : getTravelPhotoCategoryUuids())
        {
            categories.add(categoryDAO.getByUuid(PostCategory.class, uuid));
        }
        for(String uuid : getClubPhotoCategoryUuids())
        {
            categories.add(categoryDAO.getByUuid(PostCategory.class, uuid));
        }
        return categories;
    }
}
