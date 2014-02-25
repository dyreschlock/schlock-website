package com.schlock.website.services.database;

import com.schlock.website.services.database.blog.CategoryDAO;
import com.schlock.website.services.database.blog.PostDAO;
import com.schlock.website.services.database.blog.impl.CategoryDAOImpl;
import com.schlock.website.services.database.blog.impl.PostDAOImpl;
import org.apache.tapestry5.ioc.ServiceBinder;

public class DatabaseModule
{
    public static void bind(ServiceBinder binder)
    {
        binder.bind(PostDAO.class, PostDAOImpl.class);
        binder.bind(CategoryDAO.class, CategoryDAOImpl.class);
    }
}
