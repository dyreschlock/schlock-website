package com.schlock.website.pages;

import com.schlock.website.entities.blog.Page;
import com.schlock.website.services.database.blog.PostDAO;

import javax.inject.Inject;

public class ErrorPage
{
    @Inject
    private PostDAO postDAO;




    public Page getPage()
    {
        return (Page) postDAO.getByUuid(Page.ERROR_PAGE_UUID);
    }
}
