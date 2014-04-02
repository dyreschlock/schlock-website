package com.schlock.website.services.blog;

import com.schlock.website.services.blog.impl.ConvertWordpressImpl;
import com.schlock.website.services.blog.impl.CssCacheImpl;
import com.schlock.website.services.blog.impl.ImageManagementImpl;
import com.schlock.website.services.blog.impl.PostManagementImpl;
import org.apache.tapestry5.ioc.ServiceBinder;

public class BlogModule
{
    public static void bind(ServiceBinder binder)
    {
        binder.bind(ImageManagement.class, ImageManagementImpl.class);
        binder.bind(PostManagement.class, PostManagementImpl.class);

        binder.bind(CssCache.class, CssCacheImpl.class);

        binder.bind(ConvertWordpress.class, ConvertWordpressImpl.class);
    }
}
