package com.schlock.website.pages.old.v2;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.ioc.annotations.Inject;

public class V2AboutMe extends AbstractVersion2Page
{
    @Inject
    private PostManagement postManagement;

    @Inject
    private PostDAO postDAO;


    public AbstractPost getPost()
    {
        return postDAO.getByUuid(Page.ABOUT_ME_UUID);
    }

    public String getPostBodyHTML()
    {
        String html = postManagement.generatePostHTML(getPost(), getVersion());
        return html;
    }

    public String getPage()
    {
        return Page.ABOUT_ME_UUID;
    }
}
